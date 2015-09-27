package watch.fight.android.fightbrowser.Dashboard;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import watch.fight.android.fightbrowser.InformationFeeds.InformationFeedsActivity;
import watch.fight.android.fightbrowser.InformationFeeds.models.Story;
import watch.fight.android.fightbrowser.R;
import watch.fight.android.fightbrowser.StreamBrowser.BrowserActivity;

/**
 * Created by josh on 9/15/15.
 */
public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.ViewHolder> {
    private DashboardEntry[] mEntries;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView mEntryHeader;
        public TextView mEntryContent;
        public Button mReadMore;
        public Uri mLinkUri;

        public ViewHolder(View v) {
            super(v);
            mEntryContent = (TextView) v.findViewById(R.id.dashboard_entry_content);
            mEntryHeader = (TextView) v.findViewById(R.id.dashboard_entry_header);
            mReadMore = (Button) v.findViewById(R.id.dashboard_read_more_button);
        }

    }

    public DashboardAdapter(DashboardEntry[] entries) {
        mEntries = entries;
    }

    @Override
    public DashboardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dashboard_entry, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if(mEntries[position] != null) {
            holder.mEntryHeader.setText(mEntries[position].getHeader());
            holder.mEntryContent.setText(mEntries[position].getContent());
            holder.mReadMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext().getApplicationContext(), InformationFeedsActivity.class);
                    v.getContext().startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() { return mEntries.length; }

    public void setEntries(@NonNull DashboardEntry[] entries) {
        mEntries = entries;
    }

}
