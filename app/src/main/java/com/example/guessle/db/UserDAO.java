package com.example.guessle.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDAO {
    @Query("SELECT * FROM User")
    LiveData<List<User>> getAll();

    @Query("SELECT * FROM User WHERE UserID =:id")
    List<User> getByID(int id);

    @Update
    void update(User course);

    @Delete
    void delete(User course);

    @Insert
    void insert(User... course);
}
