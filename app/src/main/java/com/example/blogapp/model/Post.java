package com.example.blogapp.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "post_table")
public class Post {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "title")
    private String mTitle;

    @ColumnInfo(name = "desc")
    private String mContent;

    @NonNull
    @ColumnInfo(name = "published_on")
    private Date mPostDate;

    public Post( String title, String content, @NonNull Date postDate){
        this.mTitle = title;
        this.mContent = content;
        this.mPostDate = postDate;
    }

    @Ignore
    public Post(int id, String title, String content, @NonNull Date postDate){
        this.id = id;
        this.mTitle = title;
        this.mContent = content;
        this.mPostDate = postDate;
    }

    @Ignore
    public Post(){}

    public int getId() {
        return id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String mContent) {
        this.mContent = mContent;
    }

    public Date getPostDate() {
        return mPostDate;
    }

    public void setPostDate(Date mPostDate) {
        this.mPostDate = mPostDate;
    }

    public void setId(int id) {
        this.id = id;
    }
}
