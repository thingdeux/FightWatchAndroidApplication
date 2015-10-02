package watch.fight.android.fightbrowser.Config.models.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import watch.fight.android.fightbrowser.Utils.DBSchema;

/**
 * Created by josh on 10/1/15.
 */
public class GameDBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = DBSchema.DBName;
    private static final String TEXT_TYPE = " TEXT";
    public static final String SQL_INDEX_GAME_ID = "CREATE INDEX " + GameDBSchema.GameTable.Cols.ID +
            "_index " + "ON " + GameDBSchema.GameTable.NAME + "(" + GameDBSchema.GameTable.Cols.ID + ")";

    public static final String SQL_CREATE_FEED_TABLE =
            "CREATE TABLE " + GameDBSchema.GameTable.NAME + " (" +
                    GameDBSchema.GameTable.Cols._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    GameDBSchema.GameTable.Cols.ID + " INTEGER" + "," +
                    GameDBSchema.GameTable.Cols.GAME_NAME + TEXT_TYPE + "," +
                    GameDBSchema.GameTable.Cols.IS_FILTERED + " INTEGER " + "," +
                    GameDBSchema.GameTable.Cols.DATE_ADDED + " INTEGER " + "," +
                    GameDBSchema.GameTable.Cols.ORDINAL + " INTEGER " +
                    " )";

    public static final String SQL_DROP_GameS_TABLE = "DROP TABLE IF EXISTS " + GameDBSchema.GameTable.NAME;


    public GameDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        DBSchema.createAllTables(db);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // For now blow the table out and recreate on upgrade
        db.execSQL(SQL_DROP_GameS_TABLE);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Not Implemented - Intentionally blank
    }

}
