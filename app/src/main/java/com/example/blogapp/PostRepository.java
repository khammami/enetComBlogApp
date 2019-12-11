package com.example.blogapp;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.blogapp.db.PostDao;
import com.example.blogapp.db.PostRoomDataBase;
import com.example.blogapp.model.Post;

import java.util.List;

public class PostRepository {

    private static PostRepository sInstance;
    private final AppExecutors mExecutors;

    private final PostRoomDataBase db;
    private LiveData<List<Post>> mAllPosts;

    public PostRepository(PostRoomDataBase database, AppExecutors executors) {
        db = database;
        mExecutors = executors;
        mAllPosts = db.postDao().getAllPosts();
    }

    public static PostRepository getInstance(final PostRoomDataBase database,
                                             final AppExecutors executors) {
        if (sInstance == null) {
            synchronized (PostRepository.class) {
                if (sInstance == null) {
                    sInstance = new PostRepository(database, executors);
                }
            }
        }
        return sInstance;
    }

    public LiveData<List<Post>> getAllPosts() {
        return mAllPosts;
    }

    public void insert(final Post post) {
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                db.postDao().insert(post);
            }
        });
    }

    public void update(final Post post) {
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                db.postDao().update(post);
            }
        });
    }

    public void deleteAll() {
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                db.postDao().deleteAll();
            }
        });
    }

    // Must run off main thread
    public void deletePost(final Post post) {
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                db.postDao().deletePost(post);
            }
        });
    }

    public LiveData<Post> getPostById(int id) {
        return db.postDao().getPostById(id);
    }
}
