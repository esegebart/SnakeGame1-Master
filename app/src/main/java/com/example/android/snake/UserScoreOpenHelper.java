package com.example.android.snake;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserScoreOpenHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "UserScore.db";

    private static final String USERSCORE_TABLE_CREATE =
            "CREATE TABLE " + UserScoreReaderContract.UserScoreFeedEntry.DICTIONARY_TABLE_NAME + " (" +
                    UserScoreReaderContract.UserScoreFeedEntry.USERNAME + " TEXT, " +
                    UserScoreReaderContract.UserScoreFeedEntry.SCORE_DATE + " LONG, " +
                    UserScoreReaderContract.UserScoreFeedEntry.SCORE + " TEXT);";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + UserScoreReaderContract.UserScoreFeedEntry.DICTIONARY_TABLE_NAME;

    UserScoreOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(USERSCORE_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);

    }

}
