package com.example.blogapp.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.blogapp.model.Post;

@Database(entities = {Post.class}, version = 2, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class PostRoomDataBase extends RoomDatabase {

    public abstract PostDao postDao();

    private static PostRoomDataBase INSTANCE;

    public static PostRoomDataBase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (PostRoomDataBase.class) {
                if (INSTANCE == null) {
                    // Create database here.
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            PostRoomDataBase.class, "post_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
