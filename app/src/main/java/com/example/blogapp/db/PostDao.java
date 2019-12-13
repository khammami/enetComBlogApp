package com.example.blogapp.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.blogapp.model.Post;

import java.util.List;

@Dao
public interface PostDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Post post);

    @Query("DELETE FROM post_table")
    void deleteAll();

    @Delete
    void deletePost(Post post);


    @Query("SELECT * from post_table ORDER BY published_on DESC")
    LiveData<List<Post>> getAllPosts();

    @Update
    void update(Post... post);
}
