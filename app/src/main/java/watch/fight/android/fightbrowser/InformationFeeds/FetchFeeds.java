package watch.fight.android.fightbrowser.InformationFeeds;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import watch.fight.android.fightbrowser.Dashboard.DashboardAdapter;
import watch.fight.android.fightbrowser.InformationFeeds.models.Feed;
import watch.fight.android.fightbrowser.InformationFeeds.models.FeedDB;
import watch.fight.android.fightbrowser.InformationFeeds.models.Story;
import watch.fight.android.fightbrowser.InformationFeeds.models.StoryDB;
import watch.fight.android.fightbrowser.Utils.DateParser;
import watch.fight.android.fightbrowser.Utils.NetworkUtils;
import watch.fight.android.fightbrowser.Utils.SharedPreferences;

/**
 * Created by josh on 9/22/15.
 */
public class FetchFeeds {
    public static final String TAG = FetchFeeds.class.getSimpleName();
    public static final int ACCEPTABLE_TIME_SINCE_LAST_FEED_CHECK_IN_MINS = 15;

    public static class FetchStories extends AsyncTask<Void, Void, ArrayList<Story>> {
        private Context mContext;
        private RecyclerView.Adapter mAdapter;
        private boolean mIsSubTasker = false;
        private String mUrl;
        private boolean mIsForcedRefresh = false;

        // Allows for passing in of a recyclerview adapter which will notify the recyclerviews adapter on finish.
        public FetchStories(Context c, final RecyclerView.Adapter adapter, boolean isForcedRefresh) {
            mContext = c;
            mAdapter = adapter;
            mIsForcedRefresh = isForcedRefresh;
        }

        // Created to just allow for refreshing and re-population of stories without notifying a recyclerview.
        public FetchStories(Context c) {
            mContext = c;
        }

        protected ArrayList<Story> doInBackground(Void... response) {
            Log.d(TAG, "ForcedRefresh? -> " + mIsForcedRefresh);
            GregorianCalendar today = new GregorianCalendar();
            GregorianCalendar date = DateParser.epochToGregorian(SharedPreferences.getFeedsLastUpdated(mContext));
            date.add(Calendar.MINUTE, ACCEPTABLE_TIME_SINCE_LAST_FEED_CHECK_IN_MINS);

            // Only check for new feeds once every X minutes
            // Feed schedule will be set on the fragment, but sharedpref will determine whether or not the correct amount
            // of time has passed (in case the app goes to sleep or does something else, don't want it to reset the clock)
            if (today.after(date) || mIsForcedRefresh) {
                return getStories();
            }
            return null;
        }

        protected void onPostExecute(ArrayList<Story> stories) {
            if (stories != null) {
                SharedPreferences.setFeedsLastUpdated(mContext, System.currentTimeMillis());
                Toast t = Toast.makeText(mContext, "Fetched New Feeds!", Toast.LENGTH_SHORT);
                t.show();
                if (mAdapter != null) {
                    // Notify the recyclerview adapter if one has been passed in.
                    mAdapter.notifyDataSetChanged();
                }
            }
        }

        private ArrayList<Story> getStories() {
            ArrayList<Story> stories = new ArrayList<>();
            Log.v("FetchStories", "Fetching new feeds");
            List<Feed> feeds = FeedDB.getInstance(mContext.getApplicationContext()).getAllFeeds();

            if (feeds != null) {
                for (int i = 0; i < feeds.size(); i++) {
                    processFeed(feeds.get(i).getName(),
                            feeds.get(i).getRSSUrl());
                }
            }
            return stories;
        }

        protected void processFeed(String siteName, String url) {
            Log.v("ProcessFeed", "Fetching feed for: " + url);
            ArrayList<Story> stories = NetworkUtils.parseRss(siteName, url);
            if (stories != null) {
                StoryDB DB = StoryDB.getInstance(mContext.getApplicationContext());
                // Delete all stories from the given site and add the new updates
                // will only delete if the feed has been succesfully gathered
                DB.deleteStoriesBySiteName(siteName);
                DB.addStories(stories);
            } else {
                Log.e("ProcessFeed", "Received error on " + url);
            }

        }

    }

    public static HashMap<String, Story> FetchLatestStories(Context context) {
        HashMap<String, Story> feedMapper = new HashMap<>();
        List<Feed> feeds = FeedDB.getInstance(context.getApplicationContext()).getAllFeeds();
        List<Story> stories = StoryDB.getInstance(context.getApplicationContext()).getTopStoryForEachSite();

        if (stories != null) {
            for (int i = 0; i < stories.size(); i++) {
                feedMapper.put(stories.get(i).getSiteName(), stories.get(i));
            }
        }

//        if (feeds != null) {
//            for (int i = 0; i < feeds.size(); i++) {
//                feedMapper.put(feeds.get(i).getName(),
//                        getLatestStory(feeds.get(i).getName(), feeds.get(i).getRSSUrl()));
//            }
//        }

        // TODO :  Kappa Feed is coming back with /Too many requests .... only sometimes.
//        feedMapper.put("INTENTIONALERROR", getLatestStory("http://www.eventhubs.com/feeds/"));


        return feedMapper;
    }

    protected static Story getLatestStory(String siteName, String url) {
        // TODO : Will check the DB First and if it hasn't been updated in a while kickoff an update task while returning what it found.
        // So that the next request will be fresh.
        Log.v("ProcessFeed", "Fetching feed for: " + url);
        ArrayList<Story> stories = NetworkUtils.parseRss(siteName, url);
        if (stories != null) {
            Log.d("FeedCounts", url + " -> " + stories.size());
            return stories.get(0);
        } else {
            Log.e("ProcessFeed", "Received error on " + url);
            return null;
        }
    }
}
