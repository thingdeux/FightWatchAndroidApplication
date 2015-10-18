package watch.fight.android.fightbrowser.Events.models.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import watch.fight.android.fightbrowser.Events.models.Bracket;
import watch.fight.android.fightbrowser.Events.models.DB.BracketDBSchema.BracketTable;
import watch.fight.android.fightbrowser.Events.models.Event;

/**
 * Created by josh on 10/4/15.
 */
public class BracketDB {
    private static int IS_VERIFIED_DEFAULT_STATE = 0;
    private static int IS_USER_ADDED_DEFAULT_STATE = 0;
    private static BracketDB sBracketDB;
    private SQLiteDatabase mDatabase;
    private Context mContext;

    public static BracketDB getInstance(Context context) {
        if (sBracketDB == null) {
            sBracketDB = new BracketDB(context);
        }
        return sBracketDB;
    }

    private BracketDB(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new BracketDBHelper(mContext).getWritableDatabase();
    }

    public List<Bracket> getBrackets(long fk_event_id) {
        List<Bracket> brackets = new ArrayList<>();

        BracketCursorWrapper cursor = queryBrackets(
                BracketTable.Cols.FK_EVENT_ID + " = ?",
                new String[]{"" + fk_event_id}
        );

        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                brackets.add(cursor.getBracket());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return brackets;
    }

    public Bracket getBracket(Integer id) {
        BracketCursorWrapper cursor = queryBrackets(
                BracketTable.Cols._ID + " = ?",
                new String[]{"" + id}
        );

        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getBracket();
        } finally {
            cursor.close();
        }
    }

    public List<Bracket> getAllBrackets() {
        List<Bracket> brackets = new ArrayList<>();

        // Pass no where clause or args, will return everything.
        BracketCursorWrapper cursor = queryBrackets(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                brackets.add(cursor.getBracket());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return brackets;
    }

    public void addBracket(Bracket bracket, Event parentEvent) {
        if (parentEvent != null) {
            bracket.setRelatedEvent(parentEvent.getId());
        }
        ContentValues values = getContentValues(bracket);
        mDatabase.insert(BracketTable.NAME, null, values);
    }

    public void addBrackets(List<Bracket> brackets, Event parentEvent) {
        if (brackets != null && brackets.size() > 0) {
            mDatabase.beginTransaction();
            for (int i = 0; i < brackets.size(); i++) {
                brackets.get(i).setRelatedEvent(parentEvent.getId());
                ContentValues values = getContentValues(brackets.get(i));
                mDatabase.insert(BracketTable.NAME, null, values);
            }
            mDatabase.setTransactionSuccessful();
            mDatabase.endTransaction();
        } else {
            Log.e("addBrackets", "Error - Attemping to add 0 or null brackets to DB");
        }

    }

    public void deleteBrackets(long event_id, boolean deleteUserAdded) {
        Log.v("DeleteBracket", "Deleting Bracket: " + event_id);
        if (deleteUserAdded) {
            // The cheaper query without doing table scan for USER_ADDED
            deleteBrackets(BracketTable.Cols.FK_EVENT_ID + " = ?", new String[]{"" + event_id});
        } else {
            deleteBrackets(BracketTable.Cols.FK_EVENT_ID + " = ? AND " + BracketTable.Cols.USER_ADDED + " = ?",
                    new String[]{"" + event_id, "0" });
        }

    }

    public void deleteAllBrackets() {
        Log.v("deleteBrackets", "Deleting All Brackets!");
        mDatabase.delete(BracketTable.NAME, null, null);
    }

    private void deleteBrackets(String whereClause, String[] whereArgs) {
        int isDeleted = mDatabase.delete(BracketTable.NAME, whereClause, whereArgs);
        if (whereClause == null && isDeleted != 1) {
            Log.e("deleteBrackets", "Unable to delete brackets -> " + whereArgs);
        } else {
            Log.i("deleteBrackets", "Deleted: " + isDeleted + " brackets");
        }
    }

    private BracketCursorWrapper queryBrackets(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                BracketTable.NAME,
                null, // Columns -null selects all columns
                whereClause,
                whereArgs,
                null, //GroupBy
                null, // having
                null // orderBy
        );

        return new BracketCursorWrapper(cursor);
    }

    private static ContentValues getContentValues(Bracket bracket) {
        ContentValues values = new ContentValues();
        // Bools stored in DB as Int / convert to bool
        int isVerified = IS_VERIFIED_DEFAULT_STATE;
        if (bracket.getIsVerified() != null) {
            isVerified = (bracket.getIsVerified()) ? 1 : 0;
        }

        int isUserAdded = IS_USER_ADDED_DEFAULT_STATE;
        if (bracket.getIsUserAdded() != null) {
            isUserAdded = (bracket.getIsUserAdded()) ? 1 : 0;
        }

        values.put(BracketTable.Cols._ID, bracket.getId());
        values.put(BracketTable.Cols.FK_EVENT_ID, bracket.getRelatedEvent());
        values.put(BracketTable.Cols.NAME, bracket.getBracketName());
        values.put(BracketTable.Cols.URL, bracket.getBracketUrl());
        values.put(BracketTable.Cols.TYPE, bracket.getBracketType());
        values.put(BracketTable.Cols.IS_VERIFIED, isVerified);
        values.put(BracketTable.Cols.USER_ADDED, isUserAdded);

        return values;
    }
}
