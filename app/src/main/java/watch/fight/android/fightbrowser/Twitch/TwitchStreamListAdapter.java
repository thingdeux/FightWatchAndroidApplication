package watch.fight.android.fightbrowser.Twitch;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import watch.fight.android.fightbrowser.R;

/**
 * Created by josh on 9/11/15.
 */
public class TwitchStreamListAdapter extends RecyclerView.Adapter<TwitchStreamListAdapter.ViewHolder> {
    private TwitchFeaturedStream[] mData;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View mLinearLayout;
        public ViewHolder(View v) {
            super(v);
            mLinearLayout = v;
        }
    }

    // Provide constructor for the dataset to use in the recycler view
    public TwitchStreamListAdapter(TwitchFeaturedStream[] data) {
        mData = data;
    }

    @Override
    public TwitchStreamListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create a new View
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.twitch_list, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (called by layout manager
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Get element from the dataset and replace the current contents of the viewholder with it
        TextView tv = (TextView) holder.mLinearLayout.findViewById(R.id.twitch_stream_title);
        tv.setText(mData[position].getTitle());
    }

    @Override
    public int getItemCount() {
        return mData.length;
    }

}
