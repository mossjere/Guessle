package com.example.guessle;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.guessle.db.AppDatabase;
import com.example.guessle.db.User;

import java.util.List;

public class AllScoreViewModel extends ViewModel {
    private LiveData<List<User>> courseList;

    public LiveData<List<User>> getScoreList(Context c)
    {
        if(courseList == null)
            courseList = AppDatabase.getInstance(c).userDAO().getAll();
        return courseList;
    }
}
