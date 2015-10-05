package watch.fight.android.fightbrowser.Utils.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by josh on 10/4/15.
 */
public abstract class BaseDB extends SQLiteOpenHelper {
    protected static final int DATABASE_VERSION = 1;
    protected static final String DATABASE_NAME = DBSchema.DBName;
    protected static final String TEXT_TYPE = " TEXT";
    protected static final String INTEGER_TYPE = " INTEGER";

    public BaseDB(Context context) {
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
