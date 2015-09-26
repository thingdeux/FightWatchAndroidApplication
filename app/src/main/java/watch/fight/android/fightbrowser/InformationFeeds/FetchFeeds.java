package watch.fight.android.fightbrowser.InformationFeeds;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

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
    public static final int ACCEPTABLE_TIME_SINCE_LAST_FEED_CHECK_IN_MINS = 30;  // TODO : Don't forget to change this to something reasonable

    public static class FetchStories extends AsyncTask<Void, Void, ArrayList<Story>> {
        private Context mContext;
        private DashboardAdapter mAdapter;
        private boolean mIsSubTasker = false;
        private String mUrl;

        public FetchStories(Context c, final DashboardAdapter adapter) {
            mContext = c;
            mAdapter = adapter;
        }

        public FetchStories(Context c) {
            mContext = c;
        }

        protected ArrayList<Story> doInBackground(Void... response) {
            ArrayList<Story> stories = new ArrayList<>();


            GregorianCalendar today = new GregorianCalendar();
            GregorianCalendar date = DateParser.epochToGregorian(SharedPreferences.getFeedsLastUpdated(mContext));
            date.add(Calendar.MINUTE, ACCEPTABLE_TIME_SINCE_LAST_FEED_CHECK_IN_MINS);

            // Only check for new feeds once every X minutes
            // Feed schedule will be set on the fragment, but sharedpref will determine whether or not the correct amount
            // of time has passed (in case the app goes to sleep or does something else, don't want it to reset the clock)
            if (today.after(date)) {
                Log.v("FetchStories", "Fetching new feeds");
                List<Feed> feeds = FeedDB.getInstance(mContext.getApplicationContext()).getAllFeeds();

                if (feeds != null) {
                    // Will Do Delete per story in processFeed
                    StoryDB.getInstance(mContext.getApplicationContext()).deleteAllStories();  // TODO : Just for Debug Purposes, remove this
                    for (int i = 0; i < feeds.size(); i++) {
                        processFeed(feeds.get(i).getName(),
                                feeds.get(i).getRSSUrl());
                    }
                }
                return stories;
            }
//            Log.i("JJDEBUG", "THE STORIES!");
//            for (Story s : StoryDB.getInstance(mContext.getApplicationContext()).getAllStories()) {
//                Log.i("Story", s.getSiteName() + " (" + s.getPublishedDate() + ") -> " + s.getTitle());
//            }
            return null;
        }

        protected void onPostExecute(ArrayList<Story> stories) {
            if (stories != null) {
                SharedPreferences.setFeedsLastUpdated(mContext, System.currentTimeMillis());

                // TODO : Will only be using this with Information Feeds Adapter
//                mAdapter.setStories(stories);
//                mAdapter.notifyDataSetChanged();
            }
        }

        protected void processFeed(String siteName, String url) {
            // TODO : Write Feed updates to DB and simply notify adapter that the dataset has changed.
            // Adapter will poll DB for the content.
            Log.v("ProcessFeed", "Fetching feed for: " + url);
            ArrayList<Story> stories = NetworkUtils.parseRss(siteName, url);
            if (stories != null) {
                StoryDB.getInstance(mContext.getApplicationContext()).addStories(stories);
            } else {
                Log.e("ProcessFeed", "Received error on " + url);
            }

        }

    }

    public static HashMap<String, Story> FetchLatestStories(Context context) {
        HashMap<String, Story> feedMapper = new HashMap<>();
        List<Feed> feeds = FeedDB.getInstance(context.getApplicationContext()).getAllFeeds();

        if (feeds != null) {
            for (int i = 0; i < feeds.size(); i++) {
                feedMapper.put(feeds.get(i).getName(),
                        getLatestStory(feeds.get(i).getName(), feeds.get(i).getRSSUrl()));
            }
        }
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
