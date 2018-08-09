package com.example.ginz.funnyphoto.data.source;

import android.support.annotation.NonNull;

import com.example.ginz.funnyphoto.data.model.Post;

import java.util.List;

public interface PostsDataSource {

    public interface OnPostCompleteListener<T> {
        void onRequestSusscee(T response);
        void onRequestError(Exception e);
    }

    public interface PostRemoteDataSource {
        void getPost(@NonNull int page, @NonNull OnPostCompleteListener callback);
        void savePost(@NonNull Post post, @NonNull OnPostCompleteListener callback);
        void update(@NonNull Post post);
        void likePost(@NonNull String username, @NonNull String postID,
                      @NonNull OnPostCompleteListener callback);
    }
}
