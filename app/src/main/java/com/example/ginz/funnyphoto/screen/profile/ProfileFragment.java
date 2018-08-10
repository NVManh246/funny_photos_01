package com.example.ginz.funnyphoto.screen.profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ginz.funnyphoto.R;
import com.example.ginz.funnyphoto.data.model.User;
import com.example.ginz.funnyphoto.data.source.source.PostsRepository;
import com.example.ginz.funnyphoto.data.source.source.UsersRepository;
import com.example.ginz.funnyphoto.data.source.source.local.UserLocalDataSource;
import com.example.ginz.funnyphoto.data.source.source.remote.PostsRemoteDataSource;
import com.example.ginz.funnyphoto.data.source.source.remote.UsersRemoteDataSource;
import com.example.ginz.funnyphoto.screen.profile.adapter.PhotoAdapter;
import com.example.ginz.funnyphoto.screen.profile.adapter.PostAdapter;
import com.example.ginz.funnyphoto.data.model.Post;
import com.example.ginz.funnyphoto.utils.Preconditions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment implements ProfileContract.View{
    private static final int SPAN_COUNT = 3;
    private static final String LOGINED_USER = "LOGINED_USER";
    private static final String KEY_USERNAME = "USERNAME";
    private FragmentActivity mActivity;
    private RecyclerView mRecyclerPost;
    private ImageView mImageAvatar;
    private TextView mTextFullName;
    private TextView mTextEmail;
    private List<Post> mPosts;
    private ProfileContract.Presenter mPresenter;
    private SharedPreferences mSharedPreferences;
    private PhotoAdapter mPostAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, null);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = getActivity();
        initView();
        setupRecycler();

        mPresenter = new ProfilePresenter(UsersRepository.getInstance(
                UsersRemoteDataSource.getInstance(), UserLocalDataSource.getInstance(getContext())
        ), PostsRepository.getInstance(PostsRemoteDataSource.getInstance()), this);

        mSharedPreferences = getContext().getSharedPreferences(LOGINED_USER, Context.MODE_PRIVATE);
        String username = mSharedPreferences.getString(KEY_USERNAME,"");

        mPresenter.loadProfile();
        mPresenter.getPosts(username);
    }

    private void initView(){
        mRecyclerPost = mActivity.findViewById(R.id.recycler_post_profile);
        mTextEmail= mActivity.findViewById(R.id.text_email_profile);
        mTextFullName = mActivity.findViewById(R.id.text_fullname_profile);
        mImageAvatar = mActivity.findViewById(R.id.image_avatar);
    }

    private void setupRecycler(){
        mPosts = new ArrayList<>();

        mPostAdapter = new PhotoAdapter(getActivity(), mPosts);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), SPAN_COUNT);
        mRecyclerPost.setHasFixedSize(true);
        mRecyclerPost.setLayoutManager(mLayoutManager);
        mRecyclerPost.setAdapter(mPostAdapter);
        mRecyclerPost.setNestedScrollingEnabled(false);
    }

    @Override
    public void onLoadProfileSuccess(User user) {
        mTextFullName.setText(user.getFullName());
        mTextEmail.setText(user.getEmail());
        if(!user.getAvatar().equals("")) {
             Picasso.with(getContext()).load(user.getAvatar()).into(mImageAvatar);
        }
    }

    @Override
    public void onLoadProfileError(Exception exception) {
        Toast.makeText(mActivity, exception.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoadPostsSuccess(ArrayList<Post> list) {
        mPosts.addAll(list);
        mPostAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadPostsError(Exception exception) {

    }

    @Override
    public void setPresenter(ProfileContract.Presenter presenter) {
        mPresenter = Preconditions.checkNotNull(presenter);
    }
}
