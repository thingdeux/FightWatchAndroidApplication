package watch.fight.android.fightbrowser.Events.models.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import watch.fight.android.fightbrowser.Events.models.DB.EventDBSchema.EventTable;
import watch.fight.android.fightbrowser.Utils.Database.BaseDB;
import watch.fight.android.fightbrowser.Utils.Database.DBSchema;

/**
 * Created by josh on 9/28/15.
 */
public class EventDBHelper extends BaseDB {
    public static final String SQL_INDEX_EVENT_ID = "CREATE INDEX " + EventTable.Cols.ID +
            "_index " + "ON " + EventTable.NAME + "(" + EventTable.Cols.ID + ")";

    public static final String SQL_CREATE_FEED_TABLE =
            "CREATE TABLE " + EventTable.NAME + " (" +
                    EventTable.Cols._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    EventTable.Cols.ID + " INTEGER UNIQUE NOT NULL" + "," +
                    EventTable.Cols.NAME + TEXT_TYPE + "," +
                    EventTable.Cols.WEBSITE + TEXT_TYPE + "," +
                    EventTable.Cols.FLAVOR_TEXT + TEXT_TYPE + "," +
                    EventTable.Cols.HEADER_IMAGE_URL + TEXT_TYPE + "," +
                    EventTable.Cols.START_DATE + INTEGER_TYPE + "," +
                    EventTable.Cols.END_DATE + INTEGER_TYPE  + "," +
                    EventTable.Cols.BRACKETSUPDATED + INTEGER_TYPE +
                    " )";

    public static final String SQL_DROP_EVENTS_TABLE = "DROP TABLE IF EXISTS " + EventTable.NAME;

    @Override
    public void onConfigure(SQLiteDatabase db){
        db.setForeignKeyConstraintsEnabled(true);
    }

    public EventDBHelper(Context context) {
        super(context);
    }

}
