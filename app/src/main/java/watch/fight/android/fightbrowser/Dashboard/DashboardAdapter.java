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

import java.util.ArrayList;
import java.util.List;

import watch.fight.android.fightbrowser.InformationFeeds.models.Story;
import watch.fight.android.fightbrowser.R;

/**
 * Created by josh on 9/15/15.
 */
public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.ViewHolder> {
//    private DashboardEntryHolder mEntryHolder;
    private ArrayList<Story> mStories;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mHeader;
        public Uri mLinkUri;

        public ViewHolder(View v) {
            super(v);
            mHeader = (TextView) v.findViewById(R.id.dashboard_entry_title);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(mLinkUri);
            Log.v("DashboardAdapter", "" + mLinkUri);
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
        holder.mHeader.setText(mStories.get(position).getTitle());
        holder.mLinkUri = mStories.get(position).getUrl();
    }

//    @Override
//    public int getItemCount() { return mEntryHolder.getDashboardEntries().size(); }

    @Override
    public int getItemCount() { return mStories.size(); }

    public void setStories(@NonNull ArrayList<Story> stories) {
        mStories = stories;
    }


}
