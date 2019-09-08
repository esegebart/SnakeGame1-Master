/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.snake;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;




/**
 * Snake: a simple game that everyone can enjoy.
 * 
 * This is an implementation of the classic Game "Snake", in which you control a serpent roaming
 * around the garden looking for apples. Be careful, though, because when you catch one, not only
 * will you become longer, but you'll move faster. Running into yourself or the walls will end the
 * game.
 * 
 */

public class Snake extends AppCompatActivity {

    /**
     * Constants for desired direction of moving the snake
     */
    public static int MOVE_LEFT = 0;
    public static int MOVE_UP = 1;
    public static int MOVE_DOWN = 2;
    public static int MOVE_RIGHT = 3;

    private final String USERNAME_PLACEHOLDER = "username";

    private static String ICICLE_KEY = "snake-view";

    private SnakeView mSnakeView;
    private TextView mUserScoreButton;

    /**Create object of MediaPlayer*/
    private MediaPlayer eatSound;

    private UserScoreOpenHelper dbHelper;

    private List<UserScore> userScores;

    // Declare DatabaseReference
//    private DatabaseReference mDatabase;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("message");
    private DataSnapshot dataSnapshot;


    /**
     * Called when Activity is first created. Turns off the title bar, sets up the content views,
     * and fires up the SnakeView.
     * 
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.snake_layout);

        /**Load the sound into the eatSound variable*/
        eatSound = MediaPlayer.create(this, R.raw.eating);

        mSnakeView = (SnakeView) findViewById(R.id.snake);
        mSnakeView.setDependentViews((TextView) findViewById(R.id.text),
                findViewById(R.id.arrowContainer), findViewById(R.id.background),
                (TextView) findViewById(R.id.userScores));

        mUserScoreButton = (TextView) findViewById(R.id.userScores);

        if (savedInstanceState == null) {
            // We were just launched -- set up a new game
            mSnakeView.setMode(SnakeView.READY);
        } else {
            // We are being restored
            Bundle map = savedInstanceState.getBundle(ICICLE_KEY);
            if (map != null) {
                mSnakeView.restoreState(map);
            } else {
                mSnakeView.setMode(SnakeView.PAUSE);
            }
        }
        mSnakeView.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (mSnakeView.getGameState() == SnakeView.RUNNING) {
                    // Normalize x,y between 0 and 1
                    float x = event.getX() / v.getWidth();
                    float y = event.getY() / v.getHeight();

                    // Direction will be [0,1,2,3] depending on quadrant
                    int direction = 0;
                    direction = (x > y) ? 1 : 0;
                    direction |= (x > 1 - y) ? 2 : 0;

                    // Direction is same as the quadrant which was clicked
                    mSnakeView.moveSnake(direction);

                } else {
                    // If the game is not running then on touching any part of the screen
                    // we start the game by sending MOVE_UP signal to SnakeView
                    mSnakeView.moveSnake(MOVE_UP);
                }
                return false;
            }
        });

        /** Create method to play the sound when the SnakeMovementListener is called in SnakeView
         * and onEventOccurred is called*/
        mSnakeView.setSnakeMovementListener(new SnakeView.SnakeMovementListener() {
            @Override
            public void onEventOccurred() {
                eatSound.start();
                // and whatever else I want to do when I update the snake!
            }
        });

        dbHelper = new UserScoreOpenHelper(this);
        userScores = readScoresFromDB(1);
        if (!userScores.isEmpty()) {
            showScore(userScores.get(0));
        }
        mSnakeView.setGameOverListener(new SnakeView.GameOverListener() {
            @Override
            public void onEventOccurred(long score) {
                writeScoreToDB(score);
            }
        });

        mUserScoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userScores = readScoresFromDB(5);
                UserScoresDialogFragment dialog = new UserScoresDialogFragment()
                        .newInstance((ArrayList<UserScore>) userScores);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                String tag = "tag";
                dialog.show(ft, tag);
            }
        });

        // Initialize DatabaseReference
//        mDatabase = FirebaseDatabase.getInstance().getReference();

    }

    @Override
    protected void onPause() {
        super.onPause();
        // Pause the game along with the activity
        mSnakeView.setMode(SnakeView.PAUSE);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Store the game state
        outState.putBundle(ICICLE_KEY, mSnakeView.saveState());
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }

    /**
     * Handles key events in the game. Update the direction our snake is traveling based on the
     * DPAD.
     *
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent msg) {

        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_UP:
                mSnakeView.moveSnake(MOVE_UP);
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                mSnakeView.moveSnake(MOVE_RIGHT);
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                mSnakeView.moveSnake(MOVE_DOWN);
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                mSnakeView.moveSnake(MOVE_LEFT);
                break;
        }

        return super.onKeyDown(keyCode, msg);
    }

    private void writeScoreToDB(long score) {
        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(UserScoreReaderContract.UserScoreFeedEntry.USERNAME, USERNAME_PLACEHOLDER);
        values.put(UserScoreReaderContract.UserScoreFeedEntry.SCORE_DATE, System.currentTimeMillis());
        values.put(UserScoreReaderContract.UserScoreFeedEntry.SCORE, score);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(UserScoreReaderContract.UserScoreFeedEntry.DICTIONARY_TABLE_NAME,
                null, values);
    }

    private List<UserScore> readScoresFromDB(int limitQuery) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                UserScoreReaderContract.UserScoreFeedEntry.USERNAME,
                UserScoreReaderContract.UserScoreFeedEntry.SCORE_DATE,
                UserScoreReaderContract.UserScoreFeedEntry.SCORE
        };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                UserScoreReaderContract.UserScoreFeedEntry.SCORE_DATE + " DESC";

        Cursor cursor = db.query(
                UserScoreReaderContract.UserScoreFeedEntry.DICTIONARY_TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder,// The sort order
                Integer.toString(limitQuery)
        );

        List<UserScore> scores = new ArrayList<UserScore>();
        while(cursor.moveToNext()) {
            String username = cursor.getString(
                    cursor.getColumnIndexOrThrow(UserScoreReaderContract.UserScoreFeedEntry.USERNAME));;
            long date = cursor.getLong(
                    cursor.getColumnIndexOrThrow(UserScoreReaderContract.UserScoreFeedEntry.SCORE_DATE));
            int score = cursor.getInt(
                    cursor.getColumnIndexOrThrow(UserScoreReaderContract.UserScoreFeedEntry.SCORE));;
            scores.add(new UserScore(username, date, score));
        }
        cursor.close();
        return scores;
    }

    private void showScore(UserScore userScore) {
        if (userScore != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Your last score was " + userScore.score +
                    " on " + DateFormat.getInstance().format(userScore.date)
            + "!")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //do things
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    public void basicReadWrite() {
        // Write a message to the database

        myRef.setValue("Hello, World!");
    }

    // Read from the database
//        myRef.addValueEventListener(new ValueEventListener() {
//
//        @Override
//        public void onDataChange(DataSnapshot dataSnapshot) {
//            // This method is called once with the initial value and again
//            // whenever data at this location is updated.
//            String value = dataSnapshot.getValue(String.class);
//            Log.d(TAG, "Value is: " + value);
//        }
//
//        @Override
//        public void onCancelled(DatabaseError error) {
//            // Failed to read value
//            Log.w(TAG, "Failed to read value.", error.toException());
//        }
//    });




}
