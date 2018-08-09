package com.example.ginz.funnyphoto.data.source;

import android.support.annotation.NonNull;
import com.example.ginz.funnyphoto.data.model.Post;
import com.example.ginz.funnyphoto.data.source.remote.PostsRemoteDataSource;

public class PostsRepository implements PostsDataSource.PostRemoteDataSource {

    private static PostsRepository sInstance;
    private PostsDataSource.PostRemoteDataSource mRemoteDataSource;

    private PostsRepository(PostsRemoteDataSource postsRemoteDataSource){
        mRemoteDataSource = postsRemoteDataSource;
    }

    public static PostsRepository getInstance(PostsRemoteDataSource postsRemoteDataSource){
        if(sInstance == null){
            sInstance = new PostsRepository(postsRemoteDataSource);
        }
        return sInstance;
    }

    @Override
    public void getPost(@NonNull int page,
                        @NonNull PostsDataSource.OnPostCompleteListener callback) {
        mRemoteDataSource.getPost(page, callback);
    }

    @Override
    public void savePost(@NonNull Post post, @NonNull PostsDataSource.OnPostCompleteListener callback) {
        mRemoteDataSource.savePost(post, callback);
    }

    @Override
    public void update(@NonNull Post post) {
        mRemoteDataSource.update(post);
    }

    @Override
    public void likePost(@NonNull String username, @NonNull String postID,
                         @NonNull PostsDataSource.OnPostCompleteListener callback) {
        mRemoteDataSource.likePost(username, postID, callback);
    }
}
