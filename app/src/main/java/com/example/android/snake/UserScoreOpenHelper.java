package com.example.android.snake;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.android.snake.UserScoreContract.UserScoreEntry.COLUMN_DATE;
import static com.example.android.snake.UserScoreContract.UserScoreEntry.COLUMN_SCORE;
import static com.example.android.snake.UserScoreContract.UserScoreEntry.COLUMN_USERNAME;
import static com.example.android.snake.UserScoreContract.UserScoreEntry.USER_SCORE_LIST;

public class UserScoreOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "userScores.db";

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    private static final int DATABASE_VERSION = 2;

    private static final String USERSCORE_TABLE_CREATE =
            "CREATE TABLE " + USER_SCORE_LIST + " (" +
                    COLUMN_USERNAME + " TEXT, " +
                    COLUMN_DATE + " DATE, " +
                    COLUMN_SCORE + " TEXT);";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + USER_SCORE_LIST;

    UserScoreOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(USERSCORE_TABLE_CREATE);
    }

}
