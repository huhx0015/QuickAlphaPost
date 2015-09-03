package com.huhx0015.quickalphapost.interfaces;

import com.huhx0015.quickalphapost.models.Post;
import com.huhx0015.quickalphapost.models.User;
import java.util.List;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Michael Yoon Huh on 9/3/2015.
 */
public interface QAPApiInterface {

    // TODO: Need to analyze JSON structure for post retrieval
    @GET("posts/stream/global")
    void getLatestPosts(String parameter, Callback<List<Post>> callback);

    @GET("users/{user_id}/posts")
    void getUser(@Path("user_id") String user_id, Callback<User> cb);

}
