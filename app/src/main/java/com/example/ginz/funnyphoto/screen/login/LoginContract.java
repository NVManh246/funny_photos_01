package com.example.ginz.funnyphoto.screen.login;

import com.example.ginz.funnyphoto.BasePresenter;
import com.example.ginz.funnyphoto.BaseView;
import com.example.ginz.funnyphoto.data.model.User;

public interface LoginContract {

    interface View {
        void onShowProgress();
        void onHideProgress();
        void onShowError(int typeError);
        void navigateToMain(User user);
    }

    interface Presenter{
        void login(String username, String password);
    }
}
