package watch.fight.android.fightbrowser.Events.models;

import android.provider.BaseColumns;

/**
 * Created by josh on 9/28/15.
 */
public class EventDBSchema {
    public EventDBSchema() {}

    // Story Schema
    public static final class EventTable {
        public static final String NAME = "events";
        public static abstract class Cols implements BaseColumns {
            public static final String ID = "eventid";
            public static final String NAME = "eventname";
            public static final String EVENT_DATE = "eventdate";
        }

    }
}
