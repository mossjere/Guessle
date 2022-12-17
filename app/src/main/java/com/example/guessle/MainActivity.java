package com.example.guessle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.guessle.db.AppDatabase;
import com.example.guessle.db.User;
import com.example.guessle.db.Word;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity implements LaunchFragment.LaunchButtons, GameFragment.GameCompleted, GameFinishedFragment.GameOverInterface {

    FragmentManager fm;
    LaunchFragment launchFragment;
    GameFragment gameFragment;
    GetWord getWord;

    Word word;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        launchFragment = new LaunchFragment();

        fm = getSupportFragmentManager();
        fm.beginTransaction()
            .replace(R.id.displayedFragment, launchFragment, "LaunchFragment")
            .commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuItemExitGame:
                fm = getSupportFragmentManager();
                fm.beginTransaction()
                        .replace(R.id.displayedFragment, launchFragment, "LaunchFragment")
                        .commit();
                return true;

            case R.id.menuItemHint:
                if(word == null)
                    return true;
                Random rand = new Random();
                int randIndex = rand.nextInt(5);
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("Hint")
                        .setMessage("The " + (randIndex+1) + " letter of the word is " + word.getWord().charAt(randIndex))
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .create();
                dialog.show();
                return true;

            case R.id.menuItemHowToPlay:
                AlertDialog dialog2 = new AlertDialog.Builder(this)
                        .setTitle("How to play")
                        .setMessage("Each game you get 5 guesses.  After each guess if the letter is in the right location it turns green.  If the letter is in the word but in the wrong location it turns yellow")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .create();
                dialog2.show();

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void newGameClicked(String username) {
        if(username == "" || username == null)
            return;
        startGame(username, 0);
    }

    @Override
    public void highScoresClicked() {
        fm.beginTransaction()
                .replace(R.id.displayedFragment, new HighScoresFragment(), "GameFragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gameFinishedDialogClosed(boolean gameWon, String username, int score) {
        Log.d("MainActivity", "Username: " + username + " , score: " + score);
        if(gameWon)
            startGame(username, score);
        else {
        fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.displayedFragment, launchFragment, "LaunchFragment")
                .commit();
        User user = new User(username, score);
        new Thread(new Runnable() {
            @Override
            public void run() {
                AppDatabase.getInstance(getApplicationContext())
                        .userDAO()
                        .insert(user);
            }
        }).start();
        }
    }

    @Override
    public void gameOver(boolean gameWon, String username, int score) {
        new GameFinishedFragment(gameWon, word.getWord(), username, score).show(fm,"GameFinishedFragment");

    }

    public void startGame(String username, int score)
    {
        fm = getSupportFragmentManager();

        getWord = new GetWord();
        getWord.setOnWordGenerationListener(new GetWord.OnWordGeneration() {
            @Override
            public void completeWordList(Word[] words) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Random rand = new Random();
                        int wordIndex = rand.nextInt(words.length);
                        word = words[wordIndex];
                        Log.d("MainActivity", "Random Word: " + words[wordIndex].getWord());
                        gameFragment = new GameFragment(word, username, score);

                        fm.beginTransaction()
                                .replace(R.id.displayedFragment, gameFragment, "GameFragment")
                                .commit();
                    }
                }).start();
            }
        });
        getWord.execute("");
    }
}