package com.example.guessle;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

public class LaunchFragment extends Fragment {

    View view;
    Button btnNewGame;
    Button btnHighScores;
    TextInputEditText username;
    String usersName;

    public interface LaunchButtons{
        public void newGameClicked(String username);
        public void highScoresClicked();
    }

    LaunchButtons loopback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_launch, container, false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        btnNewGame = (Button) view.findViewById(R.id.btnNewGame);
        btnHighScores = (Button) view.findViewById(R.id.btnHighScores);
        username = (TextInputEditText) view.findViewById(R.id.txtUsername);

        btnNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usersName = username.getText().toString();
                if(username != null && usersName.trim() != "" && usersName != " ")
                    loopback.newGameClicked(usersName);
            }
        });
        btnHighScores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loopback.highScoresClicked();
            }
        });
    }

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);

        try
        {
            loopback = (LaunchButtons) activity;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException( activity.toString()
                    + " must implement GameFragment.OnGamePlayListener");
        }
    }
}