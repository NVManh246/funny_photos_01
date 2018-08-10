package com.example.ginz.funnyphoto.data.source.source;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.example.ginz.funnyphoto.data.model.Post;
import com.example.ginz.funnyphoto.data.source.source.remote.PostsRemoteDataSource;

import java.util.ArrayList;

public class PostsRepository implements PostDataSource.RemoteDataSource {
    private static PostsRepository sInstance;
    private final PostDataSource.RemoteDataSource mRemoteDataSource;

    public PostsRepository(PostDataSource.RemoteDataSource remoteDataSource) {
        mRemoteDataSource = remoteDataSource;
    }

    public static synchronized PostsRepository getInstance(PostsRemoteDataSource remoteDataSource) {
        if(sInstance == null) {
            sInstance = new PostsRepository(remoteDataSource);
        }
        return sInstance;
    }

    @Override
    public void getPostsByUsername(String username, PostDataSource.GetPostCallback callback) {
        mRemoteDataSource.getPostsByUsername(username, callback);
    }

    @Override
    public void addPost(@NonNull Post post, PostDataSource.AddPostCallback callback) {

    }
}
