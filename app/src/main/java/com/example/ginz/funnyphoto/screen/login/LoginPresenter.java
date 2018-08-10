package com.example.ginz.funnyphoto.screen.login;

import android.support.annotation.NonNull;

import com.example.ginz.funnyphoto.configuration.Constants;
import com.example.ginz.funnyphoto.data.model.User;
import com.example.ginz.funnyphoto.data.source.UsersDataSource;
import com.example.ginz.funnyphoto.data.source.remote.UsersRemoteDataSource;
import com.example.ginz.funnyphoto.data.source.UsersRepository;

public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View mLoginView;
    private UsersRepository mUserRepository;

    public LoginPresenter(LoginContract.View loginView){
        mLoginView = loginView;
        mUserRepository = UsersRepository.getInstance(UsersRemoteDataSource.getInstance());
    }

    @Override
    public void login(String username, String password) {
        mLoginView.onShowProgress();
        mUserRepository.getUser(username, password, new UsersDataSource.OnUserCompleteListener() {
            @Override
            public void onRequestSusscee(@NonNull User user) {
                if(user != null){
                    mLoginView.navigateToMain(user);
                } else {
                    mLoginView.onHideProgress();
                    mLoginView.onShowError(Constants.Authentication.ERROR_USER);
                }
            }

            @Override
            public void onRequestError(Exception e) {
                mLoginView.onShowError(Constants.Authentication.ERROR_SERVER);
            }
        });
    }

    @Override
    public void start() {

    }
}
