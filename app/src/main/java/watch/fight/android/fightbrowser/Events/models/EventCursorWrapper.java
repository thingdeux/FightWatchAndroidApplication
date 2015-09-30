package watch.fight.android.fightbrowser.Events.models;

import android.database.Cursor;
import android.database.CursorWrapper;

import static watch.fight.android.fightbrowser.Events.models.EventDBSchema.*;

/**
 * Created by josh on 9/28/15.
 */
public class EventCursorWrapper extends CursorWrapper {
    public EventCursorWrapper(Cursor cursor) { super(cursor); }

    public Event getEvent() {
        Long id = getLong(getColumnIndex(EventTable.Cols.ID));
        String name = getString(getColumnIndex(EventTable.Cols.NAME));
        String headerUrl = getString(getColumnIndex(EventTable.Cols.HEADER_IMAGE_URL));
        String website = getString(getColumnIndex(EventTable.Cols.WEBSITE));
        Long startDate = getLong(getColumnIndex(EventTable.Cols.START_DATE));
        Long endDate = getLong(getColumnIndex(EventTable.Cols.END_DATE));

        Event event = new Event();
        event.setId(id);
        event.setStartDate(startDate);
        event.setEndDate(endDate);
        event.setEventName(name);
        event.setHeaderImageUrl(headerUrl);
        event.setWebsite(website);

        return event;
    }
}
