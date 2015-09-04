package com.huhx0015.quickalphapost.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.huhx0015.quickalphapost.R;
import com.huhx0015.quickalphapost.interfaces.OnRecyclerViewUpdateListener;
import com.huhx0015.quickalphapost.interfaces.QAPApiInterface;
import com.huhx0015.quickalphapost.models.Datum;
import com.huhx0015.quickalphapost.models.User;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Michael Yoon Huh on 9/3/2015.
 */

public class QAPListAdapter extends RecyclerView.Adapter<QAPListAdapter.ListViewHolder> {

    /** CLASS VARIABLES ________________________________________________________________________ **/

    // ACTIVITY VARIABLES
    private Activity activity; // References the attached activity.

    // API VARIABLES
    private final static String BASE_URL = "https://api.app.net";

    // LIST VARIABLES
    private List<Datum> postList;

    // LOGGING VARIABLES
    private static final String LOG_TAG = QAPListAdapter.class.getSimpleName();

    /** INITIALIZATION METHODS _________________________________________________________________ **/

    // QAPListAdapter(): Constructor method.
    public QAPListAdapter(List<Datum> list, Activity act){
        this.activity = act;
        this.postList = list;
    }

    /** EXTENSION METHODS ______________________________________________________________________ **/

    // onCreateViewHolder: This method is called when the custom ViewHolder needs to be initialized.
    // The layout of each item of the RecyclerView is inflated using LayoutInflater, passing the
    // output to the constructor of the custom ViewHolder.
    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // Inflates the layout given the XML layout file for the item view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.qap_card_view, parent, false);

        return new ListViewHolder(view);
    }

    // onBindViewHolder(): Overrides the onBindViewHolder to specify the contents of each item of
    // the RecyclerView. This method is similar to the getView method of a ListView's adapter.
    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {

        // Sets the TextView objects.
        holder.userNameText.setText(postList.get(position).getUser().getName());
        holder.postDescriptionText.setText(postList.get(position).getText());
        holder.timeText.setText(postList.get(position).getCreatedAt());

        Log.d(LOG_TAG, "Time created at: " + postList.get(position).getCreatedAt());

        // AvatarImage is returning null in Datum, so this is a possible workaround.
        //String userId = postList.get(position).getUser().getId();
        //retrieveUserAvatar(userId, holder);

        // Retrieves the avatar image URL at the referenced position.
        // TODO: AvatarImage is returning null in Datum.
        String avatarImage = null;

        try {
            avatarImage = postList.get(position).getUser().getAvatarImage().getUrl();
        }

        catch (NullPointerException e) {
            Log.e(LOG_TAG, "onBindViewHolder(): Null pointer exception encountered while attempting to retrieve the avatar image URL.");
        }

        // Loads the referenced image into the ImageView object.
        if (avatarImage != null) {

            Picasso.with(activity)
                    .load(avatarImage)
                    .into(holder.avatarImage);
        }

        // If no referenced image exists, the application icon is set instead.
        else {

            Picasso.with(activity)
                    .load(R.mipmap.ic_launcher)
                    .into(holder.avatarImage);
        }
    }

    // getItemCount(): Returns the number of items present in the data.
    @Override
    public int getItemCount() {
        return postList.size();
    }

    // onAttachedToRecyclerView(): Overrides the onAttachedToRecyclerView method.
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    /** REST ADAPTER METHODS ___________________________________________________________________ **/

    // retrieveUserAvater(): Retrieves the user's avatar. This method is in response to the current
    // issue with the API where the AvatarImage in the Datum object is returning null.
    public void retrieveUserAvatar(String user_id, final ListViewHolder holder) {

        // Builds a new RestAdapter instance.
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .build();

        QAPApiInterface apiRequest = restAdapter.create(QAPApiInterface.class);

        apiRequest.getUser(user_id, new Callback<User>() {

            @Override
            public void success(User user, Response response) {
                Log.d(LOG_TAG, "Request success!" + response);

                // Retrieves the avatar image URL at the referenced position.
                String avatarImageUrl = null;

                try {
                    avatarImageUrl = user.getAvatarImage().getUrl();
                }

                catch (NullPointerException e) {
                    Log.e(LOG_TAG, "retrieveUserAvatar(): Null pointer exception encountered while attempting to retrieve the avatar image URL.");
                }

                // Loads the referenced image into the ImageView object.
                if (avatarImageUrl != null) {

                    Target target = new Target() {

                        // onBitmapLoaded(): Runs when the bitmap is loaded.
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            holder.avatarImage.setImageBitmap(bitmap); // Sets the bitmap image.
                            updateRecyclerView(); // Updates the RecyclerView.
                        }

                        // onBitmapFailed(): Runs when the bitmap failed to load.
                        @Override
                        public void onBitmapFailed(Drawable errorDrawable) {
                            Log.e(LOG_TAG, "setUpImage(): ERROR: Bitmap failed to load.");
                        }

                        // onPrepareLoad(): Runs prior to loading the bitmap.
                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {}
                    };

                    Picasso.with(activity)
                            .load(avatarImageUrl)
                            .into(target);
                }

                // If no referenced image exists, the application icon is set instead.
                else {

                    Picasso.with(activity)
                            .load(R.mipmap.ic_launcher)
                            .into(holder.avatarImage);

                    updateRecyclerView(); // Updates the RecyclerView.
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(LOG_TAG, "Request failed: " + error);
            }
        });
    }

    /** INTERFACE METHODS ______________________________________________________________________ **/

    private void updateRecyclerView() {
        try { ((OnRecyclerViewUpdateListener) activity).updateRecyclerView(); }
        catch (ClassCastException cce) {} // Catch for class cast exception errors.
    }

    /** SUBCLASSES _____________________________________________________________________________ **/

    public static class ListViewHolder extends RecyclerView.ViewHolder {

        /** SUBCLASS VARIABLES _________________________________________________________________ **/

        // LAYOUT VARIABLES:
        CardView postCardView;
        ImageView avatarImage;
        TextView userNameText;
        TextView postDescriptionText;
        TextView timeText;

        /** SUBCLASS METHODS ___________________________________________________________________ **/

        ListViewHolder(View itemView) {

            super(itemView);

            postCardView = (CardView) itemView.findViewById(R.id.cardview_container);
            avatarImage = (ImageView) itemView.findViewById(R.id.avatar_image);
            userNameText = (TextView) itemView.findViewById(R.id.name_text);
            postDescriptionText = (TextView) itemView.findViewById(R.id.post_text);
            timeText = (TextView) itemView.findViewById(R.id.time_text);
        }
    }
}