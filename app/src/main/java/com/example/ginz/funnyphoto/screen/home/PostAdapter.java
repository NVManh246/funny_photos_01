package com.example.ginz.funnyphoto.screen.home;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ginz.funnyphoto.R;
import com.example.ginz.funnyphoto.data.model.Post;
import com.example.ginz.funnyphoto.data.model.User;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private static Context mContext;
    private static List<Post> mPosts;

    public PostAdapter(Context context, List<Post> mosts) {
        mContext = context;
        mPosts = mosts;
    }

    @Override
    public int getItemViewType(int position) {
        return position == mPosts.size() - 1 ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater =
                (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (viewType == VIEW_TYPE_ITEM) {
            View view = inflater.inflate(R.layout.item_post, parent, false);
            return new PostAdapter.PostViewHolder(view);
        }
        View view = inflater.inflate(R.layout.item_loading, parent, false);
        return new LoadingViewHodler(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder.getItemViewType() == VIEW_TYPE_ITEM) {
            PostViewHolder viewHolder = (PostViewHolder) holder;
            viewHolder.bindView(mContext, mPosts, position);
        }else {
            LoadingViewHodler loadingViewHodler = (LoadingViewHodler) holder;
            loadingViewHodler.bindView();
        }
    }

    @Override
    public int getItemCount() {
        return mPosts != null ? mPosts.size() : 0;
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageAvatar;
        private ImageView mImagePost;
        private ImageView mImageLike;
        private ImageView mImageComment;
        private ImageView mImageShare;
        private TextView mTextUserName;
        private TextView mTextPostTime;
        private TextView mTextContent;

        public PostViewHolder(View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            mImageAvatar = itemView.findViewById(R.id.image_avatar_post);
            mImagePost = itemView.findViewById(R.id.image_post);
            mImageLike = itemView.findViewById(R.id.image_like_post);
            mImageComment = itemView.findViewById(R.id.image_comment_post);
            mImageShare = itemView.findViewById(R.id.image_share_post);
            mTextUserName = itemView.findViewById(R.id.text_username_post);
            mTextPostTime = itemView.findViewById(R.id.text_time_post);
            mTextContent = itemView.findViewById(R.id.text_content_post);
            
        }

        private void bindView(Context context, List<Post> posts, int position){
            Post post = posts.get(position);
            User userPost = post.getUserPosted();
            Picasso.with(context).load(userPost.getAvatar()).into(mImageAvatar);
            Picasso.with(context).load(post.getImagePath()).into(mImagePost);
            mTextUserName.setText(userPost.getUsername());
            mTextPostTime.setText(post.getPostTime());
            mTextContent.setText(post.getTitle());
        }
    }

    public static class LoadingViewHodler extends RecyclerView.ViewHolder {

        private ProgressBar mProgressBar;

        public LoadingViewHodler(View itemView) {
            super(itemView);
            mProgressBar = itemView.findViewById(R.id.progressBar1);
        }

        private void bindView(){
            mProgressBar.setIndeterminate(true);
        }
    }

    public void showNewPost(List<Post> newPosts){
        if(newPosts.size() != 0){
            mPosts.addAll(newPosts);
            this.notifyItemRangeInserted(mPosts.size() - newPosts.size(), mPosts.size());
        }
    }
}
