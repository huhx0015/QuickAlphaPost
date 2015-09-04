package com.huhx0015.quickalphapost.activities;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.huhx0015.quickalphapost.constants.QAPConstants;
import com.huhx0015.quickalphapost.connection.QAPConnectivity;
import com.huhx0015.quickalphapost.ui.QAPRecyclerAdapter;
import com.huhx0015.quickalphapost.R;
import com.huhx0015.quickalphapost.interfaces.RetrofitInterface;
import com.huhx0015.quickalphapost.models.Datum;
import com.squareup.okhttp.OkHttpClient;
import java.io.IOException;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class QAPMainActivity extends AppCompatActivity {

    /** CLASS VARIABLES ________________________________________________________________________ **/

    // ASYNCTASK VARIABLES
    private QAPQueryTask queryTask; // References the AsyncTask.

    // LIST VARIABLES
    private List<Datum> postListResult;

    // LOGGING VARIABLES
    private static final String LOG_TAG = QAPMainActivity.class.getSimpleName();

    // VIEW INJECTION VARIABLES
    @Bind(R.id.fetch_button) Button fetchButton;
    @Bind(R.id.qap_recycler_view) RecyclerView alphaRecyclerView;
    @Bind(R.id.qap_progress_indicator) ProgressBar progressIndicator;

    /** ACTIVITY LIFECYCLE METHODS _____________________________________________________________ **/

    // onCreate(): The first method that is run when the activity is created.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupLayout(); // Sets up the layout for the activity.
    }

    // onStop(): This method runs when the screen is no longer visible.
    @Override
    public void onStop() {
        super.onStop();

        // If the AsyncTask is still running in the background, it is cancelled at this point.
        if (null != queryTask) {
            if (queryTask.getStatus() == AsyncTask.Status.RUNNING) {
                queryTask.cancel(true); // Cancels the AsyncTask operation.
                Log.d(LOG_TAG, "onStop(): AsyncTask has been cancelled.");
            }
        }
    }

    /** LAYOUT METHODS _________________________________________________________________________ **/

    // setupLayout(): Sets up the layout for the activity.
    private void setupLayout() {

        setContentView(R.layout.qap_main_activity); // Sets the XML layout for the activity.
        ButterKnife.bind(this); // ButterKnife view injection initialization.

        setupButtons(); // Sets up the listeners for the buttons.
    }

    // setupButtons(): Sets up the listeners for the buttons.
    private void setupButtons() {

        // FETCH BUTTON:
        fetchButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Log.d(LOG_TAG, "onClick(): Fetch button pressed.");

                // ASYNCTASK INITIALIZATION:
                queryTask = new QAPQueryTask();
                queryTask.execute(); // Executes the AsyncTask.
            }
        });
    }

    // updateView(): Updates the layout view after the QAPsQueryTask has completed.
    private void updateView(Boolean postsRetrieved) {

        progressIndicator.setVisibility(View.GONE); // Hides the progress indicator object.

        // Sets the list adapter for the RecyclerView object if the posts' data retrieval was
        // successful.
        if (postsRetrieved) {
            setUpRecyclerView(); // Sets up the RecyclerView object.
            setRecyclerList(postListResult); // Sets the adapter for the RecyclerView object.
        }
    }

    /** RECYCLERVIEW METHODS ___________________________________________________________________ **/

    private void setUpRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        alphaRecyclerView.setLayoutManager(layoutManager);
    }

    private void setRecyclerList(List<Datum> postList){
        QAPRecyclerAdapter recyclerAdapter = new QAPRecyclerAdapter(postList, this);
        alphaRecyclerView.setAdapter(recyclerAdapter);
    }

    /** RETROFIT METHODS _______________________________________________________________________ **/

    // retrieveLatestPosts(): Builds a RetrofitAdapter for retrieving the latest global posts via
    // Rest API request.
    private void retrieveLatestPosts() {

        // Builds a new RetrofitAdapter instance.
        Retrofit retrofitAdapter = new Retrofit.Builder()
                .baseUrl(QAPConstants.BASE_URL)
                .client(new OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Sets the interface for the Retrofit adapter.
        RetrofitInterface apiRequest = retrofitAdapter.create(RetrofitInterface.class);

        // Attempts to execute the Rest API request to retrieve the latest posts.
        try {
            postListResult = apiRequest.getLatestPosts().execute().body().getData();
        }

        // I/O Exception handler.
        catch (IOException e) {
            Log.e(LOG_TAG, "retrieveLatestPosts(): Exception occurred while trying to retrieve posts: " + e);
            e.printStackTrace();
        }
    }

    /** SUBCLASSES _____________________________________________________________________________ **/

    // QAPQueryTask(): An AsyncTask that runs in the background to process the network calls for
    // retrieving the latest global posts.
    public class QAPQueryTask extends AsyncTask<Void, Void, Void> {

        /** SUBCLASS VARIABLES _________________________________________________________________ **/

        Boolean isConnected = false; // Used to determine if the device has Internet connectivity.
        Boolean isError = false; // Used to determine if an error was encountered while processing the task.

        /** ASYNCTASK METHODS __________________________________________________________________ **/

        // onPreExecute(): This method runs on the UI thread just before the doInBackground method
        // executes.
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // Displays the progress indicator object.
            progressIndicator.setVisibility(View.VISIBLE);
        }

        // doInBackground(): This method constantly runs in the background while AsyncTask is
        // running.
        @Override
        protected Void doInBackground(Void... params) {

            // Checks the device's current network and Internet connectivity state.
            isConnected = QAPConnectivity.checkConnectivity(QAPMainActivity.this);

            // If connected to the Internet, this AsyncTask attempts to retrieve the latest global
            // posts.
            if (isConnected) {

                try {
                    Log.d(LOG_TAG, "QAPQueryTask(): Retrieving latest global posts...");
                    retrieveLatestPosts(); // Retrieves the posts from the Retrofit adapter.
                }

                // Exception error handler.
                catch (Exception e) {
                    isError = true; // Indicates an error has occurred.
                    Log.e(LOG_TAG, "QAPQueryTask(): An exception error occurred: " + e);
                }
            }

            else {
                isError = true; // Indicates an error has occurred.
                Toast.makeText(QAPMainActivity.this, "Your device is not connected to the Internet. Please check your settings and try again.", Toast.LENGTH_SHORT).show();
            }

            return null;
        }

        // onPostExecute(): This method runs on the UI thread after the doInBackground operation has
        // completed.
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            // If the AsyncTask was not cancelled, the recycler view is updated, as long as there
            // were no errors with the request.
            if (!isCancelled()) {

                // Runs on the UI thread.
                runOnUiThread(new Runnable() {

                    // Updates the layout view, as long as an error was not encountered.
                    public void run() {

                        if (!isError) {
                            updateView(true);
                        }
                    }
                });
            }
        }
    }
}
