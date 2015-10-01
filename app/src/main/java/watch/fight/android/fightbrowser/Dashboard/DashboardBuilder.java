package watch.fight.android.fightbrowser.Dashboard;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import watch.fight.android.fightbrowser.Events.models.Event;
import watch.fight.android.fightbrowser.Events.models.EventDB;
import watch.fight.android.fightbrowser.InformationFeeds.FetchFeeds;
import watch.fight.android.fightbrowser.InformationFeeds.models.Story;
import watch.fight.android.fightbrowser.R;
import watch.fight.android.fightbrowser.StreamBrowser.Twitch.models.TwitchStreamSummary;
import watch.fight.android.fightbrowser.Utils.Network.GsonRequest;
import watch.fight.android.fightbrowser.Utils.Network.NetworkRequest;
import watch.fight.android.fightbrowser.Utils.StringUtils;

/**
 * Created by josh on 9/25/15.
 */
public class DashboardBuilder extends AsyncTask<DashboardBuilder.DashboardBuilderValues, Void, DashboardEntry[]>{
    /*
     Gather top-level information from twitch streams / rss feeds / events and turn it into
     DashboardEntry items for the adapter.
    */
    private String TAG = DashboardBuilder.class.getSimpleName();
    private DashboardFragment mDashboardFragment;
    private Context mContext;
    private RequestQueue mRequestQueue;
    private ArrayList<TwitchStreamSummary> mSummaries = new ArrayList<>();
    private int mTimeAwaitingTwitchSummaryResponse = 0;

    protected DashboardEntry[] doInBackground(DashboardBuilderValues... dbValues) {
        Log.i("ThreadCheck", "DashboardBuild on thread: " + Thread.currentThread());
        if (dbValues[0] != null) {
            mDashboardFragment = dbValues[0].mDashboardFragment;

            if (mDashboardFragment != null) {
                mContext = dbValues[0].mContext;
                mRequestQueue = NetworkRequest.getInstance(mContext.getApplicationContext()).getRequestQueue();
                DashboardEntry[] de = {
                        buildStoryModule(mContext),
                        buildEventsModule(mContext),
                        buildStreamModule(mContext)
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
                        "  (" + events.get(i).getStartDateObj().toString() + ")");
            }
            dashboardEntry.setContent(sb.toString());
            return dashboardEntry;
        }
        return null;
    }

    private DashboardEntry buildStreamModule(Context context) {
        // TwitchSearches - Get Counts
        // TODO : Perhaps pass in synchronized list to prevent any concurrency issues.
        mRequestQueue.add(createStreamSummaryRequest("Ultra Street Fighter IV", mSummaries));
        mRequestQueue.add(createStreamSummaryRequest("Mortal Kombat X", mSummaries));
        mRequestQueue.add(createStreamSummaryRequest("Super Smash Bros. Melee", mSummaries));
        mRequestQueue.add(createStreamSummaryRequest("Ultimate Marvel vs. Capcom 3", mSummaries));


        // Wait 4 seconds or 3 summary responses
        while (mTimeAwaitingTwitchSummaryResponse < 4000) {
            if (mSummaries.size() >= 3) {
                break;
            }
            Log.i(TAG, "Waiting for twitch calls to finish: " + mTimeAwaitingTwitchSummaryResponse);
            try {
                Thread.sleep(100);
                mTimeAwaitingTwitchSummaryResponse += 100;
            } catch (InterruptedException ie) {
            }
        }

        DashboardEntry dashboardEntry = new DashboardEntry();
        dashboardEntry.setHeader("FGC Stream Activity on Twitch");
        dashboardEntry.setType(DashboardEntry.TWITCH_STREAM_COUNT);
        StringBuilder sb = new StringBuilder(mSummaries.size() * 3);
        boolean isFirstItem = true;

        for (int i = 0; i < mSummaries.size(); i++) {
            if (isFirstItem) {
                isFirstItem = false;
            } else {
                sb.append(StringUtils.multipleLineBreaks(2));
            }
            sb.append(mSummaries.get(i).getGameNameFromQuery());
            sb.append(StringUtils.multipleLineBreaks(1));
            sb.append("   Live Streams " +  mSummaries.get(i).getChannels()
                      + mSummaries.get(i).getViewers() + " Viewers");
        }
        dashboardEntry.setContent(sb.toString());
        return dashboardEntry;
    }

    private static Response.Listener<TwitchStreamSummary> twitchResponseListener(final ArrayList<TwitchStreamSummary> summaries) {
        // NOTE!! Volley returns responses on the main thread, so keep this light.
        return new Response.Listener<TwitchStreamSummary>() {
            @Override
            public void onResponse(TwitchStreamSummary response) {
                summaries.add(response);
            }
        };
    }

    private static Response.ErrorListener twitchErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VolleyError", "Error Retrieving twitch summary response! - " + error.toString());
            }
        };
    }

    private static GsonRequest<TwitchStreamSummary> createStreamSummaryRequest(String game,
                                                                               ArrayList<TwitchStreamSummary> summaries) {
        return new GsonRequest<TwitchStreamSummary>(
                "https://api.twitch.tv/kraken/streams/summary?game=" + game.replace(" ", "+"),
                TwitchStreamSummary.class,
                null,
                twitchResponseListener(summaries),
                twitchErrorListener()
        );
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
