package com.huhx0015.quickalphapost.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import com.huhx0015.quickalphapost.R;
import butterknife.Bind;
import butterknife.ButterKnife;

public class QAPMainActivity extends AppCompatActivity {

    /** CLASS VARIABLES ________________________________________________________________________ **/

    // LOGGING VARIABLES
    private static final String LOG_TAG = QAPMainActivity.class.getSimpleName();

    // VIEW INJECTION VARIABLES
    @Bind(R.id.fetch_button) Button fetchButton;

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

                // TODO: Implement action here.
            }
        });
    }
}
