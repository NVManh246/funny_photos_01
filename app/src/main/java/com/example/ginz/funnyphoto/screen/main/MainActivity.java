package com.example.ginz.funnyphoto.screen.main;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.ginz.funnyphoto.R;
import com.example.ginz.funnyphoto.data.model.User;
import com.example.ginz.funnyphoto.screen.home.HomeFragment;
import com.example.ginz.funnyphoto.screen.login.LoginActivity;
import com.example.ginz.funnyphoto.screen.post.PostPhotoActivity;

public class MainActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener, View.OnClickListener{

    public static final String ARGUMENT_USER = "argument_user";
    public BottomNavigationView mBottomMenu;
    private TextView mTextTitleToolbar;
    private User mUser;
    private HomeFragment mHomeFragment;
    private static final String FONT = "fonts/fortee.ttf";
    private ImageView mImageNewPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setListener();
        setFont();
        mUser = getUser();
        mHomeFragment = newHomeFragment(mUser);
        loadFragment(mHomeFragment);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.menu_home:
                loadFragment(mHomeFragment);
                mTextTitleToolbar.setText(getString(R.string.title_home));
                break;
            case R.id.menu_hot:
                mTextTitleToolbar.setText(getString(R.string.title_hot));
                break;
            case R.id.menu_profile:
                mTextTitleToolbar.setText(R.string.title_profile);
                break;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.image_new_post:
                startActivity(getPostIntent(this, mUser));
                break;
            case R.id.image_logo:
                Dialog dialog = new Dialog(MainActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog);
                dialog.show();
                break;
        }
    }

    private void initView(){
        mBottomMenu = findViewById(R.id.bottom_menu);
        mTextTitleToolbar = findViewById(R.id.text_title_toolbar);
        mImageNewPost = findViewById(R.id.image_new_post);
    }

    private void setListener(){
        mBottomMenu.setOnNavigationItemSelectedListener(this);
        mImageNewPost.setOnClickListener(this);
    }

    private void setFont(){
        Typeface mTypeface = Typeface.createFromAsset(getAssets(), FONT);
        mTextTitleToolbar.setTypeface(mTypeface);
        mBottomMenu.setOnNavigationItemSelectedListener(this);
    }

    private void loadFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.fade_in_fragment, R.anim.fade_out_fragment);
        transaction.replace(R.id.frame_container, fragment);
        transaction.commit();
    }

    private User getUser() {
        Intent intent = getIntent();
        User user = intent.getParcelableExtra(LoginActivity.EXTRA_USER);
        return user;
    }

    private HomeFragment newHomeFragment(User user) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARGUMENT_USER, user);
        fragment.setArguments(args);
        return fragment;
    }

    private Intent getPostIntent(Context context, User user){
        Intent intent = new Intent(context, PostPhotoActivity.class);
        intent.putExtra(LoginActivity.EXTRA_USER, user);
        return intent;
    }
}
