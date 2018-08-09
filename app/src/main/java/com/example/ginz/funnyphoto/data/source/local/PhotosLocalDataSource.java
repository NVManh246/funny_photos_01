package com.example.ginz.funnyphoto.data.source.local;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.ginz.funnyphoto.data.source.PhotosDataSource;
import com.example.ginz.funnyphoto.data.source.PostsDataSource;

import java.util.ArrayList;
import java.util.List;

public class PhotosLocalDataSource implements PhotosDataSource.PhotoLocalDataSource {

    private static PhotosLocalDataSource sInstance;
    private static Context mContext;

    private PhotosLocalDataSource(){}

    public static PhotosLocalDataSource getInstance(Context context){
        mContext = context;
        if(sInstance == null){
            sInstance = new PhotosLocalDataSource();
        }
        return sInstance;
    }

    @Override
    public void getPhotoPaths(@NonNull PhotosDataSource.OnPhotosCompleteListener callback) {
        List<String> photoPaths = new ArrayList<>();
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.MediaColumns.DATA};
        Cursor cursor = mContext.getContentResolver()
                .query(uri, projection, null, null,null);
        while(cursor.moveToNext()){
            String photoPath = cursor.getString(0);
            photoPaths.add(photoPath);
        }
        callback.onRequestSusscee(photoPaths);
    }
}
