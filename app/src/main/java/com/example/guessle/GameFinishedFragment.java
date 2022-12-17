package com.example.guessle;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class GameFinishedFragment extends DialogFragment {

    private boolean gameWon;
    String word;
    String username;
    int score;
    public GameFinishedFragment(){}
    public GameFinishedFragment(boolean gameWon, String word, String username, int score)
    {
        this.gameWon = gameWon;
        this.word = word;
        this.username = username;
        this.score = score;
    }

    private GameOverInterface listener;

    public interface GameOverInterface{
        void gameFinishedDialogClosed(boolean gameWon, String username, int score);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof GameOverInterface)
        {
            listener = (GameOverInterface) context;
        }
        else
        {
            throw new ClassCastException(context.toString() + " Must implement TooMuchChangeDialogDismissed");
        }
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        listener.gameFinishedDialogClosed(gameWon, username, score);
    }

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        if(gameWon) {
            builder.setTitle("Congratulations")
                    .setMessage("You won!! The word was " + word )
                    //TODO add definitions here
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
        }
        else
        {
            builder.setTitle("Game Over")
                    .setMessage("The word was " + word)
                    //TODO add definitions here
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
        }
        return builder.create();
    }
}