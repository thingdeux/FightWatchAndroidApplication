package watch.fight.android.fightbrowser.Events.models.DB;

import android.database.Cursor;
import android.database.CursorWrapper;

import watch.fight.android.fightbrowser.Events.models.Bracket;
import watch.fight.android.fightbrowser.Events.models.DB.BracketDBSchema.BracketTable;


/**
 * Created by josh on 10/4/15.
 */
public class BracketCursorWrapper extends CursorWrapper {
    public BracketCursorWrapper(Cursor cursor) { super(cursor); }

    public Bracket getBracket() {
        long event_id = getLong(getColumnIndex(BracketTable.Cols.FK_EVENT_ID));
        String name = getString(getColumnIndex(BracketTable.Cols.NAME));
        String type = getString(getColumnIndex(BracketTable.Cols.TYPE));
        String url = getString(getColumnIndex(BracketTable.Cols.URL));
        Integer isVerified = getInt(getColumnIndex(BracketTable.Cols.IS_VERIFIED));
        Integer id = getInt(getColumnIndex(BracketTable.Cols._ID));
        Integer isUserAdded = getInt(getColumnIndex(BracketTable.Cols.USER_ADDED));

        Bracket bracket = new Bracket();
        bracket.setId(id);
        bracket.setBracketName(name);
        bracket.setBracketUrl(url);
        bracket.setRelatedEvent(event_id);
        bracket.setBracketType(type);
        bracket.setIsVerified(isVerified > 0);
        bracket.setIsUserAdded(isUserAdded > 0);

        return bracket;
    }
}
