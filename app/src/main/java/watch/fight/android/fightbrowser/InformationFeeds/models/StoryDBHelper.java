package watch.fight.android.fightbrowser.InformationFeeds.models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static watch.fight.android.fightbrowser.InformationFeeds.models.StoryDBSchema.*;

/**
 * Created by josh on 9/26/15.
 */
public class StoryDBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = StoryTable.NAME + ".db";
    private static final String TEXT_TYPE = " TEXT";
    private static final String SQL_INDEX_SITE_NAME = "CREATE INDEX " + StoryTable.Cols.SITE_NAME +
            "_index " + "ON " + StoryTable.NAME + "(" + StoryTable.Cols.SITE_NAME + ")";

    private static final String SQL_CREATE_FEED_TABLE =
            "CREATE TABLE " + StoryTable.NAME + " (" +
                    StoryTable.Cols._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    StoryTable.Cols.SITE_NAME + TEXT_TYPE + "," +
                    StoryTable.Cols.TITLE + TEXT_TYPE + "," +
                    StoryTable.Cols.DESCRIPTION + TEXT_TYPE + "," +
                    StoryTable.Cols.URL + TEXT_TYPE + "," +
                    StoryTable.Cols.AUTHOR + TEXT_TYPE + "," +
                    StoryTable.Cols.PUBLISHED_DATE + " INTEGER" + "," +
                    StoryTable.Cols.LAST_UPDATED + TEXT_TYPE + "," +
                    StoryTable.Cols.THUMBNAIL + TEXT_TYPE +
                    " )";

    private static final String SQL_DROP_FEEDS_TABLE = "DROP TABLE IF EXISTS " + StoryTable.NAME;


    public StoryDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_FEED_TABLE);
        db.execSQL(SQL_INDEX_SITE_NAME);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // For now blow the table out and recreate on upgrade
        db.execSQL(SQL_DROP_FEEDS_TABLE);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Not Implemented - Intentionally blank
    }
}
