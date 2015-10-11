package watch.fight.android.fightbrowser.InformationFeeds.models.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import watch.fight.android.fightbrowser.Config.models.DB.GameCursorWrapper;
import watch.fight.android.fightbrowser.Config.models.DB.GameDBSchema;
import watch.fight.android.fightbrowser.Config.models.GameConfig;
import watch.fight.android.fightbrowser.InformationFeeds.models.Feed;

import static watch.fight.android.fightbrowser.InformationFeeds.models.DB.FeedDBSchema.FeedTable;

/**
 * Created by josh on 9/25/15.
 */
public class FeedDB {
    private static FeedDB sFeedDB;
    private SQLiteDatabase mDatabase;
    private Context mContext;

    public static FeedDB getInstance(Context context) {
        if (sFeedDB == null) {
            sFeedDB = new FeedDB(context);
        }
        return sFeedDB;
    }

    private FeedDB(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new FeedDBHelper(mContext).getWritableDatabase();
    }

    public List<Feed> getAllFeeds() {
        List<Feed> feeds = new ArrayList<>();

        // Pass no where clause or args, will return everything.
        FeedCursorWrapper cursor = queryFeeds(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                feeds.add(cursor.getFeed());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return feeds;
    }

    public Feed getFeed(Long id) {
        FeedCursorWrapper cursor = queryFeeds(
                FeedTable.Cols._ID + " = ?",
                new String[]{"" + id}
        );

        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getFeed();
        } finally {
            cursor.close();
        }
    }

    public void addFeed(Feed feed) {
        ContentValues values = getContentValues(feed);
        mDatabase.insert(FeedTable.NAME, null, values);
    }

    public void addFeeds(List<Feed> feeds) {
        if (feeds != null && feeds.size() > 0) {
            mDatabase.beginTransaction();
            for (int i = 0; i < feeds.size(); i++) {
                ContentValues values = getContentValues(feeds.get(i));
                mDatabase.insert(FeedTable.NAME, null, values);
            }
            mDatabase.setTransactionSuccessful();
            mDatabase.endTransaction();
        } else {
            Log.e("addFeeds", "Error - Attemping to add 0 or null feeds to DB");
        }

    }

    public void updateFeed(Feed feed) {
        String id = "" + feed.getId();
        ContentValues values = getContentValues(feed);

        mDatabase.update(FeedTable.NAME, values,
                FeedTable.Cols._ID + " = ?",
                new String[]{id});
    }

    public void deleteFeed(float id) {
        Log.v("DeleteFeed", "Deleting Feed: " + id);
        deleteFeeds(FeedTable.Cols._ID + " = ?", new String[]{"" + id});
    }

    public void deleteAllFeeds() {
        Log.v("DeletedFeed", "Deleting All Feeds!");
        mDatabase.delete(FeedTable.NAME, null, null);
    }

    public void deleteFeeds(String whereClause, String[] whereArgs) {
        int isDeleted = mDatabase.delete(FeedTable.NAME, whereClause, whereArgs);
        if (isDeleted != 1) {
            Log.e("deleteFeeds", "Unable to delete feeds -> " + whereArgs.toString());
        }
    }

    public List<Feed> getAllUnfilteredFeeds() {
        List<Feed> feeds = new ArrayList<>();

        // Pass no where clause or args, will return everything.
        FeedCursorWrapper cursor = queryFeeds(
                FeedDBSchema.FeedTable.Cols.IS_FILTERED + " = ?",
                new String[]{"0"});

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                feeds.add(cursor.getFeed());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return feeds;
    }

    public List<Long> getAllUnfilteredFeedIds() {
        List<Feed> feeds = getAllUnfilteredFeeds();
        List<Long> unFilteredIds = new ArrayList<>();
        for (int i = 0; i < feeds.size(); i++) {
            unFilteredIds.add(feeds.get(i).getId());
        }
        return unFilteredIds;
    }

    public List<String> getAllUnfilteredFeedNames() {
        List<Feed> feeds = getAllUnfilteredFeeds();
        List<String> unFilteredSites = new ArrayList<>();
        for (int i = 0; i < feeds.size(); i++) {
            unFilteredSites.add(feeds.get(i).getName());
        }
        return unFilteredSites;
    }

    public void setFiltered(Long feedid, boolean isChecked) {
        if (feedid != null) {
            Feed feed = getFeed(feedid);
            feed.setIsFiltered(isChecked);

            mDatabase.update(
                FeedDBSchema.FeedTable.NAME,
                getContentValues(feed),
                FeedDBSchema.FeedTable.Cols.ID + " = ?",
                new String[]{feedid.toString()});
        }

    }

    private FeedCursorWrapper queryFeeds(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                FeedTable.NAME,
                null, // Columns -null selects all columns
                whereClause,
                whereArgs,
                null, //GroupBy
                null, // having
                FeedTable.Cols.ORDINAL + " DESC" // orderBy
        );

        return new FeedCursorWrapper(cursor);
    }

    private static ContentValues getContentValues(Feed feed) {
        ContentValues values = new ContentValues();
        values.put(FeedTable.Cols.ID, feed.getId());
        values.put(FeedTable.Cols.NAME, feed.getName());
        values.put(FeedTable.Cols.IMAGE_URL, feed.getFeedImageUrl());
        values.put(FeedTable.Cols.LAST_UPDATED, feed.getLastUpdated());
        values.put(FeedTable.Cols.PARENT_URL, feed.getWebUrl());
        values.put(FeedTable.Cols.RSS_URL, feed.getRSSUrl());
        values.put(FeedTable.Cols.ORDINAL, feed.getOrdinal());
        values.put(FeedTable.Cols.IS_FILTERED, feed.getIsFiltered());

        return values;
    }
}
