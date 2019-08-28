package com.example.android.snake;

import android.provider.BaseColumns;

public class UserScoreReaderContract {

    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private UserScoreReaderContract() {}

    /* Inner class that defines the table contents */
    public static class UserScoreFeedEntry implements BaseColumns {
        public static final String DICTIONARY_TABLE_NAME = "userscore";
        public static final String USERNAME = "username";
        public static final String SCORE_DATE = "date";
        public static final String SCORE = "score";
    }
}
