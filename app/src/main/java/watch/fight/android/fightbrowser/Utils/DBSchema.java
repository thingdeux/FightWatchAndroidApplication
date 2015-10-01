package watch.fight.android.fightbrowser.Utils;

import android.database.sqlite.SQLiteDatabase;

import watch.fight.android.fightbrowser.Events.models.EventDBHelper;
import watch.fight.android.fightbrowser.InformationFeeds.models.FeedDBHelper;
import watch.fight.android.fightbrowser.InformationFeeds.models.StoryDBHelper;

/**
 * Created by josh on 10/1/15.
 */
public class DBSchema {
    public static String DBName = "fgc.db";

    public static void createAllTables(SQLiteDatabase db) {
        // Create initial tables
        db.execSQL(FeedDBHelper.SQL_CREATE_FEED_TABLE);
        db.execSQL(EventDBHelper.SQL_CREATE_FEED_TABLE);
        db.execSQL(EventDBHelper.SQL_INDEX_EVENT_ID);
        db.execSQL(StoryDBHelper.SQL_CREATE_FEED_TABLE);
        db.execSQL(StoryDBHelper.SQL_INDEX_SITE_NAME);
    }
}
