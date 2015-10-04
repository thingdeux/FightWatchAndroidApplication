package watch.fight.android.fightbrowser.InformationFeeds.models.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import watch.fight.android.fightbrowser.Utils.DBSchema;

/**
 * Created by josh on 9/26/15.
 */
public class StoryDBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = DBSchema.DBName;
    private static final String TEXT_TYPE = " TEXT";
    public static final String SQL_INDEX_SITE_NAME = "CREATE INDEX " + StoryDBSchema.StoryTable.Cols.SITE_NAME +
            "_index " + "ON " + StoryDBSchema.StoryTable.NAME + "(" + StoryDBSchema.StoryTable.Cols.SITE_NAME + ")";

    public static final String SQL_CREATE_FEED_TABLE =
            "CREATE TABLE " + StoryDBSchema.StoryTable.NAME + " (" +
                    StoryDBSchema.StoryTable.Cols._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    StoryDBSchema.StoryTable.Cols.SITE_NAME + TEXT_TYPE + "," +
                    StoryDBSchema.StoryTable.Cols.TITLE + TEXT_TYPE + "," +
                    StoryDBSchema.StoryTable.Cols.DESCRIPTION + TEXT_TYPE + "," +
                    StoryDBSchema.StoryTable.Cols.URL + TEXT_TYPE + "," +
                    StoryDBSchema.StoryTable.Cols.AUTHOR + TEXT_TYPE + "," +
                    StoryDBSchema.StoryTable.Cols.PUBLISHED_DATE + " INTEGER" + "," +
                    StoryDBSchema.StoryTable.Cols.LAST_UPDATED + TEXT_TYPE + "," +
                    StoryDBSchema.StoryTable.Cols.THUMBNAIL + TEXT_TYPE +
                    " )";

    public static final String SQL_DROP_FEEDS_TABLE = "DROP TABLE IF EXISTS " + StoryDBSchema.StoryTable.NAME;


    public StoryDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        DBSchema.createAllTables(db);
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
