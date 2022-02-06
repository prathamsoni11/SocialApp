package com.example.socialapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.socialapp.daos.PostDao;

public class CreatePostActivity extends AppCompatActivity {

    EditText postInput;
    Button postButton;
    PostDao postDao = new PostDao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        postInput = findViewById(R.id.postInput);
        postButton = findViewById(R.id.postButton);

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = postInput.getText().toString().trim();
                if (!input.isEmpty()){
                    postDao.addPost(input);
                    finish();
                }
            }
        });
    }
}