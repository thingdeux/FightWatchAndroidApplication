package watch.fight.android.fightbrowser.Dashboard;

import android.widget.TextView;

import java.util.List;

/**
 * Created by josh on 9/15/15.
 */
public class DashboardEntry {
    private String mHeader;
    private List<String> mItems;

    public String getHeader() {
        return mHeader;
    }

    public void setHeader(String header) {
        mHeader = header;
    }

    public List<String> getItems() {
        return mItems;
    }

    public void setItems(List<String> items) {
        mItems = items;
    }
}
