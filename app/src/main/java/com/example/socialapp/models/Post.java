package com.example.socialapp.models;

import java.util.ArrayList;

public class Post {
    public String text;
    public User createdBy;
    public long createdAt;
    public ArrayList<String> likedBy = new ArrayList<>();

    public Post(String text, User createdBy, Long createdAt){
        this.text = text;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
    }

    public Post(){
    }
}
