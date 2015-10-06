package watch.fight.android.fightbrowser.Brackets;

import android.content.Context;

import watch.fight.android.fightbrowser.Brackets.models.TournamentWrapper;
import watch.fight.android.fightbrowser.Events.models.Bracket;

/**
 * Created by josh on 10/5/15.
 */
public class ParticipantsHolder {
    private static ParticipantsHolder sParticipantsHolder;
    private TournamentWrapper data;
    private Bracket mBracket;
    private Context mContext;

    public static ParticipantsHolder getInstance(Context context) {
        if (sParticipantsHolder == null) {
            sParticipantsHolder = new ParticipantsHolder(context);
        }
        return sParticipantsHolder;
    }

    private ParticipantsHolder(Context context) {
        mContext = context;
    }

    public void setTournamentWrapper(TournamentWrapper wrapper) {
        data = wrapper;
    }

    public TournamentWrapper getTournamentWrapper() {
        return data;
    }

    public Bracket getBracket() {
        return mBracket;
    }

    public void setBracket(Bracket bracket) {
        mBracket = bracket;
    }
}
