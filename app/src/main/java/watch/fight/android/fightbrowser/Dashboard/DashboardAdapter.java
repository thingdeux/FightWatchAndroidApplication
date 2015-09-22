package watch.fight.android.fightbrowser.Dashboard;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import watch.fight.android.fightbrowser.R;

/**
 * Created by josh on 9/15/15.
 */
public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.ViewHolder> {
    private DashboardEntryHolder mEntryHolder;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mHeader;

        public ViewHolder(View v) {
            super(v);
            mHeader = (TextView) v.findViewById(R.id.dashboard_entry_title);
        }
    }



    // Provide Constructor for the dataset
    public DashboardAdapter() {
        mEntryHolder = DashboardEntryHolder.getInstance();
    }

    @Override
    public DashboardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dashboard_entry, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // Pull from dataset and replace contents of the viewholder here
        DashboardEntry de = mEntryHolder.getDashboardEntry(position);
        holder.mHeader.setText(de.getHeader());

    }

    // Dataset count here
    @Override
    public int getItemCount() { return mEntryHolder.getDashboardEntries().size(); }

}
