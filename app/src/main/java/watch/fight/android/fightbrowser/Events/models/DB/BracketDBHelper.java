package watch.fight.android.fightbrowser.Events.models.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import watch.fight.android.fightbrowser.Utils.DBSchema;

import static watch.fight.android.fightbrowser.Events.models.DB.BracketDBSchema.*;

/**
 * Created by josh on 10/4/15.
 */
public class BracketDBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = DBSchema.DBName;
    private static final String TEXT_TYPE = " TEXT";
    public static final String SQL_INDEX_FK_EVENT_ID = "CREATE INDEX " + BracketTable.Cols.FK_EVENT_ID +
            "_index " + "ON " + BracketTable.NAME + "(" + BracketTable.Cols.FK_EVENT_ID + ")";

    public static final String SQL_SET_EVENT_FK_CONSTRAINT = " FOREIGN KEY(" + BracketTable.Cols.FK_EVENT_ID + ")" +
            " REFERENCES " + EventDBSchema.EventTable.NAME + "(" + EventDBSchema.EventTable.Cols.ID + ")";
//            "ON DELETE CASCADE";

    //    " FOREIGN KEY ("+TASK_CAT+") REFERENCES "+CAT_TABLE+" ("+CAT_ID+"), "

    public static final String SQL_CREATE_BRACKET_TABLE =
            "CREATE TABLE " + BracketTable.NAME + " (" +
                    BracketTable.Cols._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    BracketTable.Cols.FK_EVENT_ID + " INTEGER" + "," +
                    BracketTable.Cols.NAME + TEXT_TYPE + "," +
                    BracketTable.Cols.URL + TEXT_TYPE + "," +
                    BracketTable.Cols.TYPE + TEXT_TYPE +

                    ")";

//    SQL_SET_EVENT_FK_CONSTRAINT +

    public static final String SQL_DROP_EVENTS_TABLE = "DROP TABLE IF EXISTS " + BracketTable.NAME;

    @Override
    public void onConfigure(SQLiteDatabase db){
        db.setForeignKeyConstraintsEnabled(true);
    }

    public BracketDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        DBSchema.createAllTables(db);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        DBSchema.onUpgrade(db, oldVersion, newVersion);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        DBSchema.onDowngrade(db, oldVersion, newVersion);
    }
}
