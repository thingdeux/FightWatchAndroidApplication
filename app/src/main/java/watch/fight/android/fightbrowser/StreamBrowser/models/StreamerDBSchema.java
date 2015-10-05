package watch.fight.android.fightbrowser.StreamBrowser.models;

import android.provider.BaseColumns;

/**
 * Created by josh on 10/4/15.
 */
public class StreamerDBSchema {
    public StreamerDBSchema() {}

    // Streamer Schema
    public static final class StreamerTable {
        public static final String NAME = "streamers";
        public static abstract class Cols implements BaseColumns {
            public static final String FK_GAME_ID = "fkgameid";
            public static final String NAME = "streamername";
            public static final String IS_FAVORITE = "streamerisfavorite";
        }
    }
}
