package watch.fight.android.fightbrowser.InformationFeeds.models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static watch.fight.android.fightbrowser.InformationFeeds.models.FeedDBSchema.*;

/**
 * Created by josh on 9/25/15.
 */
public class FeedDB extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = ".db";
    private static final String TEXT_TYPE = " TEXT";

    private static final String SQL_CREATE_FEED_TABLE =
            "CREATE TABLE " + FeedTable.NAME + " (" +
                    FeedTable.Cols._ID + " INTEGER PRIMARY KEY," +
                    FeedTable.Cols.ID + TEXT_TYPE + "," +
                    FeedTable.Cols.NAME + TEXT_TYPE + "," +
                    FeedTable.Cols.IMAGE_URL + TEXT_TYPE + "," +
                    FeedTable.Cols.PARENT_URL + TEXT_TYPE + "," +
                    FeedTable.Cols.RSS_URL + TEXT_TYPE + "," +
                    FeedTable.Cols.LAST_UPDATED + TEXT_TYPE +
                    " )";

    private static final String SQL_DROP_FEEDS_TABLE = "DROP TABLE IF EXISTS " + FeedTable.NAME;


    public FeedDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_FEED_TABLE);
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
