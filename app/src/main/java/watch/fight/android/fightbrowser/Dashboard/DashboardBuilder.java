package watch.fight.android.fightbrowser.Dashboard;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.HashMap;
import java.util.List;

import watch.fight.android.fightbrowser.Events.models.Event;
import watch.fight.android.fightbrowser.Events.models.EventDB;
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
    private DashboardFragment mDashboardFragment;
    private Context mContext;

    protected DashboardEntry[] doInBackground(DashboardBuilderValues... dbValues) {
        Log.i("ThreadCheck", "DashboardBuild on thread: " + Thread.currentThread());
        if (dbValues[0] != null) {
            mDashboardFragment = dbValues[0].mDashboardFragment;

            if (mDashboardFragment != null) {
                mContext = dbValues[0].mContext;
//            dashboards.add(buildStoryModule(mContext));
//            dashboards.add(buildEventsModule());
//            dashboards.add(buildStreamModule());
                DashboardEntry[] de = {
                        buildStoryModule(mContext),
                        buildEventsModule(mContext)
                };
                Log.v("DashboardBuilder", "Created dashboard entries");
                return de;
            }
        }
        return null;
    }

    protected void onPostExecute(DashboardEntry[] entries) {
        Log.i("DashboardBuilder", "PostExecute DashboardBuild");
        mDashboardFragment.mAdapter.setEntries(entries);
        mDashboardFragment.mAdapter.notifyDataSetChanged();
        mDashboardFragment.setUIReady();
    }

    private DashboardEntry buildStoryModule(Context context) {
        // Read from latest feeds - pop the top off of each feed
        HashMap<String, Story> latestStories = FetchFeeds.FetchLatestStories(context);

        if (latestStories != null) {
            StringBuilder sb = new StringBuilder(latestStories.size());
            DashboardEntry entry = new DashboardEntry();
            boolean isFirstItem = true;

            for (HashMap.Entry<String, Story> story : latestStories.entrySet()) {
                if (story.getValue() != null) {
                    String lineText = "";
                    if (!isFirstItem) {
                        lineText += StringUtils.multipleLineBreaks(2);
                    } else {
                        isFirstItem = false;
                    }

                     lineText += "(" + story.getKey() + ")" + " - " +
                            StringUtils.limitCharacters(story.getValue().getTitle(), 45, true);

                    sb.append(lineText);
                }
            }

            // System.getProperty("line.separator") + System.getProperty("line.separator")

            entry.setType(DashboardEntry.RSS_FEED_TYPE);
            entry.setHeader(mContext.getResources().getText(R.string.dashboard_stories_header_name).toString());
            entry.setContent(sb.toString());

            return entry;
        } else {
            return null;
        }
    }

    private DashboardEntry buildEventsModule(Context context) {
        // Get Upcoming Events
        List<Event> events = EventDB.getInstance(context).getAllEvents();
        if (events != null) {
            DashboardEntry dashboardEntry = new DashboardEntry();
            dashboardEntry.setType(DashboardEntry.EVENT_TYPE);
            dashboardEntry.setHeader("Upcoming FGC Events");
            StringBuilder sb = new StringBuilder(events.size());
            boolean isFirstItem = true;

            for (int i = 0; i < events.size(); i++) {
                if (isFirstItem) {
                    isFirstItem = false;
                } else {
                    sb.append(StringUtils.multipleLineBreaks(2));
                }
                // TODO : Query that only shows the next 3 events >= todays date - for now return all
                sb.append(StringUtils.limitCharacters(events.get(i).getEventName(), 45, true) +
                        "  (" + events.get(i).getDateObj().toString() + ")");
            }
            dashboardEntry.setContent(sb.toString());
            return dashboardEntry;
        }
        return null;
    }

    private DashboardEntry buildStreamModule() {
        // TwitchSearches - Get Counts
        return new DashboardEntry();
    }

    public static class DashboardBuilderValues {
        private DashboardFragment mDashboardFragment;
        private RecyclerView.Adapter mAdapter;
        private Context mContext;

        public DashboardBuilderValues(Context c, DashboardFragment dashboardFragment) {
            this.mContext = c;
            this.mDashboardFragment = dashboardFragment;
        }
    }

}
