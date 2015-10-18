package watch.fight.android.fightbrowser.Events.models.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import watch.fight.android.fightbrowser.Utils.Database.BaseDB;

import static watch.fight.android.fightbrowser.Events.models.DB.BracketDBSchema.*;

/**
 * Created by josh on 10/4/15.
 */
public class BracketDBHelper extends BaseDB {
    public static final String SQL_INDEX_FK_EVENT_ID = "CREATE INDEX " + BracketTable.Cols.FK_EVENT_ID +
            "_index " + "ON " + BracketTable.NAME + "(" + BracketTable.Cols.FK_EVENT_ID + ")";

    private static final String SQL_SET_EVENT_FK_CONSTRAINT = " FOREIGN KEY(" + BracketTable.Cols.FK_EVENT_ID + ") " +
            "REFERENCES " + EventDBSchema.EventTable.NAME + "(" + EventDBSchema.EventTable.Cols.ID + ") ON DELETE CASCADE";

    public static final String SQL_CREATE_BRACKET_TABLE =
            "CREATE TABLE " + BracketTable.NAME + " (" +
                    BracketTable.Cols._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    BracketTable.Cols.FK_EVENT_ID + " INTEGER" + "," +
                    BracketTable.Cols.NAME + TEXT_TYPE + "," +
                    BracketTable.Cols.URL + TEXT_TYPE + "," +
                    BracketTable.Cols.TYPE + TEXT_TYPE + "," +
                    BracketTable.Cols.IS_VERIFIED + INTEGER_TYPE + "," +
                    BracketTable.Cols.USER_ADDED + INTEGER_TYPE + "," +
                    SQL_SET_EVENT_FK_CONSTRAINT +
                    ")";

    public static final String SQL_DROP_EVENTS_TABLE = "DROP TABLE IF EXISTS " + BracketTable.NAME;

    @Override
    public void onConfigure(SQLiteDatabase db){
        db.setForeignKeyConstraintsEnabled(true);
    }

    BracketDBHelper(Context context) {
        super(context);
    }

}
