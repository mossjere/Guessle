package com.example.guessle;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.guessle.db.User;


import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ScoreRecyclerViewAdapter extends RecyclerView.Adapter<ScoreRecyclerViewAdapter.ViewHolder> {

    public final List<User> users;

    public ScoreRecyclerViewAdapter(List<User> users) {
        this.users = users;
        Collections.sort(this.users, new Comparator<User>() {
            public int compare(User o1, User o2) {
                // compare two instance of `Score` and return `int` as result.
                if(o1.getGameStreak() >= o2.getGameStreak())
                    return 1;
                if(o1.getGameStreak() == o2.getGameStreak())
                    return 0;
                return -1;
            }
        });
    }


    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final User user = users.get(position);
        if(user != null){
            holder.txtUserName.setText(user.getName());
            holder.txtUserScore.setText(user.getGameStreak() + "");


//            holder.view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Bundle bundle = new Bundle();
//                    bundle.putInt("user_pk", user.getUserID());
//
//                    CourseDetailsFragment courseDetailsFragment = new CourseDetailsFragment();
//                    courseDetailsFragment.setArguments(bundle);
//
//                    AppCompatActivity activity = (AppCompatActivity) v.getContext();
//                    activity.getSupportFragmentManager()
//                            .beginTransaction()
//                            .add(android.R.id.content, courseDetailsFragment)
//                            .addToBackStack(null)
//                            .commit();
//
//                }
//            });
        }
    }

    public void addItem(List<User> users) {
        this.users.clear();
        this.users.addAll(users);
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        public View view;
        public User user;
        public TextView txtUserName, txtUserScore;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            view = itemView;
            txtUserName = view.findViewById(R.id.riUserName);
            txtUserScore = view.findViewById(R.id.riUserScore);
        }
    }



    @Override
    public int getItemCount() {
        return users.size();
    }
}