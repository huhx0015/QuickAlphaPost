package com.huhx0015.quickalphapost.activities;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.huhx0015.quickalphapost.connection.QAPConnectivity;
import com.huhx0015.quickalphapost.interfaces.OnRecyclerViewUpdateListener;
import com.huhx0015.quickalphapost.ui.QAPListAdapter;
import com.huhx0015.quickalphapost.R;
import com.huhx0015.quickalphapost.interfaces.QAPApiInterface;
import com.huhx0015.quickalphapost.models.AlphaPost;
import com.huhx0015.quickalphapost.models.Datum;
import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class QAPMainActivity extends AppCompatActivity implements OnRecyclerViewUpdateListener {

    /** CLASS VARIABLES ________________________________________________________________________ **/

    // API VARIABLES
    private final static String BASE_URL = "https://api.app.net";

    // ASYNCTASK VARIABLES
    private QAPQueryTask task; // References the AsyncTask.

    // LAYOUT VARIABLES
    private QAPListAdapter recyclerAdapter;

    // LIST VARIABLES
    private List<Datum> postListResult;

    // LOGGING VARIABLES
    private static final String LOG_TAG = QAPMainActivity.class.getSimpleName();

    // VIEW INJECTION VARIABLES
    @Bind(R.id.fetch_button) Button fetchButton;
    @Bind(R.id.qap_recycler_view) RecyclerView alphaRecyclerView;
    @Bind(R.id.qap_progress_indicator) ProgressBar progressIndicator;

    /** ACTIVITY METHODS _______________________________________________________________________ **/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Checks to see if there is a saved instance that was saved prior, from events such as
        // rotation changes.
        if (savedInstanceState != null) {
            // TODO: Add values for restoring saved instance values on rotation.
        }

        setupLayout();
    }

    /** ACTIVITY EXTENSION METHODS _____________________________________________________________ **/

    // Inflate the menu; this adds items to the action bar if it is present.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //getMenuInflater().inflate(R.menu.qap_main_activity, menu);
        return true;
    }

    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // onSaveInstanceState(): Called to retrieve per-instance state from an activity before being
    // killed so that the state can be restored in onCreate() or onRestoreInstanceState().
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        // Saves current state values into the instance. These values are restored upon re-creation
        // of the activity.
        // TODO: Add items to save here before screen orientation calls.

        // Always calls the superclass, so it can save the view hierarchy state.
        super.onSaveInstanceState(savedInstanceState);
    }


    /** LAYOUT METHODS _________________________________________________________________________ **/

    private void setupLayout() {

        setContentView(R.layout.qap_main_activity); // Sets the XML layout for the activity.
        ButterKnife.bind(this); // ButterKnife view injection initialization.

        setupButtons();
    }

    private void setupButtons() {

        fetchButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Log.d(LOG_TAG, "onClick(): Fetch button pressed.");

                // ASYNCTASK INITIALIZATION:
                task = new QAPQueryTask();
                task.execute(); // Executes the AsyncTask.
            }
        });
    }

    // updateView(): Updates the layout view after the QAPsQueryTask has completed.
    public void updateView(Boolean postsRetrieved) {

        progressIndicator.setVisibility(View.GONE); // Hides the progress indicator object.

        // Sets the list adapter for the RecyclerView object if the posts' data retrieval was
        // successful.
        if (postsRetrieved) {
            setUpRecyclerView(); // Sets up the RecyclerView object.
            setListAdapter(postListResult); // Sets the adapter for the RecyclerView object.
        }
    }

    /** RECYCLERVIEW METHODS ___________________________________________________________________ **/

    private void setListAdapter(List<Datum> postList){
        recyclerAdapter = new QAPListAdapter(postList, this);
        alphaRecyclerView.setAdapter(recyclerAdapter);
    }

    private void setUpRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        alphaRecyclerView.setLayoutManager(layoutManager);
    }

    /** REST ADAPTER METHODS ___________________________________________________________________ **/

    public void retrieveLatestPosts() {

        //postListResult = new ArrayList<>(); // Initializes the ArrayList.

        // Builds a new RestAdapter instance.
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .build();

        QAPApiInterface apiRequest = restAdapter.create(QAPApiInterface.class);

        apiRequest.getLatestPosts(new Callback<AlphaPost>() {

            @Override
            public void success(AlphaPost alphaPost, Response response) {
                postListResult = alphaPost.getData();
                updateView(true);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(LOG_TAG, "Request failed: " + error);
            }
        });
    }

    /** INTERFACE METHODS ______________________________________________________________________ **/

    // updateRecyclerView(): Updates the RecyclerView object.
    @Override
    public void updateRecyclerView() {

        if (recyclerAdapter != null) {
            recyclerAdapter.notifyDataSetChanged();
        }
    }

    /** SUBCLASSES _____________________________________________________________________________ **/

    public class QAPQueryTask extends AsyncTask<Void, Void, Void> {

        /** SUBCLASS VARIABLES _________________________________________________________________ **/

        Boolean isConnected = false; // Used to determine if the device has Internet connectivity.
        Boolean isError = false; // Used to determine if an error has occurred or not.
        Boolean postsRetrieved = false; // Used to determine if post retrieval was successful or not.

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

            if (isConnected) {

                try {

                    Log.d(LOG_TAG, "QAPQueryTask(): Beginning Quick Alpha Post query...");

                    retrieveLatestPosts(); // Retrieves the posts from the rest adapter.

                    // If the postListResult object is null, it indicates an error has occurred and
                    // that the retrieval of the list of posts was a failure.
                    if (postListResult == null) {
                        isError = true;
                        postsRetrieved = false;
                        Log.e(LOG_TAG, "ERROR: QAPQueryTask(): The post list result was invalid.");
                    }

                    // Indicates that the retrieval of the posts was a failure.
                    else if (postListResult.size() < 1) {
                        postsRetrieved = false;
                    }

                    // Otherwise, the retrieval of the list of posts was successful.
                    else {
                        postsRetrieved = true;
                    }
                }

                // Exception error handler.
                catch (Exception e) {
                    Log.e(LOG_TAG, "doInBackground: An error was encountered during Spotify API access: " + e);
                }
            }

            else {
                Toast.makeText(QAPMainActivity.this, "Your device is not connected to the Internet. Please check your settings and try again.", Toast.LENGTH_SHORT).show();
            }

            return null;
        }

        // onPostExecute(): This method runs on the UI thread after the doInBackground operation has
        // completed.
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
