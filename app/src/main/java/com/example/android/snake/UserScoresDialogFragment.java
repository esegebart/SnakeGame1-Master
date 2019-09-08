package com.example.android.snake;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class UserScoresDialogFragment extends DialogFragment {

    public final String LIST_OF_SCORES = "user_score_list";

    private RecyclerView rvScores;
    private UserScoreAdapter adapter;
    private List<UserScore> userScores;

    public UserScoresDialogFragment newInstance(ArrayList<UserScore> userScores) {
        Bundle args = new Bundle();
        args.putSerializable(LIST_OF_SCORES, userScores);
        UserScoresDialogFragment newFragment = new UserScoresDialogFragment ();
        newFragment.setArguments(args);
        return newFragment;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            userScores = (List<UserScore>) bundle.getSerializable(LIST_OF_SCORES);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_user_scores_dialog, null);
        LinearLayout layout = view.findViewById(R.id.dialog_user_scores);
        rvScores = view.findViewById(R.id.recycler_view_scores);
        setUpAdapter(userScores);
        builder.setView(layout);
        builder.setTitle(R.string.user_scores)
                .setCancelable(true)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //do things
                    }
                });

        return builder.create();
    }

    private void setUpAdapter(List<UserScore> userScores) {
        adapter = new UserScoreAdapter(userScores);
        rvScores.setAdapter(adapter);
        rvScores.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter.notifyDataSetChanged();
    }
}
