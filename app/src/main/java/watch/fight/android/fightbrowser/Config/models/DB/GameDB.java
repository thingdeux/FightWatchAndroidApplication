package watch.fight.android.fightbrowser.Config.models.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import watch.fight.android.fightbrowser.Config.models.GameConfig;

/**
 * Created by josh on 10/1/15.
 */
public class GameDB {
    private static GameDB sGameDB;
    private SQLiteDatabase mDatabase;
    private Context mContext;

    public static GameDB getInstance(Context context) {
        if (sGameDB == null) {
            sGameDB = new GameDB(context);
        }
        return sGameDB;
    }

    private GameDB(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new GameDBHelper(mContext).getWritableDatabase();
    }

    public GameConfig getGame(Long id) {
        GameCursorWrapper cursor = queryGames(
                GameDBSchema.GameTable.Cols.ID + " = ?",
                new String[]{"" + id}
        );

        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getGame();
        } finally {
            cursor.close();
        }
    }

    public List<GameConfig> getAllGames() {
        List<GameConfig> games = new ArrayList<>();

        // Pass no where clause or args, will return everything.
        GameCursorWrapper cursor = queryGames(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                games.add(cursor.getGame());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return games;
    }

    public void addGame(GameConfig game) {
        if (game != null && game.getId() != null && game.getId() != 0) {
            ContentValues values = getContentValues(game);
            mDatabase.insert(GameDBSchema.GameTable.NAME, null, values);
        }
    }

    public void addGames(List<GameConfig> games) {
        if (games != null && games.size() > 0) {
            mDatabase.beginTransaction();
            for (int i = 0; i < games.size(); i++) {
                ContentValues values = getContentValues(games.get(i));
                mDatabase.insert(GameDBSchema.GameTable.NAME, null, values);
            }
            mDatabase.setTransactionSuccessful();
            mDatabase.endTransaction();
        } else {
            Log.e("addGames", "Error - Attemping to add 0 or null games to DB");
        }

    }

    public void deleteGame(float id) {
        Log.v("DeleteGame", "Deleting Game: " + id);
        deleteGames(GameDBSchema.GameTable.Cols._ID + " = ?", new String[]{"" + id});
    }

    public void deleteAllGames() {
        Log.v("deleteGames", "Deleting All Games!");
        mDatabase.delete(GameDBSchema.GameTable.NAME, null, null);
    }

    private void deleteGames(String whereClause, String[] whereArgs) {
        int isDeleted = mDatabase.delete(GameDBSchema.GameTable.NAME, whereClause, whereArgs);
        if (whereClause == null && isDeleted != 1) {
            Log.e("deleteGames", "Unable to delete games -> " + whereArgs);
        } else {
            Log.i("deleteGames", "Deleted: " + isDeleted + " games");
        }
    }

    private GameCursorWrapper queryGames(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                GameDBSchema.GameTable.NAME,
                null, // Columns -null selects all columns
                whereClause,
                whereArgs,
                null, //GroupBy
                null, // having
                null // orderBy
        );

        return new GameCursorWrapper(cursor);
    }

    private static ContentValues getContentValues(GameConfig config) {
        ContentValues values = new ContentValues();
        values.put(GameDBSchema.GameTable.Cols.ID, config.getId());
        values.put(GameDBSchema.GameTable.Cols.GAME_NAME, config.getGameName());
        values.put(GameDBSchema.GameTable.Cols.DATE_ADDED, config.getDateAdded());
        values.put(GameDBSchema.GameTable.Cols.ORDINAL, config.getOrdinal());

        int isFiltered = 0;
        // Convert bool to int - Stored in the DB as an INT
        if (config.getIsFiltered() != null) {
            isFiltered = (config.getIsFiltered()) ? 1 : 0;
        }

        values.put(GameDBSchema.GameTable.Cols.IS_FILTERED, isFiltered);

        return values;
    }
}