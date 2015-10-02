package watch.fight.android.fightbrowser.Config.models.DB;

import android.provider.BaseColumns;

/**
 * Created by josh on 9/28/15.
 */
public class GameDBSchema {
    public GameDBSchema() {}

    // Game Schema
    public static final class GameTable {
        public static final String NAME = "games";
        public static abstract class Cols implements BaseColumns {
            public static final String ID = "gameid";
            public static final String GAME_NAME = "gamename";
            public static final String DATE_ADDED = "gamedateadded";
            public static final String IS_FILTERED = "gameisfiltered";
            public static final String ORDINAL = "gameordinal";
        }

    }
}
