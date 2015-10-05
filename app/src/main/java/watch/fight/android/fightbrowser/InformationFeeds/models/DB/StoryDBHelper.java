package watch.fight.android.fightbrowser.InformationFeeds.models.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import watch.fight.android.fightbrowser.Utils.Database.BaseDB;
import watch.fight.android.fightbrowser.Utils.Database.DBSchema;

/**
 * Created by josh on 9/26/15.
 */
public class StoryDBHelper extends BaseDB {
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
                    StoryDBSchema.StoryTable.Cols.PUBLISHED_DATE + INTEGER_TYPE + "," +
                    StoryDBSchema.StoryTable.Cols.LAST_UPDATED + TEXT_TYPE + "," +
                    StoryDBSchema.StoryTable.Cols.THUMBNAIL + TEXT_TYPE +
                    " )";

    public static final String SQL_DROP_FEEDS_TABLE = "DROP TABLE IF EXISTS " + StoryDBSchema.StoryTable.NAME;

    public StoryDBHelper(Context context) {
        super(context);
    }

}
