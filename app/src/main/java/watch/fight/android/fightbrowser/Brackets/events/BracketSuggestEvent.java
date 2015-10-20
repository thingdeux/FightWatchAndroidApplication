package watch.fight.android.fightbrowser.Brackets.events;

import watch.fight.android.fightbrowser.Events.models.Bracket;

/**
 * Created by josh on 10/20/15.
 */
public class BracketSuggestEvent {
    public static final int NEW_BRACKET = 0;
    public static final int VERIFY_CURRENT_BRACKET = 1;
    public Bracket mBracket;
    public int submissionType;

    public BracketSuggestEvent(Bracket bracket, int submissionType) {
        this.mBracket = bracket;
        this.submissionType = submissionType;
    }
}
