package com.example.ginz.funnyphoto.screen.login;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.ginz.funnyphoto.R;
import com.example.ginz.funnyphoto.configuration.Constants;
import com.example.ginz.funnyphoto.data.model.User;
import com.example.ginz.funnyphoto.screen.main.MainActivity;
import com.example.ginz.funnyphoto.screen.registration.RegistrationActivity;

public abstract class LoginActivity extends AppCompatActivity
        implements View.OnClickListener, LoginContract.View{

    public static final String EXTRA_USER = "USER";
    private static final String FONT_PATH = "fonts/fortee.ttf";
    private LoginContract.Presenter mLoginPresenter;
    private TextView mTextLogo;
    private EditText mEditUsername;
    private EditText mEditPassword;
    private Button mButtonLogin;
    private Button mButtonRegister;
    private ProgressBar mProgress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        setListener();
        setFontLogo();
        mLoginPresenter = new LoginPresenter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.button_login:
                String username = mEditUsername.getText().toString();
                String password = mEditPassword.getText().toString();
                mLoginPresenter.login(username, password);
                break;

            case R.id.button_register:
                startActivity(new Intent(LoginActivity.this,
                        RegistrationActivity.class));
                break;
        }
    }

    @Override
    public void onShowProgress() {
        mProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void onHideProgress() {
        mProgress.setVisibility(View.GONE);
    }

    @Override
    public void onShowError(int typeError) {
        if(typeError == Constants.Authentication.ERROR_USER) {
            Toast.makeText(this,
                    getString(R.string.error_user_or_password_incorrect),
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this,
                    getString(R.string.error_connect),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void navigateToMain(User user) {
        startActivity(getMainIntent(this, user));
        finish();
    }

    private void initView(){
        mTextLogo = findViewById(R.id.text_logo);
        mEditUsername = findViewById(R.id.edit_username_login);
        mEditPassword = findViewById(R.id.edit_password_login);
        mButtonLogin = findViewById(R.id.button_login);
        mButtonRegister = findViewById(R.id.button_register);
        mProgress = findViewById(R.id.progress_login);
    }

    private void setListener(){
        mButtonLogin.setOnClickListener(this);
        mButtonRegister.setOnClickListener(this);
    }

    private void setFontLogo(){
        Typeface mTypefaceLogo = Typeface.createFromAsset(getAssets(), FONT_PATH);
        mTextLogo.setTypeface(mTypefaceLogo);
    }


    private Intent getMainIntent(Context context, User user){
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(EXTRA_USER, user);
        return intent;
    }
}
