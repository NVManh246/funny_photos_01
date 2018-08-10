package com.example.ginz.funnyphoto.data.source.source.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.example.ginz.funnyphoto.data.model.User;
import com.example.ginz.funnyphoto.data.source.source.UserDataSource;

public class UserLocalDataSource implements UserDataSource.LocalDataSource {
    private static final String LOGINED_USER = "LOGINED_USER";
    private static final String KEY_USERNAME = "USERNAME";
    private static final String KEY_EMAIL = "EMAIL";
    private static final String KEY_PASSWORD = "PASSWORD";
    private static final String KEY_FULLNAME = "FULLNAME";
    private static final String KEY_AVATAR = "AVATAR";
    private static UserLocalDataSource sInstance;
    private static Context mContext;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    private UserLocalDataSource(Context context) {
        this.mContext = context;
        mSharedPreferences = mContext.getSharedPreferences(
                LOGINED_USER, Context.MODE_PRIVATE
        );
        mEditor = mSharedPreferences.edit();
    }

    public static UserLocalDataSource getInstance(Context context) {
        if(sInstance == null) {
            sInstance = new UserLocalDataSource(context);
        }
        return sInstance;
    }

    @Override
    public void getLocalUser(@NonNull UserDataSource.GetUserCallback callback) {
        String username = mSharedPreferences.getString(KEY_USERNAME, "");
        String email = mSharedPreferences.getString(KEY_EMAIL, "");
        String fullName = mSharedPreferences.getString(KEY_FULLNAME, "");
        String password = mSharedPreferences.getString(KEY_PASSWORD, "");
        String avatar = mSharedPreferences.getString(KEY_AVATAR, "");

        User user = new User(username, password, fullName, avatar, email);
        callback.onUserLoaded(user);
    }

    @Override
    public void saveLocalUser(@NonNull User user) {
        updateUser(user);
    }

    @Override
    public void updateLocalUser(@NonNull User user,
                                @NonNull UserDataSource.UpdateUserCallback callback) {
        updateUser(user);
        callback.onUpdateUserSuccess(user);
    }

    private void updateUser(User user) {
        mEditor.putString(KEY_USERNAME, user.getUsername());
        mEditor.putString(KEY_PASSWORD, user.getPassword());
        mEditor.putString(KEY_EMAIL, user.getEmail());
        mEditor.putString(KEY_FULLNAME, user.getFullName());
        mEditor.putString(KEY_AVATAR, user.getAvatar());

        mEditor.commit();
    }
}
