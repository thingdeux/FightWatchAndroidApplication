package watch.fight.android.fightbrowser.Brackets;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import watch.fight.android.fightbrowser.Brackets.models.Match;
import watch.fight.android.fightbrowser.Brackets.models.MatchWrapper;
import watch.fight.android.fightbrowser.Brackets.models.Participant;
import watch.fight.android.fightbrowser.Brackets.models.ParticipantWrapper;
import watch.fight.android.fightbrowser.Brackets.models.TournamentWrapper;
import watch.fight.android.fightbrowser.Events.models.Bracket;

/**
 * Created by josh on 10/5/15.
 */
public class ParticipantsHolder {
    private static ParticipantsHolder sParticipantsHolder;
    private TournamentWrapper data;
    private List<MatchWrapper> mUpcomingMatches = new ArrayList<>();
    private HashSet<String> mActiveParticipants = new HashSet<>();
    private List<String> mActiveParticipantIds = new ArrayList<>();
    private HashMap<String, Participant> mParticipants = new HashMap<>();
    private List<MatchWrapper> mMatches = new ArrayList<>();
    private Bracket mBracket;
    private Context mContext;
    private boolean mIsTournamentActive;

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
        BuildParticipantMap(data.getTournament().getParticipants());
        mMatches = data.getTournament().getMatches();
        BuildActivePlayersAndMatches();
        setIsTournamentActive();
    }

    public void BuildActivePlayersAndMatches() {
        if (mMatches != null) {
            Match m;
            for (int i = 0; i < mMatches.size(); i++) {
                m = mMatches.get(i).getMatch();
                if (m != null) {
                    if (m.getState() != null)
                    {
                        if(m.getState().toLowerCase().equals("pending") || m.getState().toLowerCase().equals("open")) {
                            if (m.getPlayerOneId() != null || m.getPlayerTwoId() != null) {
                                // Don't want to see the empty brackets from way in the future here
                                mUpcomingMatches.add(mMatches.get(i));
                            }

                            addActivePlayer(m.getPlayerOneId());
                            addActivePlayer(m.getPlayerTwoId());
                        }
                    }
                }
            }
            for (String s : mActiveParticipants) {
                mActiveParticipantIds.add(s);
            }
        }
    }

    public void addActivePlayer(String id) {
        if (id != null && !id.isEmpty()) {
            mActiveParticipants.add(id);
        }
    }

    public void BuildParticipantMap(List<ParticipantWrapper> participants) {
        if (participants != null) {
            Participant p;
            for (int i = 0; i < participants.size(); i++) {
                p = participants.get(i).getParticipant();
                mParticipants.put(p.getId().toString(), p);
            }
        }
    }

    public void setIsTournamentActive() {
        if (data != null) {
            if (data.getTournament().getState() != null) {
                mIsTournamentActive = (data.getTournament().getState().toLowerCase().equals("underway"));
            }
        }

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

    public List<MatchWrapper> getUpcomingMatches() {
        return mUpcomingMatches;
    }

    public HashMap<String, Participant> getParticipants() {
        return mParticipants;
    }

    public List<MatchWrapper> getMatches() {
        return mMatches;
    }

    public boolean isTournamentActive() {
        return mIsTournamentActive;
    }

    public List<String> getActiveParticipantIds() {
        return mActiveParticipantIds;
    }

    public void wipe() {
        mActiveParticipantIds.clear();
        mActiveParticipants.clear();
        mParticipants.clear();
        mMatches.clear();
        mUpcomingMatches.clear();
        mIsTournamentActive = false;
        mBracket = null;
        mContext = null;

    }
}
