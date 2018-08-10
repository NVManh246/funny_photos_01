package com.example.ginz.funnyphoto.data.source.source;

import android.support.annotation.NonNull;

import com.example.ginz.funnyphoto.data.model.Post;

import java.util.ArrayList;
import java.util.List;

public interface PostDataSource {

    interface GetPostCallback {
        void onPostLoaded(ArrayList<Post> list);
        void onDataNotAvailable(Exception exception);
    }

    interface AddPostCallback {
        void onAddPostSuccess(Post post);
        void onAddPostError(Exception exception);
    }

    interface UpdatePostCallback {
        void onUpdatePostSuccess(Post post);
        void onUpdatePostError(Exception exception);
    }

    interface RemoteDataSource {
        void getPostsByUsername(String username, GetPostCallback callback);
        void addPost(@NonNull Post post, AddPostCallback callback);
    }

}
