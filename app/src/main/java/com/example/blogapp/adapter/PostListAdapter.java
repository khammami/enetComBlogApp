package com.example.blogapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blogapp.model.Post;
import com.example.blogapp.R;

import java.util.List;

public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.PostViewHolder> {

    private final LayoutInflater mInflater;
    private List<Post> mPosts; // Cached copy of words
    private static ClickListener clickListener;

    public PostListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new PostViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        if (mPosts != null) {
            Post current = mPosts.get(position);
            holder.titleItemView.setText(current.getTitle());
            holder.contentItemView.setText(current.getContent());
            holder.dateItemView.setText(current.getPostDate().toString());
        }
    }

    public void setPosts(List<Post> posts) {
        mPosts = posts;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mPosts != null)
            return mPosts.size();
        else return 0;
    }

    public Post getPostAtPosition(int position) {
        return mPosts.get(position);
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {

        private final TextView titleItemView;
        private final TextView contentItemView;
        private final TextView dateItemView;


        public PostViewHolder(@NonNull View itemView) {
            super(itemView);

            titleItemView = itemView.findViewById(R.id.itemTitleTetView);
            contentItemView = itemView.findViewById(R.id.itemContentTetView);
            dateItemView = itemView.findViewById(R.id.itemDateTetView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onItemClick(view, getAdapterPosition());
                }
            });
        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        PostListAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(View v, int position);
    }
}
