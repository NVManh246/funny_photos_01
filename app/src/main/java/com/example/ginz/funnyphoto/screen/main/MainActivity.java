package com.example.ginz.funnyphoto.screen.main;

import android.app.Dialog;

import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ginz.funnyphoto.screen.profile.ProfilePresenter;
import com.example.ginz.funnyphoto.widget.BottomNavigationBehavior;
import com.example.ginz.funnyphoto.widget.IBottomMenuBehaviorListener;
import com.example.ginz.funnyphoto.R;
import com.example.ginz.funnyphoto.screen.profile.ProfileFragment;

public class MainActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener, View.OnClickListener,
        IBottomMenuBehaviorListener {

    private static final String FONT = "fonts/fortee.ttf";
    public BottomNavigationView mBottomMenu;
    private Typeface mTypeface;
    private TextView mTextTitleToolbar;
    private boolean mIsBottomMenuVisible = true;
    private Animation mAnimSlideUp;
    private Animation mAnimSlideDown;
    private ImageView mImageNewPost;
    private ProfilePresenter mProfilePresenter;

    Fragment profileFragment = new ProfileFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageView = findViewById(R.id.image_logo);

        imageView.setOnClickListener(this);

        initView();
        setListener();
        setFont();

        CoordinatorLayout.LayoutParams layoutParams =
                (CoordinatorLayout.LayoutParams) mBottomMenu.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehavior(this));
        mAnimSlideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up_bottom_menu);
        mAnimSlideDown = AnimationUtils.loadAnimation(this, R.anim.slide_down_bottom_menu);
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
        mTypeface = Typeface.createFromAsset(getAssets(), FONT);
        mTextTitleToolbar.setTypeface(mTypeface);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.menu_home:
                break;
            case R.id.menu_hot:
                break;
            case R.id.menu_profile:
                // mViewPager.setCurrentItem(2);
                mTextTitleToolbar.setText(getString(R.string.title_profile));
                loadFragment(profileFragment);
                break;
        }
        return true;
    }

    private void loadFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.fade_in_fragment, R.anim.fade_out_fragment);
        transaction.replace(R.id.frame_container, fragment);
        transaction.commit();
    }

    @Override
    public void onScrollUp() {  //hiển thị bottom menu
        if(!mIsBottomMenuVisible) {
            mBottomMenu.clearAnimation();
            mBottomMenu.startAnimation(mAnimSlideUp);
            mBottomMenu.setEnabled(true);
        }
        mIsBottomMenuVisible = true;
    }

    @Override
    public void onScrollDown() {    //Ẩn bottom menu
        if(mIsBottomMenuVisible) {
            mBottomMenu.clearAnimation();
            mBottomMenu.startAnimation(mAnimSlideDown);
            mBottomMenu.setEnabled(false);
        }
        mIsBottomMenuVisible = false;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.image_new_post:
//                Intent intent = new Intent(MainActivity.this, ChoosePhotoAvtivity.class);
//                startActivity(intent);
                break;
            case R.id.image_logo:
                Dialog dialog = new Dialog(MainActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog);
                dialog.show();
                break;
        }
    }
}
