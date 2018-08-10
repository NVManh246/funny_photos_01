package com.example.ginz.funnyphoto.screen.registration;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.ginz.funnyphoto.R;
import com.example.ginz.funnyphoto.configuration.Constants;
import com.example.ginz.funnyphoto.data.source.source.UserDataSource;
import com.example.ginz.funnyphoto.data.model.User;
import com.example.ginz.funnyphoto.data.source.source.UsersRepository;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.ginz.funnyphoto.utils.Preconditions.checkNotNull;

public class RegisterPresenter implements RegisterContract.Presenter,
        UserDataSource.RegisterUserCallback {

    @NonNull
    private UsersRepository mUserRepository;

    @NonNull
    private final RegisterContract.View mRegisterView;

    public RegisterPresenter(@NonNull UsersRepository userDataSource,
                             @NonNull RegisterContract.View registerView) {

        mUserRepository = checkNotNull(userDataSource);
        mRegisterView = checkNotNull(registerView);
        mRegisterView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void onRegisterUserStart() {

    }

    @Override
    public void onRegisterUserSuccess(User user) {
        mRegisterView.onRegisterSuccess(user);
    }

    @Override
    public void onRegisterUserError(Exception message) {
        mRegisterView.onRegisterError(message.toString());
    }

    @Override
    public void doRegister(User user, boolean isChecked) {
        if(validateUser(user, isChecked)) {
            mUserRepository.saveUser(user, this);
            mUserRepository.saveLocalUser(user);
        }
    }

    private boolean validateUser(User user, boolean register) {
        if(user.getUsername().isEmpty()) {
            mRegisterView.onRegisterError(mRegisterView.getString(
                    R.string.registration_username_required));
            return false;
        }
        if(user.getEmail().isEmpty()) {
            mRegisterView.onRegisterError(mRegisterView.getString(
                    R.string.registration_email_required));
            return false;
        }
        if(user.getPassword().isEmpty()) {
            mRegisterView.onRegisterError(mRegisterView.getString(
                    R.string.registration_password_required));
            return false;
        }
        if(user.getFullName().isEmpty()) {
            mRegisterView.onRegisterError(mRegisterView.getString(
                    R.string.registration_name_required));
            return  false;
        }
        if(!register) {
            mRegisterView.onRegisterError(mRegisterView.getString(
                    R.string.registration_check_policy_required));
            return false;
        }
        return true;
    }

    private boolean isRegisterSuccessful(String data) throws JSONException {
        JSONObject jsonObject = new JSONObject(data);
        String result = jsonObject.getString(Constants.Authentication.KEY_MESSAGE);
        return result.equals(Constants.Authentication.MESSAGE_OK);
    }

}
