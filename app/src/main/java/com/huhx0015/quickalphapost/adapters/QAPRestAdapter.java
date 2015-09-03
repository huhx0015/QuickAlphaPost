package com.huhx0015.quickalphapost.adapters;

import android.util.Log;
import com.huhx0015.quickalphapost.interfaces.QAPApiInterface;
import com.huhx0015.quickalphapost.models.Datum;
import com.huhx0015.quickalphapost.models.Post;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Michael Yoon Huh on 9/3/2015.
 */
public class QAPRestAdapter {

    /** CLASS VARIABLES ________________________________________________________________________ **/

    // API VARIABLES
    private final static String BASE_URL = "https://api.app.net/";

    // LOGGING VARIABLES
    private static final String LOG_TAG = QAPRestAdapter.class.getSimpleName();

    /** REST ADAPTER METHODS ___________________________________________________________________ **/

    // TODO: Finish implementation of this method.
    public static void retrieveLatestPosts() {

        // Builds a new RestAdapter instance.
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .build();

        QAPApiInterface apiRequest = restAdapter.create(QAPApiInterface.class);

        // TODO: Finish later.
        apiRequest.getLatestPosts(new Callback<ArrayList<Post>>() {

            @Override
            public void success(ArrayList<Post> posts, Response response) {

                Log.d(LOG_TAG, "Request successful: " + response);

                Log.d(LOG_TAG, "Post Sample 0 Post Text Test: " + posts.get(0).getDatum().getText());

            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(LOG_TAG, "Request failed: " + error);
            }
        });
    }
}
