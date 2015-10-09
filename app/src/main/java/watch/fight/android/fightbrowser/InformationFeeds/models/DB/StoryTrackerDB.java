package watch.fight.android.fightbrowser.InformationFeeds.models.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;

import watch.fight.android.fightbrowser.InformationFeeds.models.Story;
import watch.fight.android.fightbrowser.Utils.DateParser;

/**
 * Created by josh on 10/8/15.
 */
public class StoryTrackerDB {
    public static int FEED_TRACKER_MAX_AGE = 24*5; // 5 Days (in hours)
    private static StoryTrackerDB sStoryTrackerDB;
    private SQLiteDatabase mDatabase;
    private Context mContext;

    public static StoryTrackerDB getInstance(Context context) {
        if (sStoryTrackerDB == null) {
            sStoryTrackerDB = new StoryTrackerDB(context);
        }
        return sStoryTrackerDB;
    }

    private StoryTrackerDB(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new StoryTrackerDBHelper(mContext).getWritableDatabase();
    }

    public StoryTracker getStoryTracker(int id) {
        StoryTrackerCursorWrapper cursor = queryStoryTrackers(
                StoryTrackerDBSchema.StoryTrackerTable.Cols._ID + " = ?",
                new String[]{"" + id}
        );

        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getStoryTracker();
        } finally {
            cursor.close();
        }
    }

    public HashSet<String> getAllTrackers() {
        HashSet<String> stories = new HashSet<>();

        // Pass no where clause or args, will return everything.
        StoryTrackerCursorWrapper cursor = queryStoriesWithOrder(null, null, "DESC");

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                StoryTracker st = cursor.getStoryTracker();
                stories.add(st.getUrl().toString());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return stories;
    }

    public List<StoryTracker> getAllTrackersAsObjs() {
        List<StoryTracker> stories = new ArrayList<>();

        // Pass no where clause or args, will return everything.
        StoryTrackerCursorWrapper cursor = queryStoriesWithOrder(null, null, "DESC");

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                stories.add(cursor.getStoryTracker());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return stories;
    }

    public void addStoryTracker(StoryTracker story) {
        ContentValues values = getContentValues(story);
        mDatabase.insert(StoryTrackerDBSchema.StoryTrackerTable.NAME, null, values);
    }

    public void pruneOldTrackers() {
        // Gather anything older than ... say .... 5 days and delete it.
        GregorianCalendar today = new GregorianCalendar();
        today.add(Calendar.HOUR, -FEED_TRACKER_MAX_AGE);

        deleteStories(
                StoryTrackerDBSchema.StoryTrackerTable.Cols.DATE_ADDED + " <= ?",
                new String[] {DateParser.dateToEpoch(today).toString()}

        );

    }

    public void addStoryTrackers(List<Story> stories) {
        if (stories != null) {
            mDatabase.beginTransaction();
            StoryTracker tracker = new StoryTracker();
            for (int i = 0; i < stories.size(); i++) {
                if (stories.get(i) != null && stories.get(i).getUrl() != null) {
                    tracker.setUrl(stories.get(i).getUrl().toString());
                    tracker.setDateAdded(System.currentTimeMillis());
                    ContentValues values = getContentValues(tracker);
                    mDatabase.insert(StoryTrackerDBSchema.StoryTrackerTable.NAME, null, values);
                }
            }
            mDatabase.setTransactionSuccessful();
            mDatabase.endTransaction();
        } else {
            Log.e("addStoryTrackers", "Error attempting to add 0 or null stories to DB");
        }
    }
    public void deleteStoryTracker(int id) {
        Log.v("DeleteStoryTracker", "Deleting StoryTracker: " + id);
        deleteStories(StoryTrackerDBSchema.StoryTrackerTable.Cols._ID + " = ?", new String[]{"" + id});
    }

    public void deleteAllStoryTrackers() {
        Log.v("deleteStories", "Deleting All Stories!");
        mDatabase.delete(StoryTrackerDBSchema.StoryTrackerTable.NAME, null, null);
    }

    private void deleteStories(String whereClause, String[] whereArgs) {
        int isDeleted = mDatabase.delete(StoryTrackerDBSchema.StoryTrackerTable.NAME, whereClause, whereArgs);
        if (whereClause == null && isDeleted != 1) {
            Log.e("deleteStories", "Unable to delete stories -> " + whereArgs);
        } else {
            Log.i("deleteStories", "Deleted: " + isDeleted + " stories");
        }
    }

    private StoryTrackerCursorWrapper queryStoryTrackers(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                StoryTrackerDBSchema.StoryTrackerTable.NAME,
                null, // Columns -null selects all columns
                whereClause,
                whereArgs,
                null, //GroupBy
                null, // having
                null // orderBy
        );

        return new StoryTrackerCursorWrapper(cursor);
    }

    private StoryTrackerCursorWrapper queryStoriesWithOrder(String whereClause, String[] whereArgs, String DescOrASC) {
        Cursor cursor = mDatabase.query(
                StoryTrackerDBSchema.StoryTrackerTable.NAME,
                null, // Columns -null selects all columns
                whereClause,
                whereArgs,
                null, //GroupBy
                null, // having
                StoryTrackerDBSchema.StoryTrackerTable.Cols._ID + " " + DescOrASC // orderBy
        );

        return new StoryTrackerCursorWrapper(cursor);
    }

    private static ContentValues getContentValues(StoryTracker story) {
        ContentValues values = new ContentValues();
        values.put(StoryTrackerDBSchema.StoryTrackerTable.Cols.URL, story.getUrl());
        values.put(StoryTrackerDBSchema.StoryTrackerTable.Cols.DATE_ADDED, DateParser.dateToEpoch(story.getDateAdded()));

        return values;
    }
}
