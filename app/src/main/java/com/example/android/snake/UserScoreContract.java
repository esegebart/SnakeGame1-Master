package com.example.android.snake;

import android.provider.BaseColumns;

public final class UserScoreContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private UserScoreContract(){};

    /**Inner class that defines the table contents*/
    public static class UserScoreEntry implements BaseColumns {
        public static final String USER_SCORE_LIST = "userScoreList";
        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_SCORE = "score";
        public static final String COLUMN_DATE = "date";
    }
}
