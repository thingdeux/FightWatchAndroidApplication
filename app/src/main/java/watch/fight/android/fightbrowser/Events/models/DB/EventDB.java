package watch.fight.android.fightbrowser.Events.models.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import watch.fight.android.fightbrowser.Events.models.DB.EventDBSchema.EventTable;
import watch.fight.android.fightbrowser.Events.models.Event;
import watch.fight.android.fightbrowser.InformationFeeds.models.DB.FeedDBSchema;
import watch.fight.android.fightbrowser.InformationFeeds.models.Feed;

/**
 * Created by josh on 9/28/15.
 */
public class EventDB {
    private static EventDB sEventDB;
    private SQLiteDatabase mDatabase;
    private Context mContext;

    public static EventDB getInstance(Context context) {
        if (sEventDB == null) {
            sEventDB = new EventDB(context);
        }
        return sEventDB;
    }

    private EventDB(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new EventDBHelper(mContext).getWritableDatabase();
    }

    public Event getEvent(Long id) {
        EventCursorWrapper cursor = queryEvents(
                EventTable.Cols.ID + " = ?",
                new String[]{"" + id}
        );

        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getEvent();
        } finally {
            cursor.close();
        }
    }

    public List<Event> getAllEvents() {
        List<Event> events = new ArrayList<>();

        // Pass no where clause or args, will return everything.
        EventCursorWrapper cursor = queryEvents(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                events.add(cursor.getEvent());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return events;
    }

    public List<Event> getAllUpcomingEvents() {
        List<Event> events = new ArrayList<>();
        GregorianCalendar now = new GregorianCalendar();
        Long nowMil = now.getTimeInMillis();

        // Pass no where clause or args, will return everything.
        EventCursorWrapper cursor = queryEvents(
                EventTable.Cols.START_DATE + " >= ?",
                new String[] { "" + nowMil });

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                events.add(cursor.getEvent());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return events;
    }

    public void addEvent(Event event) {
        ContentValues values = getContentValues(event);
        mDatabase.insert(EventTable.NAME, null, values);
    }

    public void addEvents(List<Event> events) {
        if (events != null && events.size() > 0) {
            mDatabase.beginTransaction();
            for (int i = 0; i < events.size(); i++) {
                ContentValues values = getContentValues(events.get(i));
                mDatabase.insert(EventTable.NAME, null, values);
            }
            mDatabase.setTransactionSuccessful();
            mDatabase.endTransaction();
        } else {
            Log.e("addEvents", "Error - Attemping to add 0 or null events to DB");
        }

    }

    public void deleteEvent(float id) {
        Log.v("DeleteEvent", "Deleting Event: " + id);
        deleteEvents(EventTable.Cols.ID + " = ?", new String[]{"" + id});
    }

    public void deleteAllEvents() {
        Log.v("deleteEvents", "Deleting All Events!");
        mDatabase.delete(EventTable.NAME, null, null);
    }

    public void updateEvent(Event event) {
        String id = "" + event.getId();
        ContentValues values = getContentValues(event);

        mDatabase.update(EventTable.NAME, values,
                EventTable.Cols.ID + " = ?",
                new String[]{id});
    }

    private void deleteEvents(String whereClause, String[] whereArgs) {
        int isDeleted = mDatabase.delete(EventTable.NAME, whereClause, whereArgs);
        if (whereClause == null && isDeleted != 1) {
            Log.e("deleteEvents", "Unable to delete events -> " + whereArgs);
        } else {
            Log.i("deleteEvents", "Deleted: " + isDeleted + " events");
        }
    }

    private EventCursorWrapper queryEvents(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                EventTable.NAME,
                null, // Columns -null selects all columns
                whereClause,
                whereArgs,
                null, //GroupBy
                null, // having
                null // orderBy
        );

        return new EventCursorWrapper(cursor);
    }

    private static ContentValues getContentValues(Event event) {
        ContentValues values = new ContentValues();
        values.put(EventTable.Cols.ID, event.getId());
        values.put(EventTable.Cols.NAME, event.getEventName());
        values.put(EventTable.Cols.HEADER_IMAGE_URL, event.getHeaderImageUrl());
        values.put(EventTable.Cols.START_DATE, event.getStartDate());
        values.put(EventTable.Cols.END_DATE, event.getEndDate());
        values.put(EventTable.Cols.WEBSITE, event.getWebsite());
        values.put(EventTable.Cols.FLAVOR_TEXT, event.getFlavorText());
        values.put(EventTable.Cols.BRACKETSUPDATED, event.getLastUpdated());

        return values;
    }
}
