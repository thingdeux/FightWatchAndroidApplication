package watch.fight.android.fightbrowser.InformationFeeds.models.DB;

import android.provider.BaseColumns;

/**
 * Created by josh on 9/25/15.
 */
public final class FeedDBSchema {
    public FeedDBSchema() {}

    // Feed Schema
    public static final class FeedTable {
        public static final String NAME = "feeds";
        public static abstract class Cols implements BaseColumns {
            public static final String ID = "feedid";
            public static final String NAME = "feedname";
            public static final String RSS_URL = "feedrssurl";
            public static final String PARENT_URL = "feedparenturl";
            public static final String IMAGE_URL = "feedimageurl";
            public static final String LAST_UPDATED = "feedlastupdated";
        }

    }

}
