package watch.fight.android.fightbrowser.Dashboard;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

import watch.fight.android.fightbrowser.InformationFeeds.FetchFeeds;
import watch.fight.android.fightbrowser.InformationFeeds.models.Story;
import watch.fight.android.fightbrowser.R;
import watch.fight.android.fightbrowser.Utils.StringUtils;

/**
 * Created by josh on 9/25/15.
 */
public class DashboardBuilder extends AsyncTask<DashboardBuilder.DashboardBuilderValues, Void, DashboardEntry[]>{
    /*
     Gather top-level information from twitch streams / rss feeds / events and turn it into
     DashboardEntry items for the adapter.
    */
    private DashboardAdapter mAdapter;
    private Context mContext;

    protected DashboardEntry[] doInBackground(DashboardBuilderValues... dbValues) {
        if (dbValues[0] != null) {
            mAdapter = dbValues[0].mAdapter;
            mContext = dbValues[0].mContext;
//            dashboards.add(buildStoryModule(mContext));
//            dashboards.add(buildEventsModule());
//            dashboards.add(buildStreamModule());
            DashboardEntry[] de = {buildStoryModule(mContext)};
            Log.v("DashboardBuilder", "Created dashboard entries");
            return de;
        } else {
            return null;
        }

    }

    protected void onPostExecute(DashboardEntry[] entries) {
        mAdapter.setEntries(entries);
        mAdapter.notifyDataSetChanged();
    }

    private DashboardEntry buildStoryModule(Context context) {
        // Read from latest feeds - pop the top off of each feed
        HashMap<String, Story> latestStories = FetchFeeds.FetchLatestStories(context);

        if (latestStories != null) {
            StringBuilder sb = new StringBuilder(latestStories.size());
            DashboardEntry entry = new DashboardEntry();

            for (HashMap.Entry<String, Story> story : latestStories.entrySet()) {
                sb.append(story.getKey() + "\t" +
                        StringUtils.limitCharacters(story.getValue().getTitle(), 45, true) +
                        System.getProperty("line.separator"));
            }

            entry.setType(DashboardEntry.RSS_FEED_TYPE);
            entry.setHeader(mContext.getResources().getText(R.string.dashboard_stories_header_name).toString());
            entry.setContent(sb.toString());

            return entry;
        } else {
            return null;
        }
    }

    private DashboardEntry buildEventsModule() {
        // Get Upcoming Events
        return new DashboardEntry();
    }

    private DashboardEntry buildStreamModule() {
        // TwitchSearches - Get Counts
        return new DashboardEntry();
    }

    public static class DashboardBuilderValues {
        private DashboardAdapter mAdapter;
        private Context mContext;

        DashboardBuilderValues(Context c, DashboardAdapter da) {
            this.mContext = c;
            this.mAdapter = da;
        }
    }

}
