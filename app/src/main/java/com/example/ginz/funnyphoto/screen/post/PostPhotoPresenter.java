package com.example.ginz.funnyphoto.screen.post;

import com.example.ginz.funnyphoto.configuration.Constants;
import com.example.ginz.funnyphoto.data.model.Post;
import com.example.ginz.funnyphoto.data.source.PhotosDataSource;
import com.example.ginz.funnyphoto.data.source.PhotosRepository;
import com.example.ginz.funnyphoto.data.source.PostsDataSource;
import com.example.ginz.funnyphoto.data.source.PostsRepository;
import com.example.ginz.funnyphoto.data.source.remote.PostsRemoteDataSource;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class PostPhotoPresenter implements PostPhotoContact.Presenter {

    private PostPhotoContact.View mView;
    private PhotosRepository mPhotoRepository;
    private PostsRepository mPostsRepository;

    public PostPhotoPresenter(PhotosRepository photoRepository, PostPhotoContact.View view) {
        mView = view;
        mPhotoRepository = photoRepository;
        mPostsRepository = PostsRepository.getInstance(PostsRemoteDataSource.getInstance());
    }

    @Override
    public void loadPhoto() {
        mPhotoRepository.getPhotoPaths(new PhotosDataSource.OnPhotosCompleteListener() {
            @Override
            public void onRequestSusscee(List<String> response) {
                mView.showPhotos(response);
            }

            @Override
            public void onRequestError(Exception e) {
                mView.showError();
            }
        });
    }

    @Override
    public void post(Post post) {
        mPostsRepository.savePost(post, new PostsDataSource.OnPostCompleteListener<String>() {
            @Override
            public void onRequestSusscee(String response) {
                try {
                    if(getResult(response).equals(Constants.Authentication.MESSAGE_OK)){
                        mView.postSuccess();
                    } else {
                        mView.showError();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onRequestError(Exception e) {
                mView.showError();
            }
        });
    }

    private String getResult(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        String restul = jsonObject.getString(Constants.Authentication.KEY_MESSAGE);
        return restul;
    }
}
