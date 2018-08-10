package com.example.ginz.funnyphoto.data.source.source;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.telecom.Call;

import com.example.ginz.funnyphoto.data.model.User;
import com.example.ginz.funnyphoto.data.source.source.local.UserLocalDataSource;
import com.example.ginz.funnyphoto.data.source.source.remote.UsersRemoteDataSource;

public class UsersRepository implements UserDataSource.RemoteDataSource, UserDataSource.LocalDataSource {

    private static UsersRepository sInstance = null;
    private final UserDataSource.RemoteDataSource mRemoteDataSource;
    private final UserDataSource.LocalDataSource mLocalDataSource;

    private UsersRepository(UsersRemoteDataSource remoteDataSource,
                            UserLocalDataSource localDataSource) {
        mRemoteDataSource = remoteDataSource;
        mLocalDataSource = localDataSource;
    }

    public static synchronized UsersRepository getInstance(UsersRemoteDataSource remoteDataSource,
                                                           UserLocalDataSource localDataSource){
        if(sInstance == null) {
            sInstance = new UsersRepository(remoteDataSource, localDataSource);
        }
        return sInstance;
    }

    public static void destroyInstance() {
        sInstance = null;
    }

    @Override
    public void getUser(@NonNull String username, @NonNull UserDataSource.GetUserCallback callback){
        mRemoteDataSource.getUser(username, callback);
    }

    @Override
    public void saveUser(@NonNull User user, @NonNull UserDataSource.RegisterUserCallback callback) {
        mRemoteDataSource.saveUser(user, callback);
    }

    @Override
    public void updateUser(@NonNull User user, @NonNull String bitmapAvatar,
                           @NonNull UserDataSource.UpdateUserCallback callback) {
        mRemoteDataSource.updateUser(user, bitmapAvatar, callback);
    }

    @Override
    public void getLocalUser(@NonNull UserDataSource.GetUserCallback callback) {
        mLocalDataSource.getLocalUser(callback);
    }

    @Override
    public void saveLocalUser(@NonNull User user) {
        mLocalDataSource.saveLocalUser(user);
    }

    @Override
    public void updateLocalUser(@NonNull User user, @NonNull UserDataSource.UpdateUserCallback callback) {
        mLocalDataSource.updateLocalUser(user, callback);
    }
}
