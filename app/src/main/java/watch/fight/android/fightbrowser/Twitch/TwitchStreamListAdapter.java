package watch.fight.android.fightbrowser.Twitch;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import watch.fight.android.fightbrowser.R;
import watch.fight.android.fightbrowser.Utils.ImageLoader;

/**
 * Created by josh on 9/11/15.
 */
public class TwitchStreamListAdapter extends RecyclerView.Adapter<TwitchStreamListAdapter.ViewHolder> {
    private TwitchStreamHolder mStreamHolder;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public ImageView mImageView;

        public ViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.twitch_stream_title);
            mImageView = (ImageView) v.findViewById(R.id.twitch_stream_header);
        }
    }

    // Provide constructor for the dataset to use in the recycler view
    public TwitchStreamListAdapter() {
        mStreamHolder = TwitchStreamHolder.getInstance();
    }

    @Override
    public TwitchStreamListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create a new View
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.twitch_list_item, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (called by layout manager
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Get element from the dataset and replace the current contents of the viewholder with it
        holder.mImageView.setImageResource(R.drawable.twitch_stream_default);
        ImageLoader loader = new ImageLoader(mStreamHolder.getFeatured(position).getStream().getPreview().getMediumImage(), holder.mImageView);
        loader.execute();
        // TODO : Only load images when the user is not scrolling, load Twitch PlaceHolder in each view until then
        holder.mTextView.setText(mStreamHolder.getFeatured(position).getTitle());
    }

    @Override
    public int getItemCount() {
//        Log.v("RecyclerAdapter", "Stream Holder has streams " + mData.length + " found");
        return mStreamHolder.getFeatured().length;
    }

}
