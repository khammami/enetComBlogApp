package com.example.blogapp;

import android.app.Application;

import com.example.blogapp.db.PostRoomDataBase;

public class BasicApp extends Application {

    private AppExecutors mAppExecutors;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppExecutors = new AppExecutors();
    }

    public PostRoomDataBase getDatabase() {
        return PostRoomDataBase.getInstance(this);
    }

    public PostRepository getRepository() {
        return PostRepository.getInstance(getDatabase(), mAppExecutors);
    }
}
