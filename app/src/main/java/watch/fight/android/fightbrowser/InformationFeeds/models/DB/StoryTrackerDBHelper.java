package watch.fight.android.fightbrowser.InformationFeeds.models.DB;

import android.content.Context;

import watch.fight.android.fightbrowser.Utils.Database.BaseDB;

/**
 * Created by josh on 10/8/15.
 */
public final class StoryTrackerDBHelper extends BaseDB {
    public static final String SQL_CREATE_STORYTRACKER_TABLE =
            "CREATE TABLE " + StoryTrackerDBSchema.StoryTrackerTable.NAME + " (" +
                    StoryTrackerDBSchema.StoryTrackerTable.Cols._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    StoryTrackerDBSchema.StoryTrackerTable.Cols.URL + TEXT_TYPE + "," +
                    StoryTrackerDBSchema.StoryTrackerTable.Cols.DATE_ADDED + TEXT_TYPE +
                    " )";

    public static final String SQL_DROP_STORYTRACKER_TABLE = "DROP TABLE IF EXISTS " + StoryTrackerDBSchema.StoryTrackerTable.NAME;

    public StoryTrackerDBHelper(Context context) {
        super(context);
    }

}
