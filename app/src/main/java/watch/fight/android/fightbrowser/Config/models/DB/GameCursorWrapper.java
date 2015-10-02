package watch.fight.android.fightbrowser.Config.models.DB;

import android.database.Cursor;
import android.database.CursorWrapper;

import watch.fight.android.fightbrowser.Config.models.GameConfig;

/**
 * Created by josh on 10/1/15.
 */
public class GameCursorWrapper extends CursorWrapper {
    public GameCursorWrapper(Cursor cursor) { super(cursor); }

    public GameConfig getGame() {
        Integer id = getInt(getColumnIndex(GameDBSchema.GameTable.Cols.ID));
        String name = getString(getColumnIndex(GameDBSchema.GameTable.Cols.GAME_NAME));
        Long dateAdded = getLong(getColumnIndex(GameDBSchema.GameTable.Cols.DATE_ADDED));
        Integer isFiltered = getInt(getColumnIndex(GameDBSchema.GameTable.Cols.IS_FILTERED));

        GameConfig gameConfig = new GameConfig();
        gameConfig.setId(id);
        gameConfig.setGameName(name);
        gameConfig.setDateAdded(dateAdded);
        gameConfig.setIsFiltered(isFiltered == 1);

        return gameConfig;
    }
}
