package watch.fight.android.fightbrowser.StreamBrowser.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import watch.fight.android.fightbrowser.Config.models.GameConfig;


/**
 * Created by josh on 10/4/15.
 */
public class StreamerDB {
    private static StreamerDB sStreamerDB;
    private SQLiteDatabase mDatabase;
    private Context mContext;

    public static StreamerDB getInstance(Context context) {
        if (sStreamerDB == null) {
            sStreamerDB = new StreamerDB(context);
        }
        return sStreamerDB;
    }

    private StreamerDB(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new StreamerDBHelper(mContext).getWritableDatabase();
    }

    public List<Streamer> getStreamers(int fk_game_id) {
        List<Streamer> streamers = new ArrayList<>();

        StreamerCursorWrapper cursor = queryStreamers(
                StreamerDBSchema.StreamerTable.Cols.FK_GAME_ID + " = ?",
                new String[]{"" + fk_game_id}
        );

        try {
            if (cursor.getCount() == 0) {
                return null;
            } else {
                Log.i("StreamersFound", "Found " + cursor.getCount() + " brackets for " + fk_game_id);
            }

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                streamers.add(cursor.getStreamer());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return streamers;
    }

    public List<Streamer> getAllStreamers() {
        List<Streamer> streamers = new ArrayList<>();

        // Pass no where clause or args, will return everything.
        StreamerCursorWrapper cursor = queryStreamers(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                streamers.add(cursor.getStreamer());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return streamers;
    }

    public void addStreamer(Streamer streamer, GameConfig parentGame) {
        if (parentGame != null) {
            streamer.setRelatedGame(parentGame.getId());
        }
        ContentValues values = getContentValues(streamer);
        mDatabase.insert(StreamerDBSchema.StreamerTable.NAME, null, values);
    }

    public void addStreamers(List<String> streamers, GameConfig parentGame) {
        if (streamers != null && streamers.size() > 0) {
            mDatabase.beginTransaction();
            Streamer streamer = new Streamer();
            for (int i = 0; i < streamers.size(); i++) {
                // Known Streamers come in from the API as a list of strings, assign them default values
                // And insert. Re-Using the instantiated streamer object.
                streamer.setName(streamers.get(i));
                streamer.setRelatedGame(parentGame.getId());
                streamer.setIsFavorite(false);
                ContentValues values = getContentValues(streamer);
                mDatabase.insert(StreamerDBSchema.StreamerTable.NAME, null, values);
            }
            mDatabase.setTransactionSuccessful();
            mDatabase.endTransaction();
        } else {
            Log.e("addStreamers", "Error - Attemping to add 0 or null streamers to DB");
        }

    }

    public void deleteStreamers(long fk_game_id) {
        Log.v("DeleteStreamer", "Deleting Streamer: " + fk_game_id);
        deleteStreamers(StreamerDBSchema.StreamerTable.Cols.FK_GAME_ID + " = ?", new String[]{"" + fk_game_id});
    }

    public void deleteAllStreamers() {
        Log.v("deleteStreamers", "Deleting All Streamers!");
        mDatabase.delete(StreamerDBSchema.StreamerTable.NAME, null, null);
    }

    private void deleteStreamers(String whereClause, String[] whereArgs) {
        int isDeleted = mDatabase.delete(StreamerDBSchema.StreamerTable.NAME, whereClause, whereArgs);
        if (whereClause == null && isDeleted != 1) {
            Log.e("deleteStreamers", "Unable to delete streamers -> " + whereArgs);
        } else {
            Log.i("deleteStreamers", "Deleted: " + isDeleted + " streamers");
        }
    }

    private StreamerCursorWrapper queryStreamers(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                StreamerDBSchema.StreamerTable.NAME,
                null, // Columns -null selects all columns
                whereClause,
                whereArgs,
                null, //GroupBy
                null, // having
                null // orderBy
        );

        return new StreamerCursorWrapper(cursor);
    }

    private static ContentValues getContentValues(Streamer streamer) {
        ContentValues values = new ContentValues();
        values.put(StreamerDBSchema.StreamerTable.Cols.FK_GAME_ID, streamer.getRelatedGame());
        values.put(StreamerDBSchema.StreamerTable.Cols.NAME, streamer.getName());
        values.put(StreamerDBSchema.StreamerTable.Cols.IS_FAVORITE, streamer.getRelatedGame());
        return values;
    }
}
