package com.example.ginz.funnyphoto.screen.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.ginz.funnyphoto.R;
import com.example.ginz.funnyphoto.data.model.Post;
import com.example.ginz.funnyphoto.data.model.User;
import com.example.ginz.funnyphoto.screen.main.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements PostContact.View {

    private FragmentActivity mActivity;
    private PostContact.Presenter mPresenter;
    private int mPage = 1;
    private RecyclerView mRecyclerPost;
    private LinearLayoutManager mLayoutManager;
    private PostAdapter mPostAdapter;
    private List<Post> mPosts;
    private boolean mIsLoading;
    private boolean mIsLastPost;
    private User mUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = getActivity();
        initView();
        mPresenter = new PostPresenter(this);
        mPresenter.loadMore(mPage++);
        setupRecycler();
        loadMore();
        mUser = getUser();
    }

    @Override
    public void showNewPost(List<Post> newPosts) {
        mIsLoading = false;
        mPostAdapter.showNewPost(newPosts);
    }

    @Override
    public void lastPost() {
        mIsLastPost = true;
        Toast.makeText(mActivity,
                mActivity.getString(R.string.msg_last_data),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError() {
        Toast.makeText(mActivity,
                mActivity.getString(R.string.error_connect),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart() {
        super.onStart();
        mPosts.clear();
        mPostAdapter.notifyDataSetChanged();
        mPage = 1;
        mPresenter.loadMore(mPage++);
    }

    private void initView(){
        mRecyclerPost = mActivity.findViewById(R.id.recycler_post);
    }

    private void setupRecycler(){
        mPosts = new ArrayList<>();
        mPostAdapter = new PostAdapter(mActivity, mPosts);
        mRecyclerPost.setAdapter(mPostAdapter);
        mLayoutManager = new LinearLayoutManager(mActivity);
        mRecyclerPost.setLayoutManager(mLayoutManager);
    }

    private User getUser(){
        User user = (User) getArguments().get(MainActivity.ARGUMENT_USER);
        return user;
    }

    private void loadMore(){
        mRecyclerPost.addOnScrollListener(new EndlessScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore() {
                if(!mIsLoading && !mIsLastPost) {
                    mPresenter.loadMore(mPage++);
                    mIsLoading = true;
                }
            }
        });
    }
}
