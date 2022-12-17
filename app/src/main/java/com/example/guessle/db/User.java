package com.example.guessle.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {

    @PrimaryKey(autoGenerate = true)
    private int UserID;

    private String name;

    private int gameStreak;

    public User(String name, int gameStreak) {
        this.name = name;
        this.gameStreak = gameStreak;
    }

    @Override
    public String toString() {
        return "User{" +
                "UserID=" + UserID +
                ", name='" + name + '\'' +
                ", gameStreak=" + gameStreak +
                '}';
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGameStreak() {
        return gameStreak;
    }

    public void setGameStreak(int gameStreak) {
        this.gameStreak = gameStreak;
    }
}
