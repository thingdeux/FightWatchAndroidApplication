package watch.fight.android.fightbrowser.InformationFeeds.models.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import watch.fight.android.fightbrowser.Utils.Database.BaseDB;
import watch.fight.android.fightbrowser.Utils.Database.DBSchema;

/**
 * Created by josh on 9/25/15.
 */
public class FeedDBHelper extends BaseDB {
    public static final String SQL_CREATE_FEED_TABLE =
            "CREATE TABLE " + FeedDBSchema.FeedTable.NAME + " (" +
                    FeedDBSchema.FeedTable.Cols._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    FeedDBSchema.FeedTable.Cols.ID + INTEGER_TYPE + "," +
                    FeedDBSchema.FeedTable.Cols.NAME + TEXT_TYPE + "," +
                    FeedDBSchema.FeedTable.Cols.IMAGE_URL + TEXT_TYPE + "," +
                    FeedDBSchema.FeedTable.Cols.PARENT_URL + TEXT_TYPE + "," +
                    FeedDBSchema.FeedTable.Cols.RSS_URL + TEXT_TYPE + "," +
                    FeedDBSchema.FeedTable.Cols.LAST_UPDATED + TEXT_TYPE +
                    " )";

    public static final String SQL_DROP_FEEDS_TABLE = "DROP TABLE IF EXISTS " + FeedDBSchema.FeedTable.NAME;

    public FeedDBHelper(Context context) {
        super(context);
    }

}
