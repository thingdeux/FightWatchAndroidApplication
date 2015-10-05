package watch.fight.android.fightbrowser.Events.models.DB;

import android.database.Cursor;
import android.database.CursorWrapper;

import watch.fight.android.fightbrowser.Events.models.Bracket;


/**
 * Created by josh on 10/4/15.
 */
public class BracketCursorWrapper extends CursorWrapper {
    public BracketCursorWrapper(Cursor cursor) { super(cursor); }

    public Bracket getBracket() {
        long event_id = getLong(getColumnIndex(BracketDBSchema.BracketTable.Cols.FK_EVENT_ID));
        String name = getString(getColumnIndex(BracketDBSchema.BracketTable.Cols.NAME));
        String type = getString(getColumnIndex(BracketDBSchema.BracketTable.Cols.TYPE));
        String url = getString(getColumnIndex(BracketDBSchema.BracketTable.Cols.URL));

        Bracket bracket = new Bracket();
        bracket.setBracketName(name);
        bracket.setBracketUrl(url);
        bracket.setRelatedEvent(event_id);
        bracket.setBracketType(type);

        return bracket;
    }
}
