package watch.fight.android.fightbrowser.InformationFeeds;

import android.os.AsyncTask;
import android.util.Log;

import org.mcsoxford.rss.RSSFeed;
import org.mcsoxford.rss.RSSLoader;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by josh on 9/28/15.
 * This Feed loader will get just enough feeds to populate an entry on the dashboard and return them
 * (while continuing to process and load the DB in the background)
 */
//public class ASyncRSSFeedLoader {
//    private static String TAG = ASyncRSSFeedLoader.class.getSimpleName();
//    private static final int NUMBER_OF_FEEDS_BEFORE_PASSING_TO_CALLER = 3;
//    private boolean mHasPassedResults = false;
//    AsyncTask mTaskFetcher;
//
//    ASyncRSSFeedLoader(AsyncTask task) {
//        mTaskFetcher = task;
//    }
//
//
//    public void updateStoriesAsync() {
//        // Start the processing queue - poll and yank out feeds until X number of feeds have been reached.
//        // Fire a callback that provides the feeds. But don't stop processing the others.
//        ArrayList<RSSFeed> finishedFeeds = new ArrayList<>();
//        RSSLoader loader = RSSLoader.fifo();
//        loader.load("http://www.eventhubs.com/feeds/latest/");
//        loader.load("https://www.reddit.com/r/kappa/.rss");
//        loader.load("https://www.reddit.com/r/smashbros/.rss");
//
//        while (loader.isLoading()) {
//            try {
//                Log.i("upDatedStoriesAsync", "Polling Async loader");
//                Future<RSSFeed> feed = loader.poll();  // Non-Blocking
//                if (feed != null && feed.isDone()) {
//                    finishedFeeds.add(feed.get());
//                    if (finishedFeeds.size() >= NUMBER_OF_FEEDS_BEFORE_PASSING_TO_CALLER && ! mHasPassedResults) {
//                        FetchFeeds.FetchStories fetcher = (FetchFeeds.FetchStories) mTaskFetcher;
////                        if (fetcher != null) {
////                            fetcher.enoughFeedsRetrieved(finishedFeeds);
////                            mHasPassedResults = true;
////                        }
//                    }
//                }
//                Thread.sleep(500);
//            } catch (InterruptedException ie) {
//                Log.e(TAG, "Interrupted Exception " + ie);
//            } catch (ExecutionException ee) {
//                Log.e(TAG, "Execution Exception " + ee);
//            }
//
//        }
//
//    }
//
//    public void processFeed(Future<RSSFeed> feed) {
//        Log.i("ASyncFeedLoader", "Feed Retrieved: " + feed.isDone());
//    }
//}
