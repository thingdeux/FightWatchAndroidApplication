package watch.fight.android.fightbrowser.InformationFeeds.models.DB;

import android.provider.BaseColumns;

/**
 * Created by josh on 10/8/15.
 */
public class StoryTrackerDBSchema {
    public StoryTrackerDBSchema() {}

    // StoryTracker Schema
    public static final class StoryTrackerTable {
        public static final String NAME = "storytrackers";
        public static abstract class Cols implements BaseColumns {
            public static final String URL = "storytrackerurl";
            public static final String DATE_ADDED = "storytrackerdateadded";
        }
    }
}
