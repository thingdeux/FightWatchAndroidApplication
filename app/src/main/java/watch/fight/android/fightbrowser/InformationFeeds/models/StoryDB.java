package watch.fight.android.fightbrowser.InformationFeeds.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import watch.fight.android.fightbrowser.Utils.DateParser;

import static watch.fight.android.fightbrowser.InformationFeeds.models.StoryDBSchema.*;

/**
 * Created by josh on 9/26/15.
 */
public class StoryDB {
    private static StoryDB sStoryDB;
    private SQLiteDatabase mDatabase;
    private Context mContext;

    public static StoryDB getInstance(Context context) {
        if (sStoryDB == null) {
            sStoryDB = new StoryDB(context);
        }
        return sStoryDB;
    }

    private StoryDB(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new StoryDBHelper(mContext).getWritableDatabase();
    }

    public Story getStory(int id) {
        StoryCursorWrapper cursor = queryStories(
                StoryTable.Cols._ID + " = ?",
                new String[]{"" + id}
        );

        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getStory();
        } finally {
            cursor.close();
        }
    }

    public List<Story> getAllStories() {
        List<Story> stories = new ArrayList<>();

        // Pass no where clause or args, will return everything.
        StoryCursorWrapper cursor = queryStoriesWithOrder(null, null, "DESC");

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                stories.add(cursor.getStory());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return stories;
    }

    public List<Story> getTopStoryForEachSite() {
        List<Story> stories = new ArrayList<>();
        StoryCursorWrapper cursor = TopStoriesQuery();

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                stories.add(cursor.getStory());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return stories;
    }

    public void addStory(Story story) {
        ContentValues values = getContentValues(story);
        mDatabase.insert(StoryTable.NAME, null, values);
    }

    public void addStories(List<Story> stories) {
        if (stories != null && stories.size() > 0) {
            mDatabase.beginTransaction();
            for (int i = 0; i < stories.size(); i++) {
                ContentValues values = getContentValues(stories.get(i));
                mDatabase.insert(StoryTable.NAME, null, values);
            }
            mDatabase.setTransactionSuccessful();
            mDatabase.endTransaction();
        } else {
            Log.e("addStories", "Error - Attemping to add 0 or null stories to DB");
        }

    }

    public void deleteStory(float id) {
        Log.v("DeleteStory", "Deleting Story: " + id);
        deleteStories(StoryTable.Cols._ID + " = ?", new String[]{"" + id});
    }

    public void deleteAllStories() {
        Log.v("deleteStories", "Deleting All Stories!");
        mDatabase.delete(StoryTable.NAME, null, null);
    }

    public void deleteStoriesBySiteName(String siteName) {
        if (siteName != null && !siteName.isEmpty()) {
            deleteStories(StoryTable.Cols.SITE_NAME + " = ?",
                    new String[]{siteName});
        }
    }

    private void deleteStories(String whereClause, String[] whereArgs) {
        int isDeleted = mDatabase.delete(StoryTable.NAME, whereClause, whereArgs);
        if (whereClause == null && isDeleted != 1) {
            Log.e("deleteStories", "Unable to delete stories -> " + whereArgs);
        } else {
            Log.i("deleteStories", "Deleted: " + isDeleted + " stories");
        }
    }

    private StoryCursorWrapper queryStories(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                StoryTable.NAME,
                null, // Columns -null selects all columns
                whereClause,
                whereArgs,
                null, //GroupBy
                null, // having
                null // orderBy
        );

        return new StoryCursorWrapper(cursor);
    }

    private StoryCursorWrapper queryStoriesWithOrder(String whereClause, String[] whereArgs, String DescOrASC) {
        Cursor cursor = mDatabase.query(
                StoryTable.NAME,
                null, // Columns -null selects all columns
                whereClause,
                whereArgs,
                null, //GroupBy
                null, // having
                StoryTable.Cols.PUBLISHED_DATE + " " + DescOrASC // orderBy
        );

        return new StoryCursorWrapper(cursor);
    }


    private StoryCursorWrapper queryStoriesBySite(String siteName) {
        Cursor cursor = mDatabase.query(
                StoryTable.NAME,
                null, // Columns - null selects all columns
                StoryTable.Cols.SITE_NAME + " = ?",
                new String[] {siteName},
                null, //GroupBy
                null, // having
                null // orderBy
        );

        return new StoryCursorWrapper(cursor);
    }

    private StoryCursorWrapper queryStoriesWithGroupAndOrder(String whereClause, String[] whereArgs,
                                                             String groupBy, String orderBy) {
        Cursor cursor = mDatabase.query(
                StoryTable.NAME,
                null, // Columns -null selects all columns
                whereClause,
                whereArgs,
                groupBy, //GroupBy
                null, // having
                orderBy // orderBy
        );

        return new StoryCursorWrapper(cursor);
    }

    private static ContentValues getContentValues(Story story) {
        ContentValues values = new ContentValues();
        values.put(StoryTable.Cols.SITE_NAME, story.getSiteName());
        values.put(StoryTable.Cols.TITLE, story.getTitle());
        values.put(StoryTable.Cols.DESCRIPTION, story.getDescription());
        values.put(StoryTable.Cols.URL, story.getUrl().toString());
        values.put(StoryTable.Cols.AUTHOR, story.getAuthor());
        values.put(StoryTable.Cols.PUBLISHED_DATE, DateParser.dateToEpoch(story.getPublishedDate()));
        values.put(StoryTable.Cols.LAST_UPDATED, story.getLastUpdated());

        return values;
    }


//    private static String LATEST_STORY_INNER = "SELECT * FROM " + StoryTable.NAME +
//            " ORDER BY " + StoryTable.Cols.PUBLISHED_DATE + " DESC";
//    private static String LATEST_STORY_QUERY = "SELECT * FROM (" + LATEST_STORY_INNER + ") as temp GROUP BY " + StoryTable.Cols.SITE_NAME;

    private StoryCursorWrapper TopStoriesQuery() {
        Cursor cursor = mDatabase.rawQuery("SELECT *, MAX(" + StoryTable.Cols.PUBLISHED_DATE + ") " + "FROM stories GROUP BY storysitename", null);
        return new StoryCursorWrapper(cursor);
    }
}

