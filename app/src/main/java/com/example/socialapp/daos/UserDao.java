package com.example.socialapp.daos;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.socialapp.models.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class UserDao {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference userCollection = db.collection("users");

    public void addUser(@NonNull User user){
        userCollection.document(user.uid).set(user);
    }
    public Task<DocumentSnapshot> getUserById(String uId){
        return userCollection.document(uId).get();
    }
}
