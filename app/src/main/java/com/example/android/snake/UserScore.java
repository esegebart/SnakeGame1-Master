package com.example.android.snake;

public class UserScore {

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
