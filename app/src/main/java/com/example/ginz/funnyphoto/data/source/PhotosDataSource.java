package com.example.ginz.funnyphoto.data.source;

import android.support.annotation.NonNull;
import java.util.List;

public interface PhotosDataSource {
    public interface OnPhotosCompleteListener {
        void onRequestSusscee(List<String> response);
        void onRequestError(Exception e);
    }

    public interface PhotoLocalDataSource {
        void getPhotoPaths(@NonNull OnPhotosCompleteListener callback);
    }
}
