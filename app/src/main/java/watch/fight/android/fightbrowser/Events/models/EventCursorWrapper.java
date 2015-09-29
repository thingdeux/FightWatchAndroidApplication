package watch.fight.android.fightbrowser.Events.models;

import android.database.Cursor;
import android.database.CursorWrapper;

import watch.fight.android.fightbrowser.Utils.DateParser;

import static watch.fight.android.fightbrowser.Events.models.EventDBSchema.*;

/**
 * Created by josh on 9/28/15.
 */
public class EventCursorWrapper extends CursorWrapper {
    public EventCursorWrapper(Cursor cursor) { super(cursor); }

    public Event getEvent() {
        Long id = getLong(getColumnIndex(EventTable.Cols.ID));
        String name = getString(getColumnIndex(EventTable.Cols.NAME));
        Long date = getLong(getColumnIndex(EventTable.Cols.EVENT_DATE));

        Event event = new Event();
        event.setId(id);
        // TODO FIX This - Store the field as long from the API and long in the model
        event.setDate(DateParser.epochToDate(date).toString());
        event.setEventName(name);
        return event;
    }
}
