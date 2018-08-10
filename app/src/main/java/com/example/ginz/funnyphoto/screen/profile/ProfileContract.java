package com.example.ginz.funnyphoto.screen.profile;

import android.widget.BaseAdapter;

import com.example.ginz.funnyphoto.BasePresenter;
import com.example.ginz.funnyphoto.BaseView;
import com.example.ginz.funnyphoto.data.model.Post;
import com.example.ginz.funnyphoto.data.model.User;

import java.util.ArrayList;
import java.util.List;

public interface ProfileContract {

    interface View extends BaseView<Presenter> {

        void onLoadProfileSuccess(User user);

        void onLoadProfileError(Exception exception);

        void onLoadPostsSuccess(ArrayList<Post> list);

        void onLoadPostsError(Exception exception);

        String getString(int resourcID);
    }

    interface Presenter extends BasePresenter{
        void loadProfile();

        void getPosts(String username);
    }
}
