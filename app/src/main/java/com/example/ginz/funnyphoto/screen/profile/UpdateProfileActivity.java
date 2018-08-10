package com.example.ginz.funnyphoto.screen.profile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ginz.funnyphoto.R;
import com.example.ginz.funnyphoto.data.model.User;
import com.example.ginz.funnyphoto.data.source.source.UsersRepository;
import com.example.ginz.funnyphoto.data.source.source.local.UserLocalDataSource;
import com.example.ginz.funnyphoto.data.source.source.remote.UsersRemoteDataSource;
import com.example.ginz.funnyphoto.screen.main.MainActivity;
import com.example.ginz.funnyphoto.utils.ImageHandler;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class UpdateProfileActivity extends AppCompatActivity implements
        UpdateProfileContract.View, View.OnClickListener {

    private static final int REQUEST_PICK_IMAGE = 69;
    private static final String TEST_USERNAME = "admin";
    private ImageView mImageClose;
    private ImageView mImageDone;
    private ImageView mImageAvatar;
    private TextView mTextChangeAvatar;
    private EditText mEditFullName;
    private EditText mEditEmail;
    private EditText mEditPassword;
    private Uri mUriAvatar;
    private Bitmap mBitmap;
    private UpdateProfileContract.Presenter mPresenter;
    private ProgressBar mProgressUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        mPresenter = new UpdateProfilePresenter(UsersRepository.getInstance(UsersRemoteDataSource
                .getInstance(), UserLocalDataSource.getInstance(this)), this);
        initView();
        setListener();
    }

    private void setListener() {
        mImageClose.setOnClickListener(this);
        mImageDone.setOnClickListener(this);
        mTextChangeAvatar.setOnClickListener(this);
    }

    private void initView() {
        mImageClose = findViewById(R.id.image_update_profile_close_btn);
        mImageDone = findViewById(R.id.image_update_profile_done_btn);
        mImageAvatar = findViewById(R.id.image_avatar_profile);
        mTextChangeAvatar = findViewById(R.id.text_update_avatar);
        mEditEmail = findViewById(R.id.edit_email_update);
        mEditFullName = findViewById(R.id.edit_fullname_update);
        mEditPassword = findViewById(R.id.edit_password_update);
        mProgressUpdate = findViewById(R.id.progress_update_profile);
    }

    private void goMainScreen() {
        Intent intent = new Intent(UpdateProfileActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void updateProfile() {
        String email = mEditEmail.getText().toString();
        String fullName = mEditFullName.getText().toString();
        String password = mEditPassword.getText().toString();

        User user = new User(TEST_USERNAME, password, fullName, "", email);

        mPresenter.updateProfile(user, ImageHandler.imageToString(mBitmap));
    }

    private void changeAvatar() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_PICK_IMAGE);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.image_update_profile_close_btn:
                goMainScreen();
                break;
            case R.id.image_update_profile_done_btn:
                updateProfile();
                break;
            case R.id.text_update_avatar:
                changeAvatar();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && requestCode == REQUEST_PICK_IMAGE) {
            mUriAvatar = data.getData();
            mImageAvatar.setImageURI(mUriAvatar);

            try {
                InputStream inputStream = getContentResolver().openInputStream(mUriAvatar);
                mBitmap = BitmapFactory.decodeStream(inputStream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onUpdateProfileStart() {
        Toast.makeText(this, getString(R.string.update_starting), Toast.LENGTH_SHORT).show();
        mProgressUpdate.setVisibility(View.VISIBLE);
    }

    @Override
    public void onUpdateProfileSuccess(User user) {
        mProgressUpdate.setVisibility(View.GONE);
        Toast.makeText(this, getString(R.string.update_profile_success), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpdateProfileError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setPresenter(UpdateProfileContract.Presenter presenter) {
        mPresenter = (UpdateProfilePresenter) presenter;
    }
}
