package watch.fight.android.fightbrowser.Events.models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import watch.fight.android.fightbrowser.InformationFeeds.models.StoryDBHelper;

/**
 * Created by josh on 9/28/15.
 */
public class EventDB {
    private static EventDB sEventDB;
    private SQLiteDatabase mDatabase;
    private Context mContext;

    public static EventDB getInstance(Context context) {
        if (sEventDB == null) {
            sEventDB = new EventDB(context);
        }
        return sEventDB;
    }

    private EventDB(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new StoryDBHelper(mContext).getWritableDatabase();
    }


    // TODO : FINISH SETTING UP QUERIES
}
