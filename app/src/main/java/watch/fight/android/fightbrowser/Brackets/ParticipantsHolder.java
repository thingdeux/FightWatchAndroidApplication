package watch.fight.android.fightbrowser.Brackets;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
    private HashSet<String> mActiveParticipantSet = new HashSet<>();
    private List<String> mActiveParticipants = new ArrayList<>();
    private List<String> mAllParticipants = new ArrayList<>();
    private HashMap<String, Participant> mParticipants = new HashMap<>();
    private List<MatchWrapper> mMatches = new ArrayList<>();
    private List<MatchWrapper> mValidMatches = new ArrayList<>();
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
                    addUpcomingMatch(m, i);
                }
            }

            for (String s : mActiveParticipantSet) {
                mActiveParticipants.add(s);
            }

            Collections.sort(mActiveParticipants, String.CASE_INSENSITIVE_ORDER);
        }
    }

    public void addActivePlayer(String id) {
        if (id != null && !id.isEmpty() && mParticipants.get(id) != null) {
            mActiveParticipantSet.add(mParticipants.get(id).getName());
        }
    }

    public void BuildParticipantMap(List<ParticipantWrapper> participants) {
        if (participants != null) {
            Participant p;
            for (int i = 0; i < participants.size(); i++) {
                p = participants.get(i).getParticipant();
                mParticipants.put(p.getId().toString(), p);
                if (p.getName() != null && !p.getName().isEmpty()) {
                    mAllParticipants.add(p.getName());
                }
            }

            Collections.sort(mAllParticipants, String.CASE_INSENSITIVE_ORDER);
        }
    }

    public void setIsTournamentActive() {
        if (data != null) {
            if (data.getTournament().getState() != null) {
                mIsTournamentActive = (data.getTournament().getState().toLowerCase().contains("underway"));
            }
        }
    }

    public void addUpcomingMatch(Match match, int index) {
        /*
            I need to make sure that both matches have participants whom have valid names in the
            participant map.  With multi-pool brackets there are playerids that don't match up with
            the participants and as such no name is retrieved.  *Reported to Challonge 10/11/15*
         */
        if (match.getState() != null)
        {
            String p1 = match.getPlayerOneId();
            String p2 = match.getPlayerTwoId();

            if (p1 != null || p2 != null) {
                if (mParticipants.get(p1) != null || mParticipants.get(p2) != null) {
                    if(match.getState().toLowerCase().equals("pending") || match.getState().toLowerCase().equals("open")) {
                        // Don't want to see the empty brackets from way in the future here
                        mUpcomingMatches.add(mMatches.get(index));
                        if (p1 != null) { addActivePlayer(p1); }
                        if (p2 != null) { addActivePlayer(p2); }
                    } else {
                        mValidMatches.add(mMatches.get(index));
                    }
                }
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

    public List<String> getActiveParticipants() {
        return mActiveParticipants;
    }

    public List<String> getAllParticipants() {
        return mAllParticipants;
    }

    public List<MatchWrapper> getValidMatches() {
        return mValidMatches;
    }

    public void wipe() {
        mAllParticipants.clear();
        mActiveParticipants.clear();
        mActiveParticipantSet.clear();
        mValidMatches.clear();
        mParticipants.clear();
        mMatches.clear();
        mUpcomingMatches.clear();
        mIsTournamentActive = false;
        mBracket = null;
        mContext = null;

    }
}
