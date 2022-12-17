package com.example.guessle.db;

public class Word {
    private String word;
    private int Score;
    private String[] defs;

    public String getWord() {
        return word;
    }

    public String[] getDefs() {
        return defs;
    }

    public void setDefs(String[] defs) {
        this.defs = defs;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getScore() {
        return Score;
    }

    public void setScore(int score) {
        Score = score;
    }

    public Word(String word, int score) {
        this.word = word;
        Score = score;
    }

    public Word(String word, int score, String[] defs) {
        this.word = word;
        Score = score;
        this.defs = defs;
    }
}
