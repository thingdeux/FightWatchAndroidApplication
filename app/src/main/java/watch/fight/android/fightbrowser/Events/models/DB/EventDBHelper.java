package watch.fight.android.fightbrowser.Events.models.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import watch.fight.android.fightbrowser.Utils.DBSchema;

/**
 * Created by josh on 9/28/15.
 */
public class EventDBHelper extends SQLiteOpenHelper{
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = DBSchema.DBName;
    private static final String TEXT_TYPE = " TEXT";
    public static final String SQL_INDEX_EVENT_ID = "CREATE INDEX " + EventDBSchema.EventTable.Cols.ID +
            "_index " + "ON " + EventDBSchema.EventTable.NAME + "(" + EventDBSchema.EventTable.Cols.ID + ")";

    public static final String SQL_CREATE_FEED_TABLE =
            "CREATE TABLE " + EventDBSchema.EventTable.NAME + " (" +
                    EventDBSchema.EventTable.Cols._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    EventDBSchema.EventTable.Cols.ID + " INTEGER" + "," +
                    EventDBSchema.EventTable.Cols.NAME + TEXT_TYPE + "," +
                    EventDBSchema.EventTable.Cols.WEBSITE + TEXT_TYPE + "," +
                    EventDBSchema.EventTable.Cols.FLAVOR_TEXT + TEXT_TYPE + "," +
                    EventDBSchema.EventTable.Cols.HEADER_IMAGE_URL + TEXT_TYPE + "," +
                    EventDBSchema.EventTable.Cols.START_DATE + " INTEGER"  + "," +
                    EventDBSchema.EventTable.Cols.END_DATE + " INTEGER"  +
                    " )";

    public static final String SQL_DROP_EVENTS_TABLE = "DROP TABLE IF EXISTS " + EventDBSchema.EventTable.NAME;

    @Override
    public void onConfigure(SQLiteDatabase db){
        db.setForeignKeyConstraintsEnabled(true);
    }

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
