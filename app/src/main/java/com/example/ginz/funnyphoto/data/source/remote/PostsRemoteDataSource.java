package com.example.ginz.funnyphoto.data.source.remote;

import android.os.Handler;
import android.support.annotation.NonNull;
import com.example.ginz.funnyphoto.configuration.Constants;
import com.example.ginz.funnyphoto.data.model.Post;
import com.example.ginz.funnyphoto.data.source.GetDataAsyncTask;
import com.example.ginz.funnyphoto.data.source.OnCompleteListener;
import com.example.ginz.funnyphoto.data.source.PostsDataSource;
import org.json.JSONException;
import java.util.List;

public class PostsRemoteDataSource implements PostsDataSource.PostRemoteDataSource {

    private static PostsRemoteDataSource sInstance;

    private PostsRemoteDataSource(){}

    public static PostsRemoteDataSource getInstance(){
        if(sInstance == null){
            sInstance = new PostsRemoteDataSource();
        }
        return sInstance;
    }

    @Override
    public void getPost(@NonNull int page,
                        @NonNull final PostsDataSource.OnPostCompleteListener callback) {
        new GetDataAsyncTask.Builder()
                .setApiUrl(Constants.Server.RESOURCE_PHOTO + page)
                .addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onRequestSusscee(@NonNull final String response) {
                        new Handler().post(new Runnable() {
                            @Override
                            public void run() {
                                List<Post> newPosts = null;
                                try {
                                    newPosts = Post.parsePosts(response);
                                    callback.onRequestSusscee(newPosts);
                                } catch (JSONException e) {
                                    callback.onRequestError(e);
                                }
                            }
                        });
                    }

                    @Override
                    public void onRequestError(Exception e) {
                        callback.onRequestError(e);
                    }
                })
                .build().execute();
    }

    @Override
    public void savePost(@NonNull Post post) {

    }

    @Override
    public void update(@NonNull Post post) {

    }

    @Override
    public void likePost(@NonNull String username, @NonNull String postID,
                         @NonNull PostsDataSource.OnPostCompleteListener callback) {
    }
}
