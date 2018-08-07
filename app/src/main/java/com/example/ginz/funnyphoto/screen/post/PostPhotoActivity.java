package com.example.ginz.funnyphoto.screen.post;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.ginz.funnyphoto.R;
import com.example.ginz.funnyphoto.data.model.User;
import com.example.ginz.funnyphoto.screen.login.LoginActivity;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

public class PostPhotoActivity extends AppCompatActivity
        implements PostPhotoContact.View, View.OnClickListener{

    private RecyclerView mRecyclerPhoto;
    private PhotoAdapter mPhotoAdapter;
    private List<String> mPhotoPaths;
    private ImageView mImagePhotoSelected;
    private String mImagePhotoSelectedPath;
    private Button mButtonPost;
    private EditText mEditTitle;
    private User mUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        initView();
        setupRecyclerPhoto();
        mUser = getUser();
    }

    @Override
    public void showPhotos(List<String> photoPaths) {
        mPhotoPaths = photoPaths;
        mPhotoAdapter.showPhotos(mPhotoPaths);
    }

    @Override
    public void showError() {
        Toast.makeText(this, getString(R.string.error_any), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void postSuccess() {
        finish();
    }

//    @Override
//    public void onClickPhoto(int position) {
//        mImagePhotoSelectedPath = mPhotoPaths.get(position);
//        Picasso.with(this)
//                .load(new File(mImagePhotoSelectedPath))
//                .into(mImagePhotoSelected);
//    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.button_post:
                break;
        }
    }

    private void initView(){
        mRecyclerPhoto = findViewById(R.id.recycler_garelly);
        mImagePhotoSelected = findViewById(R.id.image_selected_photo);
        mButtonPost = findViewById(R.id.button_post);
        mEditTitle = findViewById(R.id.edit_title);
        mButtonPost.setOnClickListener(this);
    }

    private void setupRecyclerPhoto(){
        mPhotoAdapter = new PhotoAdapter(this, mPhotoPaths);
        mRecyclerPhoto.setAdapter(mPhotoAdapter);
        mRecyclerPhoto.setLayoutManager(new GridLayoutManager(this, 3));
    }

    private User getUser() {
        Intent intent = getIntent();
        User user = intent.getParcelableExtra(LoginActivity.EXTRA_USER);
        return user;
    }
}
