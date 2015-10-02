package watch.fight.android.fightbrowser.Events.models.DB;

import android.database.Cursor;
import android.database.CursorWrapper;

import watch.fight.android.fightbrowser.Events.models.Event;

/**
 * Created by josh on 9/28/15.
 */
public class EventCursorWrapper extends CursorWrapper {
    public EventCursorWrapper(Cursor cursor) { super(cursor); }

    public Event getEvent() {
        Long id = getLong(getColumnIndex(EventDBSchema.EventTable.Cols.ID));
        String name = getString(getColumnIndex(EventDBSchema.EventTable.Cols.NAME));
        String headerUrl = getString(getColumnIndex(EventDBSchema.EventTable.Cols.HEADER_IMAGE_URL));
        String flavorText = getString(getColumnIndex(EventDBSchema.EventTable.Cols.FLAVOR_TEXT));
        String website = getString(getColumnIndex(EventDBSchema.EventTable.Cols.WEBSITE));
        Long startDate = getLong(getColumnIndex(EventDBSchema.EventTable.Cols.START_DATE));
        Long endDate = getLong(getColumnIndex(EventDBSchema.EventTable.Cols.END_DATE));

        Event event = new Event();
        event.setId(id);
        event.setStartDate(startDate);
        event.setEndDate(endDate);
        event.setEventName(name);
        event.setHeaderImageUrl(headerUrl);
        event.setWebsite(website);
        event.setFlavorText(flavorText);

        return event;
    }
}
