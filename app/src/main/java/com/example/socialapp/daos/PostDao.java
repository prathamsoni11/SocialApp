package com.example.socialapp.daos;

import com.example.socialapp.models.Post;
import com.example.socialapp.models.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class PostDao {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    public final CollectionReference postCollection = db.collection("posts");
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public void addPost(String text){
        String currentUser = mAuth.getCurrentUser().getUid();
        UserDao userDao = new UserDao();
        Task<DocumentSnapshot> task = userDao.getUserById(currentUser);
        task.addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User user = documentSnapshot.toObject(User.class);

                Long currentTime = System.currentTimeMillis();
                Post post = new Post(text,user,currentTime);
                postCollection.document().set(post);
            }
        });
    }

    public Task<DocumentSnapshot> getPostById(String postId){
        return postCollection.document(postId).get();
    }

    public void updateLikes(String postId){
        Task<DocumentSnapshot> task = getPostById(postId);
        task.addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String currentUserId = mAuth.getCurrentUser().getUid();
                Post post = documentSnapshot.toObject(Post.class);
                boolean isLiked = post.likedBy.contains(currentUserId);

                if (isLiked){
                    post.likedBy.remove(currentUserId);
                }else {
                    post.likedBy.add(currentUserId);
                }
                postCollection.document(postId).set(post);
            }
        });
    }
}
