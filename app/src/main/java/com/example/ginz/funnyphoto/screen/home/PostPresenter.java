package com.example.ginz.funnyphoto.screen.home;

import com.example.ginz.funnyphoto.configuration.Constants;
import com.example.ginz.funnyphoto.data.model.Post;
import com.example.ginz.funnyphoto.data.source.PostsDataSource;
import com.example.ginz.funnyphoto.data.source.PostsRepository;
import com.example.ginz.funnyphoto.data.source.remote.PostsRemoteDataSource;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class PostPresenter implements PostContact.Presenter {

    private PostContact.View mHomeView;
    private PostsRepository mRepository;

    public PostPresenter(PostContact.View homeView){
        mRepository = PostsRepository.getInstance(PostsRemoteDataSource.getInstance());
        mHomeView = homeView;
    }

    @Override
    public void loadMore(int page) {
        mRepository.getPost(page, new PostsDataSource.OnPostCompleteListener<List<Post>>() {
            @Override
            public void onRequestSusscee(List<Post> response) {
                if(response.size() > 0) {
                    mHomeView.showNewPost(response);
                } else {
                    mHomeView.lastPost();
                }
            }

            @Override
            public void onRequestError(Exception e) {
                mHomeView.showError();
            }
        });
    }

    @Override
    public void like(String username, String postId) {

    }
}
