package com.example.ginz.funnyphoto.data.source.source.remote;

import android.support.annotation.NonNull;

import com.example.ginz.funnyphoto.configuration.Constants;
import com.example.ginz.funnyphoto.data.model.Post;
import com.example.ginz.funnyphoto.data.model.User;
import com.example.ginz.funnyphoto.data.source.source.DataSource;
import com.example.ginz.funnyphoto.data.source.source.PostDataSource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PostsRemoteDataSource implements PostDataSource.RemoteDataSource {

    private static PostsRemoteDataSource sInstance;

    private  PostsRemoteDataSource() {}

    public static PostsRemoteDataSource getInstance() {
        if(sInstance == null) {
            sInstance = new PostsRemoteDataSource();
        }
        return sInstance;
    }

    @Override
    public void getPostsByUsername(String username,
                                              final PostDataSource.GetPostCallback callback) {

        GetDataAsyncTask asyncTask = new GetDataAsyncTask.Builder()
                .setApiUrl(Constants.Server.RESOURCE_PHOTO_BY_OWNER + username)
                .addOnCompleteListener(new DataSource.OnCompleteListener() {
                    @Override
                    public void onRequestSuccess(String result) {
                        try {
                            JSONObject jsonResult = new JSONObject(result);
                            String message = jsonResult.getString(Constants.Authentication.KEY_MESSAGE);
                            if(message.equals(Constants.Authentication.MESSAGE_OK)) {
                                ArrayList<Post> listPosts = new ArrayList<>();
                                JSONObject jsonData = jsonResult.getJSONObject(User.Key.DATA);
                                JSONArray jsonArrayDocs = jsonData.getJSONArray(User.Key.DOCS);
                                for(int i = 0; i < jsonArrayDocs.length(); i++){
                                    JSONObject objectPost = jsonArrayDocs.getJSONObject(i);
                                    Post post = getPost(objectPost);
                                    listPosts.add(post);
                                }

                                callback.onPostLoaded(listPosts);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            callback.onDataNotAvailable(e);
                        }
                    }

                    @Override
                    public void onRequestError(Exception e) {
                            callback.onDataNotAvailable(e);
                    }
                })
                .build();
        asyncTask.execute();
    }

    @Override
    public void addPost(@NonNull Post post, PostDataSource.AddPostCallback callback) {

    }

    private Post getPost(JSONObject objectPost) throws JSONException {
        int love = objectPost.getInt(Constants.Post.LOVE);
        String id = objectPost.getString(Constants.Post.ID);
        String postDate = objectPost.getString(Constants.Post.CREATED_DATE);
        String title = objectPost.getString(Constants.Post.TITLE);
        String url = objectPost.getString(Constants.Post.URL);
        JSONObject objectUser = objectPost.getJSONObject(Constants.Post.OWNER);
        String username = objectUser.getString(User.Key.USERNAME);
        String avatar = objectUser.getString(User.Key.AVATAR);
        String name = objectUser.getString(User.Key.NAME);

        User user = new User(username, "", name, avatar, "");
        Post post = new Post(id, user, title, postDate, url, love);

        return post;
    }
}
