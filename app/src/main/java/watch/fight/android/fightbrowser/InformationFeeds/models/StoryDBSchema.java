package watch.fight.android.fightbrowser.InformationFeeds.models;

import android.provider.BaseColumns;

/**
 * Created by josh on 9/26/15.
 */
public final class StoryDBSchema {
    public StoryDBSchema() {}

    // Story Schema
    public static final class StoryTable {
        public static final String NAME = "stories";
        public static abstract class Cols implements BaseColumns {
            public static final String SITE_NAME = "storysitename";
            public static final String TITLE = "storytitle";
            public static final String DESCRIPTION = "storydescription";
            public static final String URL = "storyurl";
            public static final String AUTHOR = "storyauthor";
            public static final String PUBLISHED_DATE = "storypublisheddate";
            public static final String LAST_UPDATED = "storylastupdated";
            public static final String THUMBNAIL = "storythumbnail";
        }

    }

}
