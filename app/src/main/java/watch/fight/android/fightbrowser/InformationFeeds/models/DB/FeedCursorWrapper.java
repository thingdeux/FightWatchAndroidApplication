package watch.fight.android.fightbrowser.InformationFeeds.models.DB;

import android.database.Cursor;
import android.database.CursorWrapper;

import watch.fight.android.fightbrowser.InformationFeeds.models.Feed;

import static watch.fight.android.fightbrowser.InformationFeeds.models.DB.FeedDBSchema.FeedTable;

/**
 * Created by josh on 9/25/15.
 */
public class FeedCursorWrapper extends CursorWrapper {
    public FeedCursorWrapper(Cursor cursor) { super(cursor); }

    public Feed getFeed() {
        int id = getInt(getColumnIndex(FeedTable.Cols.ID));
        int ordinal = getInt(getColumnIndex(FeedTable.Cols.ORDINAL));
        String name = getString(getColumnIndex(FeedTable.Cols.NAME));
        String imageUrl = getString(getColumnIndex(FeedTable.Cols.IMAGE_URL));
        String lastUpdated = getString(getColumnIndex(FeedTable.Cols.LAST_UPDATED));
        String parentUrl = getString(getColumnIndex(FeedTable.Cols.PARENT_URL));
        String rssUrl = getString(getColumnIndex(FeedTable.Cols.RSS_URL));
        int isFiltered = getInt(getColumnIndex(FeedTable.Cols.IS_FILTERED));

        Feed feed = new Feed();
        feed.setId(id);
        feed.setFeedImageUrl(imageUrl);
        feed.setName(name);
        feed.setLastUpdated(lastUpdated);
        feed.setWebUrl(parentUrl);
        feed.setRSSUrl(rssUrl);
        feed.setOrdinal(ordinal);
        feed.setIsFiltered(isFiltered > 0);

        return feed;
    }
}
