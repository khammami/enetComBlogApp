package com.example.blogapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.blogapp.PostRepository;
import com.example.blogapp.model.Post;

import java.util.List;

public class PostViewModel extends AndroidViewModel {

    private PostRepository mRepository;

    private LiveData<List<Post>> mAllPosts;

    public PostViewModel(@NonNull Application application) {
        super(application);
        mRepository = new PostRepository(application);
        mAllPosts = mRepository.getAllPosts();
    }

    public LiveData<List<Post>> getAllPosts() {
        return mAllPosts;
    }

    public void insert(Post post) {
        mRepository.insert(post);
    }

    public void deleteAll() {
        mRepository.deleteAll();
    }

    public void deletePost(Post post) {
        mRepository.deletePost(post);
    }

    public void update(Post post) {
        mRepository.update(post);
    }
}
