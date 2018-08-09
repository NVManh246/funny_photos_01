package com.example.ginz.funnyphoto.data.source;

import android.support.annotation.NonNull;

import com.example.ginz.funnyphoto.data.model.Post;
import com.example.ginz.funnyphoto.data.source.local.PhotosLocalDataSource;

public class PhotosRepository implements PhotosDataSource.PhotoLocalDataSource {

    private static PhotosRepository sInstance;
    private PhotosDataSource.PhotoLocalDataSource mLocalDataSource;

    private PhotosRepository(PhotosLocalDataSource photosLocalDataSource){
        mLocalDataSource = photosLocalDataSource;
    }

    public static PhotosRepository getInstance(PhotosLocalDataSource photosLocalDataSource){
        if(sInstance == null){
            sInstance = new PhotosRepository(photosLocalDataSource);
        }
        return sInstance;
    }

    @Override
    public void getPhotoPaths(@NonNull PhotosDataSource.OnPhotosCompleteListener callback) {
        mLocalDataSource.getPhotoPaths(callback);
    }
}
