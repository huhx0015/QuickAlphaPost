package com.huhx0015.quickalphapost;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.huhx0015.quickalphapost.models.Datum;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael Yoon Huh on 9/3/2015.
 */

public class QAPListAdapter extends RecyclerView.Adapter<QAPListAdapter.ListViewHolder> {

    /** CLASS VARIABLES ________________________________________________________________________ **/

    // ACTIVITY VARIABLES
    private Activity activity; // References the attached activity.

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

        // Retrieves the avatar image URL at the referenced position.
        String avatarImage = null;

        try {
            avatarImage = postList.get(position).getUser().getAvatarImage().getUrl();
        }

        catch (NullPointerException e) {
            //Log.d(LOG_TAG, "User ID: " + postList.get(position).getUser().getAvatarImage().toString());
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