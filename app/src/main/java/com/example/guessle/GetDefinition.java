package com.example.guessle;

import android.os.AsyncTask;
import android.util.Log;

import com.example.guessle.db.User;
import com.example.guessle.db.Word;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class GetDefinition extends AsyncTask<String, Integer, String> {

    private String rawJSON;

    private OnDefinitionGeneration listener;

    public interface OnDefinitionGeneration{
        void completeWordList(Word[] word);
        String getWordFromMain();
    }


    public void setOnDefinitionGenerationListener(OnDefinitionGeneration listenerFromMain){
        listener = listenerFromMain;
    }

    @Override
    protected String doInBackground(String... strings) {
        try{
            String wordFromMain = listener.getWordFromMain();

            URL url = new URL("https://api.datamuse.com/words?sp=" + wordFromMain + "&md=d");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            int status = connection.getResponseCode();

            switch (status)
            {
                case 200:
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    rawJSON = bufferedReader.readLine();
                    Log.d("GetCanvasClasses", "doInBackground: " + rawJSON.toString());
                    break;
            }
        }
        catch (Exception e){
            Log.d("GetCanvasClasses", "doInBackground " + e.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        Word[] word;
        try {
            word = parseJson();
            listener.completeWordList(word);
        }catch (Exception e)
        {
            Log.d("getWord", "onPostExecute: " + e.getMessage());
        }
        super.onPostExecute(s);
    }

    private Word[] parseJson(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        Word[] word = null;
        try {
            word = gson.fromJson(rawJSON, Word[].class);
        }catch (Exception e){
            Log.d("GetWord", "parseJson: " + e.getMessage());
        }
        return word;
    }

    public Word[] doStuff()
    {
        try{
            Random rand = new Random();
            char[] vowels = {'a','e','i','o','u','y'};
            int vowel = rand.nextInt(6);
            int vowelPlacement = rand.nextInt(5);
            String wordRequest = "";
            for(int i = 0; i < 5; i++)
            {
                if(i == vowelPlacement)
                    wordRequest += vowels[vowel];
                else
                    wordRequest += "?";
            }

            URL url = new URL("https://api.datamuse.com/words?sp=" + wordRequest);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            int status = connection.getResponseCode();

            switch (status)
            {
                case 200:
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    rawJSON = bufferedReader.readLine();
                    Log.d("GetCanvasClasses", "doInBackground: " + rawJSON.toString());
                    break;
            }
        }
        catch (Exception e){
            Log.d("GetCanvasClasses", "doInBackground " + e.getMessage());
        }
        return parseJson();
    }

}
