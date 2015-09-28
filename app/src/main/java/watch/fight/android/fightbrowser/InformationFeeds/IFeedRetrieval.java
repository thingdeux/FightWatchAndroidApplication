package watch.fight.android.fightbrowser.InformationFeeds;

import org.mcsoxford.rss.RSSFeed;

import java.util.List;

/**
 * Created by josh on 9/28/15.
 */
public interface IFeedRetrieval {
    public void enoughFeedsRetrieved(List<RSSFeed> feeds);
}
