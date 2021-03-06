package watch.fight.android.fightbrowser.Utils.Network;

import android.util.Log;

import org.mcsoxford.rss.RSSFeed;
import org.mcsoxford.rss.RSSItem;
import org.mcsoxford.rss.RSSReader;
import org.mcsoxford.rss.RSSReaderException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import watch.fight.android.fightbrowser.InformationFeeds.models.Feed;
import watch.fight.android.fightbrowser.InformationFeeds.models.Story;

/**
 * Created by josh on 9/21/15.
 */
public class ParseUtils {
    private static String TAG = ParseUtils.class.getSimpleName();

    public static String InputStreamToString(InputStream stream) {
        try {
            BufferedReader r = new BufferedReader(new InputStreamReader(stream));
            StringBuilder total = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                total.append(line);
            }
            return total.toString();
        } catch (IOException ioe) {
            Log.e(TAG, "Unable to convert inputStreamToString");
        }
        return null;
    }

    public static ArrayList<Story> parseRss(Feed site, String rssUrl) {
        try {
            RSSReader reader = new RSSReader();
            RSSFeed rss_feed = reader.load(rssUrl);
            List<RSSItem> rss_items = rss_feed.getItems();
            ArrayList<Story> feeds = new ArrayList<>();

            if (rss_items != null) {
                for (int i = 0; i < rss_items.size(); i++) {
                    RSSItem item = rss_items.get(i);

                    Story s = new Story();
                    s.setSiteName(site.getName());
                    s.setTitle(item.getTitle());
                    s.setDescription(item.getDescription());
                    s.setUrl(item.getLink());
                    s.setPublishedDate(item.getPubDate());
                    if (item.getThumbnails() != null && item.getThumbnails().size() > 0) {
                        s.setThumbnail(item.getThumbnails().get(0).toString());
                    } else {
                        s.setThumbnail(site.getFeedImageUrl());
                    }
                    // TODO : Add get author
                    feeds.add(s);
                }
            }
            return feeds;

        } catch (RSSReaderException e) {
            Log.e(TAG, "Unable to read feed: " + rssUrl + " error: " + e);
        }
        return null;
    }

    public static ArrayList<Story> parseRSS(RSSFeed feed, Feed parentFeed) {
        List<RSSItem> rss_items = feed.getItems();
        ArrayList<Story> feeds = new ArrayList<>();

        if (rss_items != null) {
            for (int i = 0; i < rss_items.size(); i++) {
                RSSItem item = rss_items.get(i);

                Story s = new Story();
                s.setSiteName(parentFeed.getName());
                s.setTitle(item.getTitle());
                s.setDescription(item.getDescription());
                s.setUrl(item.getLink());
                s.setPublishedDate(item.getPubDate());
                if (item.getThumbnails() != null && item.getThumbnails().size() > 0) {
                    s.setThumbnail(item.getThumbnails().get(0).toString());
                } else {
                    s.setThumbnail(parentFeed.getFeedImageUrl());
                }
                feeds.add(s);
            }
        }
        return feeds;
    }

//    public static ArrayList<Story> getStoriesFromFeed(List<RSSItem> rss_items) {
//        ArrayList<Story> feeds = new ArrayList<>();
//        if (rss_items != null) {
//            for (int i = 0; i < rss_items.size(); i++) {
//                Story s = new Story();
////                s.setSiteName(siteName);
//                s.setTitle(rss_items.get(i).getTitle());
//                s.setDescription(rss_items.get(i).getDescription());
//                s.setUrl(rss_items.get(i).getLink());
//                s.setPublishedDate(rss_items.get(i).getPubDate());
//                // TODO : Add get author
//                feeds.add(s);
//            }
//        }
//        return feeds;
//    }
}
