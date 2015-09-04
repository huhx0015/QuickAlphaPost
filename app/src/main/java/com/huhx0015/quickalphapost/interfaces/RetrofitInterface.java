package com.huhx0015.quickalphapost.interfaces;

import com.huhx0015.quickalphapost.models.Post;
import retrofit.Call;
import retrofit.http.GET;

/**
 * Created by Michael Yoon Huh on 9/3/2015.
 */
public interface RetrofitInterface {

    @GET("/posts/stream/global")
    Call<Post> getLatestPosts();
}
