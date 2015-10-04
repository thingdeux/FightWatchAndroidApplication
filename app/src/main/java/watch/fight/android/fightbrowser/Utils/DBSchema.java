package watch.fight.android.fightbrowser.Utils;

import android.database.sqlite.SQLiteDatabase;

import watch.fight.android.fightbrowser.Config.models.DB.GameDBHelper;
import watch.fight.android.fightbrowser.Events.models.DB.BracketDBHelper;
import watch.fight.android.fightbrowser.Events.models.DB.EventDBHelper;
import watch.fight.android.fightbrowser.InformationFeeds.models.DB.FeedDBHelper;
import watch.fight.android.fightbrowser.InformationFeeds.models.DB.StoryDBHelper;

/**
 * Created by josh on 10/1/15.
 */
public class DBSchema {
    public static String DBName = "fgc.db";

    public static void createAllTables(SQLiteDatabase db) {
        // Create initial tables - Make sure to create tables that require FK last
        db.execSQL(EventDBHelper.SQL_CREATE_FEED_TABLE);
        db.execSQL(EventDBHelper.SQL_INDEX_EVENT_ID);
        db.execSQL(FeedDBHelper.SQL_CREATE_FEED_TABLE);
        db.execSQL(StoryDBHelper.SQL_CREATE_FEED_TABLE);
        db.execSQL(StoryDBHelper.SQL_INDEX_SITE_NAME);
        db.execSQL(GameDBHelper.SQL_CREATE_FEED_TABLE);
        db.execSQL(GameDBHelper.SQL_INDEX_GAME_ID);

        // Tables requiring Foreign Keys
        db.execSQL(BracketDBHelper.SQL_CREATE_BRACKET_TABLE);
        db.execSQL(BracketDBHelper.SQL_INDEX_FK_EVENT_ID);
    }

    public void onCreate(SQLiteDatabase db) {
        createAllTables(db);
    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // For now blow the table out and recreate on upgrade
        // TODO : Add drop table for each DB
        db.execSQL(EventDBHelper.SQL_DROP_EVENTS_TABLE);
        db.execSQL(GameDBHelper.SQL_DROP_GAMES_TABLE);
        db.execSQL(BracketDBHelper.SQL_DROP_EVENTS_TABLE);
        db.execSQL(FeedDBHelper.SQL_DROP_FEEDS_TABLE);
        db.execSQL(StoryDBHelper.SQL_DROP_FEEDS_TABLE);
        createAllTables(db);
    }

    public static void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Not Implemented - Intentionally blank
    }
}
