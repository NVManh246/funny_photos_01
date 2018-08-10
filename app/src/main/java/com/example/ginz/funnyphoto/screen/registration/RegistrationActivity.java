package com.example.ginz.funnyphoto.screen.registration;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.ginz.funnyphoto.R;
import com.example.ginz.funnyphoto.data.model.User;
import com.example.ginz.funnyphoto.data.source.source.UsersRepository;
import com.example.ginz.funnyphoto.data.source.source.local.UserLocalDataSource;
import com.example.ginz.funnyphoto.data.source.source.remote.UsersRemoteDataSource;
import com.example.ginz.funnyphoto.screen.main.MainActivity;

public class RegistrationActivity extends AppCompatActivity implements RegisterContract.View,
        View.OnClickListener{

    private static final String FONT_PATH = "fonts/fortee.ttf";
    private Typeface mTypeface;
    private Button mButtonRegister;
    private TextView mTextLogo;
    private EditText mEditFullName;
    private EditText mEditEmail;
    private EditText mEditUserName;
    private EditText mEditPassword;
    private ProgressBar mProgressBar;
    private CheckBox mCheckBox;
    private RegisterPresenter mRegisterPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mRegisterPresenter = new RegisterPresenter( UsersRepository.getInstance(
                UsersRemoteDataSource.getInstance(), UserLocalDataSource.getInstance(this)),this);

        initView();
        setListener();
        setFont();
    }

    private void setListener() {
        mButtonRegister.setOnClickListener(this);
    }

    private void initView(){
        mButtonRegister = findViewById(R.id.button_register);
        mTextLogo = findViewById(R.id.text_logo);
        mEditEmail = findViewById(R.id.edit_email_register);
        mEditFullName = findViewById(R.id.edit_fullname_register);
        mEditUserName = findViewById(R.id.edit_username_resgiter);
        mEditPassword = findViewById(R.id.edit_password_resgiter);
        mProgressBar = findViewById(R.id.progress);
        mCheckBox = findViewById(R.id.checkbox_accept);
    }

    private void setFont(){
        mTypeface = Typeface.createFromAsset(getAssets(), FONT_PATH);
        mTextLogo.setTypeface(mTypeface);
    }

    @Override
    public void onRegisterSuccess(User user) {
        goMainScreen(user);
    }

    @Override
    public void onRegisterError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.button_register:
                String fullName = mEditFullName.getText().toString();
                String email = mEditEmail.getText().toString();
                String username = mEditUserName.getText().toString();
                String password = mEditPassword.getText().toString();
                boolean isCheck = mCheckBox.isChecked();
                User user = new User(username, password, fullName, null, email);

                register(user, isCheck);

                break;
        }
    }

    @Override
    public void setPresenter(RegisterContract.Presenter presenter) {
        mRegisterPresenter = (RegisterPresenter) presenter;
    }

    private void register(User user, boolean register) {
        mRegisterPresenter.doRegister(user, register);
    }

    private void goMainScreen(User user) {
        startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
    }
}
