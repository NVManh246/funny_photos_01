package com.example.ginz.funnyphoto.screen.post;

import com.example.ginz.funnyphoto.data.model.Post;

import java.util.List;

public interface PostPhotoContact {
    interface View{
        void showPhotos(List<String> photoPaths);
        void showError();
        void postSuccess();
    }

    interface Presenter{
        void loadPhoto();
        void post(Post post);
    }
}
