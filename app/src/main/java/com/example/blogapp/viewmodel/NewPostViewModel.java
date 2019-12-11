package com.example.blogapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.blogapp.BasicApp;
import com.example.blogapp.PostRepository;
import com.example.blogapp.model.Post;


public class NewPostViewModel extends AndroidViewModel {
    private PostRepository mRepository;


    public NewPostViewModel(@NonNull Application application) {
        super(application);
        mRepository = ((BasicApp) application).getRepository();
    }

    public LiveData<Post> getPostById(int id) {
        return mRepository.getPostById(id);
    }

    public void insert(Post post) {
        mRepository.insert(post);
    }

    public void update(Post post) {
        mRepository.update(post);
    }

}
