package com.example.android.snake;

import java.io.Serializable;

public class UserScore implements Serializable {

    public UserScore() {}

    public UserScore(String username, long date, int score) {
        this.username = username;
        this.date = date;
        this.score = score;
    }

    public String username;
    public Long date;
    public int score;


}
