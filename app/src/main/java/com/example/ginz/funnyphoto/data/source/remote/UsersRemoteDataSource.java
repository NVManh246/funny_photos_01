package com.example.ginz.funnyphoto.data.source.remote;

import android.os.Handler;
import android.support.annotation.NonNull;
import com.example.ginz.funnyphoto.configuration.Constants;
import com.example.ginz.funnyphoto.data.model.User;
import com.example.ginz.funnyphoto.data.source.GetDataAsyncTask;
import com.example.ginz.funnyphoto.data.source.OnCompleteListener;
import com.example.ginz.funnyphoto.data.source.UsersDataSource;

import org.json.JSONException;

public class UsersRemoteDataSource implements UsersDataSource.UserRemoteDataSource {

    private static UsersRemoteDataSource sInstance;

    private UsersRemoteDataSource(){}

    public static UsersRemoteDataSource getInstance(){
        if(sInstance == null){
            sInstance = new UsersRemoteDataSource();
        }
        return sInstance;
    }

    @Override
    public void getUser(@NonNull String username, @NonNull String password,
                        @NonNull final UsersDataSource.OnUserCompleteListener callback) {
        new GetDataAsyncTask.Builder()
                .setApiUrl(Constants.Server.ACTION_LOGIN)
                .addParameter(User.Key.USERNAME, username)
                .addParameter(User.Key.PASSWORD, password)
                .addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onRequestSusscee(@NonNull final String response) {
                        new Handler().post(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    User user = User.parseUser(response);
                                    callback.onRequestSusscee(user);
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
                .build()
                .execute();
    }

    @Override
    public void saveUser(@NonNull User user) {

    }

    @Override
    public void updateUser(@NonNull String oldUsername, @NonNull User user) {

    }
}
