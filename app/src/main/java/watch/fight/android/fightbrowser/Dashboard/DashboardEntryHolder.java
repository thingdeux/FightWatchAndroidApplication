package watch.fight.android.fightbrowser.Dashboard;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by josh on 9/15/15.
 */
public class DashboardEntryHolder {
    private static DashboardEntryHolder mDashboardEntryHolder;
    private ArrayList<DashboardEntry> mDashboardEntries;

    public static DashboardEntryHolder getInstance() {
        if (mDashboardEntryHolder == null) {
            mDashboardEntryHolder = new DashboardEntryHolder();
            ArrayList<DashboardEntry> placeholder = new ArrayList<>();
            DashboardEntry de = new DashboardEntry();
            de.setHeader("Test Header #1");
            placeholder.add(de);
            mDashboardEntryHolder.setDashboardEntries(placeholder);
        }
        return mDashboardEntryHolder;
    }

    protected DashboardEntryHolder() {
    }

    public List<DashboardEntry> getDashboardEntries() {
        return mDashboardEntries;
    }

    public DashboardEntry getDashboardEntry(int position) {
        return mDashboardEntries.get(position);
    }

    public void setmDashboardEntry(int position, DashboardEntry entry) {
        mDashboardEntries.set(position, entry);
    }

    public void setDashboardEntries(ArrayList<DashboardEntry> dashboardEntries) {
        mDashboardEntries = dashboardEntries;
    }

}
