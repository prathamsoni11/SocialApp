package com.example.socialapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.socialapp.models.Post;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;

public class PostAdapter extends FirestoreRecyclerAdapter<Post, PostAdapter.PostViewHolder> {

    IPostAdapter listener;
    public PostAdapter(@NonNull FirestoreRecyclerOptions<Post> options,IPostAdapter listener) {
        super(options);
        this.listener = listener;
    }

    @Override
    protected void onBindViewHolder(@NonNull PostAdapter.PostViewHolder holder, int position, @NonNull Post model) {
        holder.postText.setText(model.text);
        holder.userText.setText(model.createdBy.displayName);
        Glide.with(holder.userImage.getContext()).load(model.createdBy.imageUrl).circleCrop().into(holder.userImage);
        Utils utils = new Utils();
        holder.createdAt.setText(utils.getTimeAgo(model.createdAt));
        holder.likeCount.setText(String.valueOf(model.likedBy.size()));

        FirebaseAuth auth = FirebaseAuth.getInstance();
        String currentUserId = auth.getCurrentUser().getUid();
        boolean isLiked = model.likedBy.contains(currentUserId);
        if (isLiked){
            holder.likeButton.setImageDrawable(ContextCompat.getDrawable(holder.likeButton.getContext(),R.drawable.ic_liked));
        }else {
            holder.likeButton.setImageDrawable(ContextCompat.getDrawable(holder.likeButton.getContext(),R.drawable.ic_unliked));
        }
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PostViewHolder viewHolder = new PostViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false));
        viewHolder.likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onLikeClicked(getSnapshots().getSnapshot(viewHolder.getAdapterPosition()).getId());
            }
        });
        return viewHolder;
    }

    static class PostViewHolder extends RecyclerView.ViewHolder {

        TextView postText,userText,createdAt,likeCount;
        ImageView userImage,likeButton;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            postText = itemView.findViewById(R.id.postTitle);
            userText = itemView.findViewById(R.id.userName);
            createdAt = itemView.findViewById(R.id.createdAt);
            likeCount = itemView.findViewById(R.id.likeCount);
            userImage = itemView.findViewById(R.id.userImage);
            likeButton = itemView.findViewById(R.id.likeButton);
        }
    }
}
interface IPostAdapter {
    void onLikeClicked(String postId);
}
