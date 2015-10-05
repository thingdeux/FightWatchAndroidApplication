package watch.fight.android.fightbrowser.StreamBrowser.models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import watch.fight.android.fightbrowser.Utils.Database.BaseDB;

import static watch.fight.android.fightbrowser.Config.models.DB.GameDBSchema.GameTable;
import static watch.fight.android.fightbrowser.StreamBrowser.models.StreamerDBSchema.StreamerTable;

/**
 * Created by josh on 10/4/15.
 */
public class StreamerDBHelper extends BaseDB {
    public static final String SQL_INDEX_FK_GAME_ID = "CREATE INDEX " + StreamerTable.Cols.FK_GAME_ID +
            "_index " + "ON " + StreamerTable.NAME + "(" + StreamerTable.Cols.FK_GAME_ID + ")";

    private static final String SQL_SET_GAME_FK_CONSTRAINT = " FOREIGN KEY(" + StreamerTable.Cols.FK_GAME_ID + ") " +
            "REFERENCES " + GameTable.NAME + "(" + GameTable.Cols.ID + ") ON DELETE CASCADE";

    public static final String SQL_CREATE_STREAMER_TABLE =
            "CREATE TABLE " + StreamerTable.NAME + " (" +
                    StreamerTable.Cols._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    StreamerTable.Cols.FK_GAME_ID + " INTEGER" + "," +
                    StreamerTable.Cols.IS_FAVORITE + " INTEGER" + "," +
                    StreamerTable.Cols.NAME + TEXT_TYPE + "," +
                    SQL_SET_GAME_FK_CONSTRAINT +
                    ")";

    public static final String SQL_DROP_STREAMER_TABLE = "DROP TABLE IF EXISTS " + StreamerTable.NAME;

    @Override
    public void onConfigure(SQLiteDatabase db){
        db.setForeignKeyConstraintsEnabled(true);
    }

    StreamerDBHelper(Context context) {
        super(context);
    }
}
