package watch.fight.android.fightbrowser.Config.models.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import watch.fight.android.fightbrowser.Utils.Database.BaseDB;


import static watch.fight.android.fightbrowser.Config.models.DB.GameDBSchema.GameTable;

/**
 * Created by josh on 10/1/15.
 */
public class GameDBHelper extends BaseDB {
    public static final String SQL_INDEX_GAME_ID = "CREATE INDEX " + GameTable.Cols.ID +
            "_index " + "ON " + GameTable.NAME + "(" + GameTable.Cols.ID + ")";

    public static final String SQL_CREATE_FEED_TABLE =
            "CREATE TABLE " + GameTable.NAME + " (" +
                    GameTable.Cols._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    GameTable.Cols.ID + " INTEGER UNIQUE NOT NULL" + "," +
                    GameTable.Cols.GAME_NAME + TEXT_TYPE + "," +
                    GameTable.Cols.IS_FILTERED + INTEGER_TYPE + "," +
                    GameTable.Cols.DATE_ADDED + INTEGER_TYPE + "," +
                    GameTable.Cols.ORDINAL + INTEGER_TYPE +
                    " )";

    public static final String SQL_DROP_GAMES_TABLE = "DROP TABLE IF EXISTS " + GameTable.NAME;

    @Override
    public void onConfigure(SQLiteDatabase db){
        db.setForeignKeyConstraintsEnabled(true);
    }

    public GameDBHelper(Context context) {
        super(context);
    }

}
