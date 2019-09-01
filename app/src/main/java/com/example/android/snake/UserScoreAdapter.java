package com.example.android.snake;

import android.support.annotation.NonNull;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.List;

public class UserScoreAdapter extends RecyclerView.Adapter<UserScoreAdapter.UserScoreViewHolder> {

    private List<UserScore> userScores;

    public UserScoreAdapter(List<UserScore> userScores) {
        this.userScores = userScores;
    }

    @NonNull
    @Override
    public UserScoreViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_user_score, parent, false);
        return new UserScoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserScoreViewHolder userScoreViewHolder, int i) {
        userScoreViewHolder.onBindViewHolder(userScores.get(userScoreViewHolder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return userScores.size();
    }

    class UserScoreViewHolder extends RecyclerView.ViewHolder {

        TextView userName;
        TextView date;
        TextView score;

        UserScoreViewHolder(View view) {
            super(view);
            userName = view.findViewById(R.id.username);
            date = view.findViewById(R.id.date);
            score = view.findViewById(R.id.score);
        }

        void onBindViewHolder(UserScore userScore) {
            if (userScore.username != null) {
                userName.setText(userScore.username);
            }
            if (userScore.date != null) {
                date.setText(DateFormat.getInstance().format(userScore.date));
            }
            score.setText(Integer.toString(userScore.score));
        }

    }
}



