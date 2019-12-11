package com.example.blogapp;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.blogapp.db.PostDao;
import com.example.blogapp.db.PostRoomDataBase;
import com.example.blogapp.model.Post;

import java.util.List;

public class PostRepository {

    private PostDao mPostDao;
    private LiveData<List<Post>> mAllPosts;

    public PostRepository(Application application) {
        PostRoomDataBase db = PostRoomDataBase.getDatabase(application);
        mPostDao = db.postDao();
        mAllPosts = mPostDao.getAllPosts();
    }

    public LiveData<List<Post>> getAllPosts() {
        return mAllPosts;
    }

    public void insert(Post post) {
        new insertAsyncTask(mPostDao).execute(post);
    }

    public void update(Post post)  {
        new updatePostAsyncTask(mPostDao).execute(post);
    }

    public void deleteAll()  {
        new deleteAllPostsAsyncTask(mPostDao).execute();
    }

    // Must run off main thread
    public void deletePost(Post post) {
        new deletePostAsyncTask(mPostDao).execute(post);
    }

    // Static inner classes below here to run database interactions in the background.

    /**
     * Inserts a post into the database.
     */
    private static class insertAsyncTask extends AsyncTask<Post, Void, Void> {

        private PostDao mAsyncTaskDao;

        insertAsyncTask(PostDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Post... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    /**
     * Deletes all posts from the database (does not delete the table).
     */
    private static class deleteAllPostsAsyncTask extends AsyncTask<Void, Void, Void> {
        private PostDao mAsyncTaskDao;

        deleteAllPostsAsyncTask(PostDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

    /**
     *  Deletes a single post from the database.
     */
    private static class deletePostAsyncTask extends AsyncTask<Post, Void, Void> {
        private PostDao mAsyncTaskDao;

        deletePostAsyncTask(PostDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Post... params) {
            mAsyncTaskDao.deletePost(params[0]);
            return null;
        }
    }

    /**
     *  Updates a post in the database.
     */
    private static class updatePostAsyncTask extends AsyncTask<Post, Void, Void> {
        private PostDao mAsyncTaskDao;

        updatePostAsyncTask(PostDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Post... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }
}
