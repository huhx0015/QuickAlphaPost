package com.huhx0015.quickalphapost.adapters;

import android.util.Log;
import com.huhx0015.quickalphapost.interfaces.QAPApiInterface;
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
        apiRequest.getLatestPosts(null, new Callback<String>() {

            @Override
            public void success(String s, Response response) {
                Log.d(LOG_TAG, "Request successful: " + response);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(LOG_TAG, "Request failed: " + error);
            }
        });
    }
}
