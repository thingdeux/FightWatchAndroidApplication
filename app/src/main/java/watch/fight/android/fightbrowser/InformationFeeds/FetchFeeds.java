package watch.fight.android.fightbrowser.InformationFeeds;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import watch.fight.android.fightbrowser.Dashboard.DashboardActivity;
import watch.fight.android.fightbrowser.Dashboard.DashboardBuilder;
import watch.fight.android.fightbrowser.Dashboard.DashboardFragment;
import watch.fight.android.fightbrowser.InformationFeeds.models.Feed;
import watch.fight.android.fightbrowser.InformationFeeds.models.DB.FeedDB;
import watch.fight.android.fightbrowser.InformationFeeds.models.Story;
import watch.fight.android.fightbrowser.InformationFeeds.models.DB.StoryDB;
import watch.fight.android.fightbrowser.Utils.DateParser;
import watch.fight.android.fightbrowser.Utils.Network.ParseUtils;
import watch.fight.android.fightbrowser.Utils.SharedPreferences;

/**
 * Created by josh on 9/22/15.
 */
public class FetchFeeds {
    public static final String TAG = FetchFeeds.class.getSimpleName();
    public static final int ACCEPTABLE_TIME_SINCE_LAST_FEED_CHECK_IN_MINS = 15;

    public static class FetchStories extends AsyncTask<Void, Void, Boolean> {
        private Context mContext;
        private DashboardFragment mDashboardFragment;
        private DashboardActivity mDashboardActivity;
        private InformationFeedsFragment mInformationFeedsFragment;
        private InformationFeedsActivity mInformationFeedsActivity;
        private RecyclerView.Adapter mAdapter;
        private boolean mIsSubTasker = false;
        private String mUrl;
        private boolean mIsForcedRefresh = false;

        // Allows for passing in of a recyclerview adapter which will notify the recyclerviews adapter on finish.
        public FetchStories(Context c, final RecyclerView.Adapter adapter, InformationFeedsFragment fragment, boolean isForcedRefresh) {
            mContext = c;
            mAdapter = adapter;
            mIsForcedRefresh = isForcedRefresh;
            mInformationFeedsFragment = fragment;
        }

        public FetchStories(final DashboardFragment fragment, final FragmentActivity activity) {
            mContext = activity;
            mDashboardFragment = fragment;
            DashboardActivity da = (DashboardActivity) activity;
            if (da != null) {
                mDashboardActivity = da;
            }
        }

        public FetchStories(final InformationFeedsFragment fragment, final FragmentActivity activity) {
            mContext = activity;
            mInformationFeedsFragment = fragment;
            InformationFeedsActivity ifa = (InformationFeedsActivity) activity;
            if (ifa != null) {
                mInformationFeedsActivity = ifa;
            }
        }

        // Created to just allow for refreshing and re-population of stories without notifying a recyclerview.
        public FetchStories(Context c) {
            mContext = c;
        }

        protected Boolean doInBackground(Void... x) {
            GregorianCalendar today = new GregorianCalendar();
            GregorianCalendar date = DateParser.epochToGregorian(SharedPreferences.getFeedsLastUpdated(mContext));
            date.add(Calendar.MINUTE, ACCEPTABLE_TIME_SINCE_LAST_FEED_CHECK_IN_MINS);

            // Only check for new feeds once every X minutes (defined above)
            // Feed schedule will be set on the fragment, but sharedpref will determine whether or not the correct amount
            // of time has passed (in case the app goes to sleep or does something else, don't want it to reset the clock)
            if (today.after(date) || mIsForcedRefresh) {
                updateStories();
//                new ASyncRSSFeedLoader(this).updateStoriesAsync();
                SharedPreferences.setFeedsLastUpdatedToNow(mContext);
                return true;
            }
            return false;
        }

        protected void onPostExecute(Boolean didUpdate) {
            if (mDashboardFragment != null) {
                // If this is called from the Dashboard Activity, spin up the dashboardBuilder after fetching new stories.
                new DashboardBuilder().execute(new DashboardBuilder.DashboardBuilderValues(mDashboardFragment.getActivity(), mDashboardFragment));
            }

            if (mAdapter != null) {
                // Notify the recyclerview adapter if one has been passed in.
                mAdapter.notifyDataSetChanged();
            }

            if (mInformationFeedsFragment != null) {
                mInformationFeedsFragment.setUIReady();
            }

            if (didUpdate) {
                Toast t = Toast.makeText(mContext, "Fetched New Feeds!", Toast.LENGTH_SHORT);
                t.show();
            }
        }

//        public void enoughFeedsRetrieved(List<RSSFeed> feeds) {
//            Log.i("IFeedRetrieval", "Received Feeds!");
//            loadProcessedFeedsIntoDB(feeds);
//        }

        private void updateStories() {
            Log.v("FetchStories", "Fetching new feeds");
            List<Feed> feeds = FeedDB.getInstance(mContext.getApplicationContext()).getAllFeeds();

            if (feeds != null) {
                for (int i = 0; i < feeds.size(); i++) {
                    processFeed(feeds.get(i), feeds.get(i).getRSSUrl());
                }
            }
        }

//        protected void loadProcessedFeedsIntoDB(List<RSSFeed> feeds) {
//            if (feeds != null) {
//                for (int i = 0; i < feeds.size(); i++) {
//                    if (feeds.get(i) != null) {
//                        StoryDB DB = StoryDB.getInstance(mContext.getApplicationContext());
//                        // Delete all stories from the given site and add the new updates
//                        // will only delete if the feed has been succesfully gathered
////                        DB.deleteStoriesBySiteName(siteName);
//                        List<RSSItem> items = feeds.get(i).getItems();
//                        if (items != null) {
//                            DB.addStories(ParseUtils.getStoriesFromFeed(items));
//                        }
//
//                    } else {
//                        Log.e("ProcessFeed", "Received error loadingProcessedFeedsIntoDb");
//                    }
//                }
//
//            }
//        }

        protected void processFeed(Feed site, String url) {
            Log.v("ProcessFeed", "Fetching feed for: " + url);
            ArrayList<Story> stories = ParseUtils.parseRss(site, url);
            if (stories != null) {
                StoryDB DB = StoryDB.getInstance(mContext.getApplicationContext());
                // Delete all stories from the given site and add the new updates
                // will only delete if the feed has been succesfully gathered
                DB.deleteStoriesBySiteName(site.getName());
                DB.addStories(stories);
            } else {
                Log.e("ProcessFeed", "Received error on " + url);
            }
        }

    }

    public static HashMap<String, Story> FetchLatestStories(Context context) {
        HashMap<String, Story> feedMapper = new HashMap<>();
        List<Story> stories = StoryDB.getInstance(context.getApplicationContext()).getTopStoryForEachSite();

        if (stories != null) {
            for (int i = 0; i < stories.size(); i++) {
                feedMapper.put(stories.get(i).getSiteName(), stories.get(i));
            }
        }

        return feedMapper;
    }

//    protected static Story getLatestStory(String siteName, String url) {
//        // TODO : Will check the DB First and if it hasn't been updated in a while kickoff an update task while returning what it found.
//        // So that the next request will be fresh.
//        Log.i("ProcessFeed", "Fetching feed for: " + url);
//        ArrayList<Story> stories = ParseUtils.parseRss(siteName, url);
//        if (stories != null) {
//            return stories.get(0);
//        } else {
//            Log.e("ProcessFeed", "Received error on " + url);
//            return null;
//        }
//    }
}
