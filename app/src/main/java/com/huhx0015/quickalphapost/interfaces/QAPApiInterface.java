package com.huhx0015.quickalphapost.interfaces;

import com.huhx0015.quickalphapost.models.User;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Michael Yoon Huh on 9/3/2015.
 */
public interface QAPApiInterface {

    // TODO: Need to analyze JSON structure for post retrieval
    @GET("posts/stream/global")
    void getLatestPosts(String posts, Callback<String> callback);

    @GET("users/{user_id}/posts")
    void getUser(@Path("user_id") String user_id, Callback<User> cb);

}
