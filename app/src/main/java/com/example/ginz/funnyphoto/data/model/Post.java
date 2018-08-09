package com.example.ginz.funnyphoto.data.model;

import com.example.ginz.funnyphoto.configuration.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class Post {
    private String mId;
    private User mUserPosted;
    private String mTitle;
    private String mPostTime;
    private String mImagePath;
    private int mLikes;
    private boolean mIsLiked;

    public Post(){}

    public Post(User user, String title, String image){
        mUserPosted = user;
        mTitle = title;
        mImagePath = image;
    }

    public Post(String id, User userPosted, String title, String postTime, String imagePath, int likes) {
        this(userPosted, title, imagePath);
        mId = id;
        mPostTime = postTime;
        mLikes = likes;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public User getUserPosted() {
        return mUserPosted;
    }

    public void setUserPosted(User userPosted) {
        mUserPosted = userPosted;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getPostTime() {
        return mPostTime;
    }

    public void setPostTime(String postTime) {
        mPostTime = postTime;
    }

    public String getImagePath() {
        return mImagePath;
    }

    public void setImagePath(String imagePath) {
        mImagePath = imagePath;
    }

    public int getLikes() {
        return mLikes;
    }

    public void setLikes(int likes) {
        mLikes = likes;
    }

    public boolean isLiked() {
        return mIsLiked;
    }

    public void setLiked(boolean liked) {
        mIsLiked = liked;
    }

    public static class Key {
        public static final String OWNER = "owner";
        public static final String LOVE = "love";
        public static final String ID = "_id";
        public static final String POST_ID = "postId";
        public static final String TITLE = "title";
        public static final String IMAGE_URL = "url";
        public static final String RAW_PHOTO = "rawPhoto";
        public static final String OWNER_NAME = "ownerName";
        public static final String OWNER_AVATAR = "ownerAvatar";
        public static final String OWNER_USERNAME = "ownerUsername";
    }

    public static List<Post> parsePosts(String jsonData) throws JSONException {
        List<Post> posts = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(jsonData);
        String message = jsonObject.getString(Constants.Authentication.KEY_MESSAGE);
        if(message.equals(Constants.Authentication.MESSAGE_OK)) {
            JSONObject jsonObjectData = jsonObject.getJSONObject(Constants.Authentication.DATA);
            JSONArray jsonArray = jsonObjectData.getJSONArray(Constants.Authentication.DOCS);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObjectPost = jsonArray.getJSONObject(i);
                JSONObject josnObjectOwner = jsonObjectPost.getJSONObject(Key.OWNER);
                User user = getOwner(josnObjectOwner);
                int like = jsonObjectPost.optInt(Key.LOVE, 0);
                String id = jsonObjectPost.optString(Key.ID, null);
                String title = jsonObjectPost.optString(Key.TITLE, null);
                String imageUrl = Constants.Server.BASE_URL_API
                        + jsonObjectPost.optString(Key.IMAGE_URL, null);
                Post post = new Post(id, user, title, null, handlerURL(imageUrl), like);
                posts.add(post);
            }
        }
        return posts;
    }

    private static User getOwner(JSONObject data) throws JSONException {
        String username = data.optString(User.Key.NAME, null);
        String avatar = data.optString(User.Key.AVATAR, null);
        User user = new User(username, avatar);
        return user;
    }

    private static String handlerURL(String url){
        url = url.replace("\\", "/");
        url = url.replace("/public", "");
        return url;
    }
}
