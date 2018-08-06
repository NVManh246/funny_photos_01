package com.example.ginz.funnyphoto.data.source.source.remote;

import android.support.annotation.NonNull;

import com.example.ginz.funnyphoto.configuration.Constants;
import com.example.ginz.funnyphoto.data.model.User;
import com.example.ginz.funnyphoto.data.source.source.DataSource;
import com.example.ginz.funnyphoto.data.source.source.UserDataSource;

import org.json.JSONException;
import org.json.JSONObject;

public class UsersRemoteDataSource implements UserDataSource.RemoteDataSource{

    private static UsersRemoteDataSource sInstance;

    public static UsersRemoteDataSource getInstance() {
        if(sInstance == null) {
            sInstance = new UsersRemoteDataSource();
        }
        return sInstance;
    }

    private UsersRemoteDataSource() {}

    @Override
    public void getUser(@NonNull String username, @NonNull UserDataSource.GetUserCallback callback){
    }

    @Override
    public void saveUser(@NonNull User user, final UserDataSource.RegisterUserCallback callback) {
        String username = user.getUsername();
        String password = user.getPassword();
        String email = user.getEmail();
        String fullName = user.getFullName();

        GetDataAsyncTask asyncTask = new GetDataAsyncTask.Builder()
                .setApiUrl(Constants.Server.ACTION_REGISTER)
                .addParameter(Constants.Key.USERNAME, username)
                .addParameter(Constants.Key.PASSWORD, password)
                .addParameter(Constants.Key.EMAIL, email)
                .addParameter(Constants.Key.FULL_NAME, fullName)
                .addOnCompleteListener(new DataSource.OnCompleteListener() {
                    @Override
                    public void onRequestSuccess(String result) {
                        try {
                            JSONObject jsonResult = new JSONObject(result);
                            String message = jsonResult.getString(Constants.Authentication.KEY_MESSAGE);
                            if(message.equals(Constants.Authentication.MESSAGE_OK)) {
                                JSONObject objectUser = jsonResult.getJSONObject(Constants.Key.USER);
                                if(getUser(objectUser) != null) {
                                    callback.onRegisterUserSuccess(getUser(objectUser));
                                }
                            }
                            else if(message.equals(Constants.Authentication.MESSAGE_ERROR)) {
                                String description = jsonResult.getString(Constants.Authentication.KEY_DESCRIPTION);

                                callback.onRegisterUserError(new Exception(description));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            callback.onRegisterUserError(e);
                        }
                    }

                    @Override
                    public void onRequestError(Exception error) {
                        callback.onRegisterUserError(error);
                    }
                })
                .build();
        asyncTask.execute();
    }

    @Override
    public void updateUser(@NonNull User user, @NonNull String bitmapAvatar,
                           final UserDataSource.UpdateUserCallback callback) {

        callback.onUpdateUserStart();

        String fullName = user.getFullName();
        String email = user.getEmail();
        String password = user.getPassword();
        String username = user.getUsername();

        GetDataAsyncTask getDataAsyncTask = new GetDataAsyncTask.Builder()
                .setApiUrl(Constants.Server.ACTION_UPDATE_PROFILE)
                .addParameter(Constants.Key.AVATAR, bitmapAvatar)
                .addParameter(Constants.Key.EMAIL, email)
                .addParameter(Constants.Key.FULL_NAME, fullName)
                .addParameter(Constants.Key.PASSWORD, password)
                .addParameter(Constants.Key.USERNAME, username)
                .addOnCompleteListener(new DataSource.OnCompleteListener() {
                    @Override
                    public void onRequestSuccess(String result) {
                        try {
                            JSONObject jsonResult = new JSONObject(result);

                            String message = jsonResult.getString(Constants.Authentication.KEY_MESSAGE);
                            if(message.equals(Constants.Authentication.MESSAGE_OK)) {
                                JSONObject objectUser = jsonResult.getJSONObject(Constants.Key.USER);
                                User userUpdated = getUser(objectUser);

                                    callback.onUpdateUserSuccess(userUpdated);
                            }
                            else if(message.equals(Constants.Authentication.MESSAGE_ERROR)) {
                                String description = jsonResult.getString(Constants.Authentication.KEY_DESCRIPTION);
                                callback.onUpdateUserError(new Exception(description));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            callback.onUpdateUserError(e);
                        }
                    }

                    @Override
                    public void onRequestError(Exception error) {
                        callback.onUpdateUserError(error);
                    }
                })
                .build();
        getDataAsyncTask.execute();
    }

    private User getUser(JSONObject objectUser) throws JSONException {
                String registerUsername = objectUser.getString(Constants.Key.USERNAME);
                String registerEmail = objectUser.getString(Constants.Key.EMAIL);
                String registerFullname = objectUser.getString(Constants.Key.FULL_NAME);
                String updatedAvatar = objectUser.optString(Constants.Key.AVATAR, "");
                String registerPassword = objectUser.getString(Constants.Key.PASSWORD);

            User updatedUser = new User(registerUsername, registerPassword, registerFullname,
                    updatedAvatar, registerEmail);
            return updatedUser;
    }
}
