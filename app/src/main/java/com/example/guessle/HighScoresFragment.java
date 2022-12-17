package com.example.guessle;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.guessle.db.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class HighScoresFragment extends Fragment {

    View view;
    private RecyclerView recyclerView;
    private ScoreRecyclerViewAdapter courseRecyclerViewAdapter;
    private int columnCount = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_high_scores, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Context context = getContext();
        courseRecyclerViewAdapter = new ScoreRecyclerViewAdapter(new ArrayList<User>());

        if(columnCount <= 1){
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        }
        else
        {
            recyclerView.setLayoutManager(new GridLayoutManager(context, columnCount));
        }

        recyclerView.setAdapter(courseRecyclerViewAdapter);
        recyclerView.setHasFixedSize(false);

        ViewModelProviders.of(this)
                .get(AllScoreViewModel.class)
                .getScoreList(context)
                .observe(this, new Observer<List<User>>() {
                    @Override
                    public void onChanged(List<User> users) {
                        Collections.sort(users, new Comparator<User>() {
                            @Override
                            public int compare(User lhs, User rhs) {
                                return lhs.getGameStreak() > rhs.getGameStreak() ? -1 : (lhs.getGameStreak() < rhs.getGameStreak() ) ? 1 : 0;
                            }
                        });
                        if(users != null){
                            courseRecyclerViewAdapter.addItem(users);
                        }

                    }
                });
    }
}