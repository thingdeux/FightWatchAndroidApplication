package watch.fight.android.fightbrowser.Events.models.DB;

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
            public static final String START_DATE = "eventstartdate";
            public static final String END_DATE = "eventenddate";
            public static final String HEADER_IMAGE_URL = "eventheaderimageurl";
            public static final String WEBSITE = "eventwebsite";
            public static final String FLAVOR_TEXT = "eventflavortext";
            public static final String BRACKETSUPDATED = "bracketsupdated";
        }

    }
}
