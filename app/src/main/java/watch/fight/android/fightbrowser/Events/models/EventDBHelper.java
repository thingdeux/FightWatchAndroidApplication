package watch.fight.android.fightbrowser.Events.models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import watch.fight.android.fightbrowser.Utils.DBSchema;

import static watch.fight.android.fightbrowser.Events.models.EventDBSchema.*;

/**
 * Created by josh on 9/28/15.
 */
public class EventDBHelper extends SQLiteOpenHelper{
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = DBSchema.DBName;
    private static final String TEXT_TYPE = " TEXT";
    public static final String SQL_INDEX_EVENT_ID = "CREATE INDEX " + EventTable.Cols.ID +
            "_index " + "ON " + EventTable.NAME + "(" + EventTable.Cols.ID + ")";

    public static final String SQL_CREATE_FEED_TABLE =
            "CREATE TABLE " + EventTable.NAME + " (" +
                    EventTable.Cols._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    EventTable.Cols.ID + " INTEGER" + "," +
                    EventTable.Cols.NAME + TEXT_TYPE + "," +
                    EventTable.Cols.WEBSITE + TEXT_TYPE + "," +
                    EventTable.Cols.FLAVOR_TEXT + TEXT_TYPE + "," +
                    EventTable.Cols.HEADER_IMAGE_URL + TEXT_TYPE + "," +
                    EventTable.Cols.START_DATE + " INTEGER"  + "," +
                    EventTable.Cols.END_DATE + " INTEGER"  +
                    " )";

    public static final String SQL_DROP_EVENTS_TABLE = "DROP TABLE IF EXISTS " + EventTable.NAME;


    public EventDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        DBSchema.createAllTables(db);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // For now blow the table out and recreate on upgrade
        db.execSQL(SQL_DROP_EVENTS_TABLE);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Not Implemented - Intentionally blank
    }

}
