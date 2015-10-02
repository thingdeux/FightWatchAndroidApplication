package watch.fight.android.fightbrowser.InformationFeeds.models.DB;

import android.database.Cursor;
import android.database.CursorWrapper;

import watch.fight.android.fightbrowser.InformationFeeds.models.Feed;

/**
 * Created by josh on 9/25/15.
 */
public class FeedCursorWrapper extends CursorWrapper {
    public FeedCursorWrapper(Cursor cursor) { super(cursor); }

    public Feed getFeed() {
        int id = getInt(getColumnIndex(FeedDBSchema.FeedTable.Cols.ID));
        String name = getString(getColumnIndex(FeedDBSchema.FeedTable.Cols.NAME));
        String imageUrl = getString(getColumnIndex(FeedDBSchema.FeedTable.Cols.IMAGE_URL));
        String lastUpdated = getString(getColumnIndex(FeedDBSchema.FeedTable.Cols.LAST_UPDATED));
        String parentUrl = getString(getColumnIndex(FeedDBSchema.FeedTable.Cols.PARENT_URL));
        String rssUrl = getString(getColumnIndex(FeedDBSchema.FeedTable.Cols.RSS_URL));

        Feed feed = new Feed();
        feed.setId(id);
        feed.setFeedImageUrl(imageUrl);
        feed.setName(name);
        feed.setLastUpdated(lastUpdated);
        feed.setWebUrl(parentUrl);
        feed.setRSSUrl(rssUrl);

        return feed;
    }
}
