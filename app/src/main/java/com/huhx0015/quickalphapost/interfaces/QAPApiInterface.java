package com.huhx0015.quickalphapost.interfaces;

import com.huhx0015.quickalphapost.models.AlphaPost;
import com.huhx0015.quickalphapost.models.User;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Michael Yoon Huh on 9/3/2015.
 */
public interface QAPApiInterface {

    @GET("/posts/stream/global")
    void getLatestPosts(Callback<AlphaPost> callback);

    @GET("files/{file_id}")
    void getFile(@Path("file_id") String file_id, Callback<String> callback);

    @GET("/users/{user_id}/posts")
    void getUser(@Path("user_id") String user_id, Callback<User> callback);

    @GET("users/{user_id}/avatar")
    void getUserAvatar(@Path("user_id") String user_id, Callback<String> callback);
}
