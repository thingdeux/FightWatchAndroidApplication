package watch.fight.android.fightbrowser.InformationFeeds.models.DB;

import android.content.Context;

import watch.fight.android.fightbrowser.Utils.Database.BaseDB;

import static watch.fight.android.fightbrowser.InformationFeeds.models.DB.FeedDBSchema.FeedTable;

/**
 * Created by josh on 9/25/15.
 */
public class FeedDBHelper extends BaseDB {
    public static final String SQL_CREATE_FEED_TABLE =
            "CREATE TABLE " + FeedTable.NAME + " (" +
                    FeedTable.Cols._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    FeedTable.Cols.ID + INTEGER_TYPE + "," +
                    FeedTable.Cols.NAME + TEXT_TYPE + "," +
                    FeedTable.Cols.IMAGE_URL + TEXT_TYPE + "," +
                    FeedTable.Cols.PARENT_URL + TEXT_TYPE + "," +
                    FeedTable.Cols.RSS_URL + TEXT_TYPE + "," +
                    FeedTable.Cols.ORDINAL + INTEGER_TYPE + "," +
                    FeedTable.Cols.LAST_UPDATED + TEXT_TYPE + "," +
                    FeedTable.Cols.IS_FILTERED + INTEGER_TYPE +
                    " )";

    public static final String SQL_DROP_FEEDS_TABLE = "DROP TABLE IF EXISTS " + FeedTable.NAME;

    public FeedDBHelper(Context context) {
        super(context);
    }

}
