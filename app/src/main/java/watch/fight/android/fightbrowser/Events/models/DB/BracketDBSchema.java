package watch.fight.android.fightbrowser.Events.models.DB;

import android.provider.BaseColumns;

/**
 * Created by josh on 10/3/15.
 */
public class BracketDBSchema {
    public BracketDBSchema() {}

    // Bracket Schema
    public static final class BracketTable {
        public static final String NAME = "brackets";
        public static abstract class Cols implements BaseColumns {
            public static final String FK_EVENT_ID = "fkeventid";
            public static final String NAME = "bracketname";
            public static final String URL = "bracketurl";
            public static final String TYPE = "brackettype";
            public static final String IS_VERIFIED = "bracketverified";
            public static final String USER_ADDED = "bracketuseradded";
        }
    }
}
