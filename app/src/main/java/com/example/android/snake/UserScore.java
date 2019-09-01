package com.example.android.snake;

import java.io.Serializable;

public class UserScore implements Serializable {

    public UserScore() {}

    public UserScore(String username, long date, int score) {
        this.username = username;
        this.date = date;
        this.score = score;
    }

    String username;
    Long date;
    int score;


}
