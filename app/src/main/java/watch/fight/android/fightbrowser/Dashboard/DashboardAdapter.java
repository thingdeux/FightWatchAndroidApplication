package watch.fight.android.fightbrowser.Dashboard;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import watch.fight.android.fightbrowser.InformationFeeds.models.Story;
import watch.fight.android.fightbrowser.R;

/**
 * Created by josh on 9/15/15.
 */
public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.ViewHolder> {
//    private DashboardEntryHolder mEntryHolder;
    private ArrayList<Story> mStories;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView mStoryHeader;
        public TextView mStoryTitle;
        public Uri mLinkUri;

        public ViewHolder(View v) {
            super(v);
            mStoryTitle = (TextView) v.findViewById(R.id.dashboard_entry_title);
            mStoryHeader = (TextView) v.findViewById(R.id.dashboard_entry_header);
        }

    }

    public DashboardAdapter(ArrayList<Story> feeds) {
        mStories = feeds;
    }

    @Override
    public DashboardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dashboard_entry, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
//        holder.mStoryHeader.setText(mStories.get(position).getAuthor());
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
