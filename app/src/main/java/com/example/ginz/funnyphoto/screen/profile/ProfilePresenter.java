package com.example.ginz.funnyphoto.screen.profile;

import android.support.annotation.NonNull;

import com.example.ginz.funnyphoto.data.model.Post;
import com.example.ginz.funnyphoto.data.model.User;
import com.example.ginz.funnyphoto.data.source.source.PostDataSource;
import com.example.ginz.funnyphoto.data.source.source.PostsRepository;
import com.example.ginz.funnyphoto.data.source.source.UserDataSource;
import com.example.ginz.funnyphoto.data.source.source.UsersRepository;

import java.util.ArrayList;
import java.util.List;

public class ProfilePresenter implements ProfileContract.Presenter, UserDataSource.GetUserCallback,
        PostDataSource.GetPostCallback{

    @NonNull
    private UsersRepository mUserRepository;

    @NonNull
    private PostsRepository mPostsRepository;

    @NonNull
    private final ProfileContract.View mProfileView;

    public ProfilePresenter(@NonNull UsersRepository userRepository,
                            @NonNull PostsRepository postsRepository,
                            ProfileContract.View profileView) {
        mPostsRepository = postsRepository;
        mUserRepository = userRepository;
        mProfileView = profileView;
        mProfileView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void onUserLoaded(User user) {
        mProfileView.onLoadProfileSuccess(user);
    }

    @Override
    public void onPostLoaded(ArrayList<Post> list) {
        mProfileView.onLoadPostsSuccess(list);
    }

    @Override
    public void onDataNotAvailable(Exception exception) {
        mProfileView.onLoadProfileError(exception);
    }

    @Override
    public void loadProfile() {
        mUserRepository.getLocalUser(this);
    }

    @Override
    public void getPosts(String username) {
        mPostsRepository.getPostsByUsername(username, this);
    }
}
