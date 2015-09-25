package watch.fight.android.fightbrowser.InformationFeeds;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import watch.fight.android.fightbrowser.InformationFeeds.models.Story;
import watch.fight.android.fightbrowser.R;

/**
 * Created by josh on 9/25/15.
 */
public class InformationFeedsAdapter extends RecyclerView.Adapter<InformationFeedsAdapter.ViewHolder> {
    private ArrayList<Story> mStories;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView mStoryHeader;
        public TextView mStoryTitle;
        public Uri mLinkUri;

        public ViewHolder(View v) {
            super(v);
            // TODO : Update to event layouts
            mStoryTitle = (TextView) v.findViewById(R.id.dashboard_entry_content);
            mStoryHeader = (TextView) v.findViewById(R.id.dashboard_entry_header);
        }

    }

    public InformationFeedsAdapter(ArrayList<Story> feeds) {
        mStories = feeds;
    }

    @Override
    public InformationFeedsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dashboard_entry, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mStoryHeader.setText("Street Fighter");
        holder.mStoryTitle.setText(mStories.get(position).getTitle());
        holder.mLinkUri = mStories.get(position).getUrl();
    }

    @Override
    public int getItemCount() { return mStories.size(); }

    public void setStories(@NonNull ArrayList<Story> stories) {
        mStories = stories;
    }
}
