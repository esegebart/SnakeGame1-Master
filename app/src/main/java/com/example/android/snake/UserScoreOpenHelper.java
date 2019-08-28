package com.example.android.snake;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserScoreOpenHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String USERSCORE_TABLE_NAME = "userscore";
    private static final String USERSCORE_TABLE_CREATE =
            "CREATE TABLE " + DICTIONARY_TABLE_NAME + " (" +
                    USERNAME + " TEXT, " +
                    SCORE_DATE + " DATE, " +
                    SCORE + " TEXT);";

    UserScoreOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(USERSCORE_TABLE_CREATE);
    }

}
