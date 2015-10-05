package watch.fight.android.fightbrowser.StreamBrowser.models;

import android.database.Cursor;
import android.database.CursorWrapper;

import static watch.fight.android.fightbrowser.StreamBrowser.models.StreamerDBSchema.*;

/**
 * Created by josh on 10/4/15.
 */
public class StreamerCursorWrapper extends CursorWrapper {
    public StreamerCursorWrapper(Cursor cursor) { super(cursor); }

    public Streamer getStreamer() {
        Integer game_id = getInt(getColumnIndex(StreamerTable.Cols.FK_GAME_ID));
        String name = getString(getColumnIndex(StreamerTable.Cols.NAME));
        int isFavorite = getInt(getColumnIndex(StreamerTable.Cols.IS_FAVORITE));

        Streamer streamer = new Streamer();
        streamer.setName(name);
        streamer.setRelatedGame(game_id);
        streamer.setIsFavorite((isFavorite > 0));

        return streamer;
    }
}
