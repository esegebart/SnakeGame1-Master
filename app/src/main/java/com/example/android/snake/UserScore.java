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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
