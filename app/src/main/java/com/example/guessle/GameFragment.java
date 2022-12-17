package com.example.guessle;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.guessle.db.Word;
import com.google.android.material.internal.TextWatcherAdapter;
import com.google.android.material.textfield.TextInputEditText;


public class GameFragment extends Fragment {
private
    View view;
    private TextView[] word1, word2, word3, word4, word5;
    private TextInputEditText tempWord1, tempWord2, tempWord3, tempWord4, tempWord5;
    private LinearLayout layout1, layout2, layout3, layout4, layout5;
    private boolean gameOver = false;
    private boolean[] wordGuessed = {false, false, false, false, false};
    private Word word;
    private Button submitButton;
    private String username;
    private int score;

    public GameCompleted listener;

    public interface GameCompleted{
        public void gameOver(boolean gameWon, String username, int score);
    }


    public GameFragment() {
    }
    public GameFragment(Word word, String username, int score) {
        this.word = word;
        this.username = username;
        this.score = score;
        Log.d("GameFragment" , "Word passed: " + word.getWord());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_game, container, false);
        return view;
    }

    @Override
    public void onResume() {

        word1 = new TextView[5];
        word2 = new TextView[5];
        word3 = new TextView[5];
        word4 = new TextView[5];
        word5 = new TextView[5];

        tempWord1 = view.findViewById(R.id.tempWord1Text);
        tempWord2 = view.findViewById(R.id.tempWord2Text);
        tempWord3 = view.findViewById(R.id.tempWord3Text);
        tempWord4 = view.findViewById(R.id.tempWord4Text);
        tempWord5 = view.findViewById(R.id.tempWord5Text);

        word1[0] = view.findViewById(R.id.word1Letter1);
        word1[1] = view.findViewById(R.id.word1Letter2);
        word1[2] = view.findViewById(R.id.word1Letter3);
        word1[3] = view.findViewById(R.id.word1Letter4);
        word1[4] = view.findViewById(R.id.word1Letter5);

        word2[0] = view.findViewById(R.id.word2Letter1);
        word2[1] = view.findViewById(R.id.word2Letter2);
        word2[2] = view.findViewById(R.id.word2Letter3);
        word2[3] = view.findViewById(R.id.word2Letter4);
        word2[4] = view.findViewById(R.id.word2Letter5);

        word3[0] = view.findViewById(R.id.word3Letter1);
        word3[1] = view.findViewById(R.id.word3Letter2);
        word3[2] = view.findViewById(R.id.word3Letter3);
        word3[3] = view.findViewById(R.id.word3Letter4);
        word3[4] = view.findViewById(R.id.word3Letter5);

        word4[0] = view.findViewById(R.id.word4Letter1);
        word4[1] = view.findViewById(R.id.word4Letter2);
        word4[2] = view.findViewById(R.id.word4Letter3);
        word4[3] = view.findViewById(R.id.word4Letter4);
        word4[4] = view.findViewById(R.id.word4Letter5);

        word5[0] = view.findViewById(R.id.word5Letter1);
        word5[1] = view.findViewById(R.id.word5Letter2);
        word5[2] = view.findViewById(R.id.word5Letter3);
        word5[3] = view.findViewById(R.id.word5Letter4);
        word5[4] = view.findViewById(R.id.word5Letter5);

        layout1 = (LinearLayout) view.findViewById(R.id.word1Layout);
        layout2 = (LinearLayout) view.findViewById(R.id.word2Layout);
        layout3 = (LinearLayout) view.findViewById(R.id.word3Layout);
        layout4 = (LinearLayout) view.findViewById(R.id.word4Layout);
        layout5 = (LinearLayout) view.findViewById(R.id.word5Layout);

        submitButton = view.findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click();
            }
        });

        View.OnKeyListener keyListener = new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP
                        && keyCode == KeyEvent.KEYCODE_ENTER) {
                    click();
                }

                return false;
            }
        };

        tempWord1.setOnKeyListener(keyListener);
        tempWord2.setOnKeyListener(keyListener);
        tempWord3.setOnKeyListener(keyListener);
        tempWord4.setOnKeyListener(keyListener);
        tempWord5.setOnKeyListener(keyListener);

        tempWord1.requestFocus();

        super.onResume();
    }


    private void click()
    {
        if(!wordGuessed[0])
        {
            if(tempWord1.getText().toString().length() >= 5) {
                wordGuessed[0] = true;
                for(int i = 0; i < 5; i++)
                {
                    word1[i].setText(Character.toLowerCase(tempWord1.getText().toString().charAt(i))+"");
                    if(Character.toLowerCase(tempWord1.getText().toString().charAt(i)) == Character.toLowerCase(word.getWord().charAt(i)))
                        word1[i].setBackground(ContextCompat.getDrawable(getContext(), R.drawable.ic_green_box_24));
                    else if(word.getWord().contains(Character.toLowerCase(tempWord1.getText().toString().charAt(i))+""))
                        word1[i].setBackground(ContextCompat.getDrawable(getContext(), R.drawable.ic_yellow_box_24));

                }
                tempWord1.setVisibility(View.GONE);
                layout1.setVisibility(View.VISIBLE);
                tempWord2.setEnabled(true);
                tempWord1.setEnabled(false);
            }
            if(tempWord1.getText().toString().equalsIgnoreCase(word.getWord()))
            {
                gameOver = true;
                endGame(true);
            }
        }
        else if(!wordGuessed[1])
        {
            if(tempWord2.getText().toString().length() >= 5) {
                wordGuessed[1] = true;
                for(int i = 0; i < 5; i++)
                {
                    word2[i].setText(tempWord2.getText().toString().charAt(i)+"");
                    if(Character.toLowerCase(tempWord2.getText().toString().charAt(i)) == Character.toLowerCase(word.getWord().charAt(i)))
                        word2[i].setBackground(ContextCompat.getDrawable(getContext(), R.drawable.ic_green_box_24));
                    else if(word.getWord().contains(Character.toLowerCase(tempWord2.getText().toString().charAt(i))+""))
                        word2[i].setBackground(ContextCompat.getDrawable(getContext(), R.drawable.ic_yellow_box_24));
                }
                tempWord2.setVisibility(View.GONE);
                layout2.setVisibility(View.VISIBLE);
                tempWord2.setEnabled(false);
                tempWord3.setEnabled(true);
                if(tempWord2.getText().toString().equalsIgnoreCase(word.getWord()))
                {
                    gameOver = true;
                    endGame(true);
                }
            }
        }
        else if(!wordGuessed[2])
        {
            if(tempWord3.getText().toString().length() >= 5) {
                wordGuessed[2] = true;
                for(int i = 0; i < 5; i++)
                {
                    word3[i].setText(tempWord3.getText().toString().charAt(i)+"");
                    if(Character.toLowerCase(tempWord3.getText().toString().charAt(i)) == Character.toLowerCase(word.getWord().charAt(i)))
                        word3[i].setBackground(ContextCompat.getDrawable(getContext(), R.drawable.ic_green_box_24));
                    else if(word.getWord().contains(Character.toLowerCase(tempWord3.getText().toString().charAt(i))+""))
                        word3[i].setBackground(ContextCompat.getDrawable(getContext(), R.drawable.ic_yellow_box_24));
                }
                tempWord3.setVisibility(View.GONE);
                layout3.setVisibility(View.VISIBLE);
                tempWord3.setEnabled(false);
                tempWord4.setEnabled(true);
                if(tempWord3.getText().toString().equalsIgnoreCase(word.getWord()))
                {
                    gameOver = true;
                    endGame(true);
                }
            }
        }
        else if(!wordGuessed[3])
        {
            if(tempWord4.getText().toString().length() >= 5) {
                wordGuessed[3] = true;
                for(int i = 0; i < 5; i++)
                {
                    word4[i].setText(tempWord4.getText().toString().charAt(i)+"");
                    if(Character.toLowerCase(tempWord4.getText().toString().charAt(i)) == Character.toLowerCase(word.getWord().charAt(i)))
                        word4[i].setBackground(ContextCompat.getDrawable(getContext(), R.drawable.ic_green_box_24));
                    else if(word.getWord().contains(Character.toLowerCase(tempWord4.getText().toString().charAt(i))+""))
                        word4[i].setBackground(ContextCompat.getDrawable(getContext(), R.drawable.ic_yellow_box_24));
                }
                tempWord4.setVisibility(View.GONE);
                layout4.setVisibility(View.VISIBLE);
                tempWord4.setEnabled(false);
                tempWord5.setEnabled(true);
                if(tempWord4.getText().toString().equalsIgnoreCase(word.getWord()))
                {
                    gameOver = true;
                    endGame(true);
                }
            }
        }
        else if(!wordGuessed[4])
        {
            if(tempWord5.getText().toString().length() >= 5) {
                wordGuessed[4] = true;
                for(int i = 0; i < 5; i++)
                {
                    word5[i].setText(tempWord5.getText().toString().charAt(i)+"");
                    if(Character.toLowerCase(tempWord5.getText().toString().charAt(i)) == Character.toLowerCase(word.getWord().charAt(i)))
                        word5[i].setBackground(ContextCompat.getDrawable(getContext(), R.drawable.ic_green_box_24));
                    else if(word.getWord().contains(Character.toLowerCase(tempWord5.getText().toString().charAt(i))+""))
                        word5[i].setBackground(ContextCompat.getDrawable(getContext(), R.drawable.ic_yellow_box_24));
                }
                tempWord5.setVisibility(View.GONE);
                layout5.setVisibility(View.VISIBLE);
                tempWord5.setEnabled(false);
                if(tempWord5.getText().toString().equalsIgnoreCase(word.getWord()))
                {
                    gameOver = true;
                    endGame(true);
                }
                else
                {
                    endGame(gameOver);
                }
            }
        }
    }

    private void endGame(boolean gameWon)
    {
        if(gameWon)
        {
            listener.gameOver(true, username, ++score);
        }
        else
        {
            listener.gameOver(false, username, score);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof GameCompleted)
        {
            listener = (GameCompleted) context;
        }
        else
        {
            throw new ClassCastException(context.toString() + " Must implement TooMuchChangeDialogDismissed");
        }
    }

}