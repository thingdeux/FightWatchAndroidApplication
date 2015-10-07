package watch.fight.android.fightbrowser.Brackets;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import watch.fight.android.fightbrowser.Brackets.models.Match;
import watch.fight.android.fightbrowser.Brackets.models.MatchWrapper;
import watch.fight.android.fightbrowser.Brackets.models.Participant;
import watch.fight.android.fightbrowser.Events.models.Bracket;
import watch.fight.android.fightbrowser.R;

import static watch.fight.android.fightbrowser.Brackets.ParticipantsFragment.*;

/**
 * Created by josh on 10/5/15.
 */
public class ParticipantsAdapter extends RecyclerView.Adapter<ParticipantsAdapter.ViewHolder>{
    private Bracket mBracket;
    private HashMap<String, Participant> mParticipants;
    private List<String> mActiveParticipants;
    private List<MatchWrapper> mMatches;
    private List<MatchWrapper> mUpcomingMatches;
    private boolean mIsTournamentActive;
    private Context mContext;
    private int mFragmentType;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView mPlayerOne;
        public TextView mPlayerTwo;
        public TextView mVs;
        public TextView mPlayerName;

        public ViewHolder(View v, int fragmentType) {
            super(v);
            switch (fragmentType) {
                case PARTICIPANTS_FRAGMENT_WHOSLEFT:
                    mPlayerName = (TextView) v.findViewById(R.id.bracket_player_name);
                    break;
                default:
                    mPlayerOne = (TextView) v.findViewById(R.id.bracket_player_one);
                    mPlayerTwo = (TextView) v.findViewById(R.id.bracket_player_two);
                    mVs = (TextView) v.findViewById(R.id.vs_line);
                    break;
            }
        }
    }

    public ParticipantsAdapter(Context c, int fragmentType) {
        mContext = c;
        mFragmentType = fragmentType;
        ParticipantsHolder pHolder = ParticipantsHolder.getInstance(mContext);
        mBracket = pHolder.getBracket();
        mMatches = pHolder.getMatches();
        mParticipants = pHolder.getParticipants();
        mActiveParticipants = pHolder.getActiveParticipantIds();
        mUpcomingMatches = pHolder.getUpcomingMatches();
        mIsTournamentActive = pHolder.isTournamentActive();
    }

    @Override
    public ParticipantsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        switch (mFragmentType) {
            case PARTICIPANTS_FRAGMENT_BATTLELOG:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.bracket_participants_vs_item, parent, false);
                break;
            case PARTICIPANTS_FRAGMENT_WHOSLEFT:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.bracket_participants_item, parent, false);
                break;
            case PARTICIPANTS_FRAGMENT_UPCOMING:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.bracket_participants_vs_item, parent, false);
                break;
            default:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.bracket_participants_vs_item, parent, false);
        }
        return new ViewHolder(v, mFragmentType);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        switch (mFragmentType) {
            case PARTICIPANTS_FRAGMENT_BATTLELOG:
                bindBattleLog(holder, position);
                break;
            case PARTICIPANTS_FRAGMENT_WHOSLEFT:
                bindWhosLeft(holder, position);
                break;
            case PARTICIPANTS_FRAGMENT_UPCOMING:
                bindUpcoming(holder, position);
                break;
        }

    }

    public void bindBattleLog(final ViewHolder holder, final int position) {
        final Match match = mMatches.get(position).getMatch();
        final Participant p1 = mParticipants.get(match.getPlayerOneId());
        final Participant p2 = mParticipants.get(match.getPlayerTwoId());

        holder.mPlayerOne.setText((p1 == null) ? "??" : p1.getName());
        holder.mPlayerTwo.setText((p2 == null) ? "??" : p2.getName());
        if (match.getWinnerId() != null && match.getState() != null && match.getState().equals("complete")) {
            if (match.getWinnerId().equals(p1.getId())) {
                holder.mPlayerTwo.setTextColor(mContext.getResources().getColor(R.color.primary_dark_fgc));
            } else if (match.getWinnerId().equals(p2.getId())) {
                holder.mPlayerOne.setTextColor(mContext.getResources().getColor(R.color.primary_dark_fgc));
            }
        }
    }

    public void bindWhosLeft(final ViewHolder holder, final int position) {
        final String participantId = mActiveParticipants.get(position);
        Participant p = mParticipants.get(participantId);
        holder.mPlayerName.setText(p.getName());
    }

    public void bindUpcoming(final ViewHolder holder, final int position) {
        final Match match = mUpcomingMatches.get(position).getMatch();
        final Participant p1 = mParticipants.get(match.getPlayerOneId());
        final Participant p2 = mParticipants.get(match.getPlayerTwoId());

        holder.mPlayerOne.setText((p1 == null) ? "??" : p1.getName());
        holder.mPlayerTwo.setText((p2 == null) ? "??" : p2.getName());
    }

    @Override
    public int getItemCount() {
        switch (mFragmentType) {
            case PARTICIPANTS_FRAGMENT_BATTLELOG:
                return mMatches.size();
            case PARTICIPANTS_FRAGMENT_WHOSLEFT:
                return mActiveParticipants.size();
            case PARTICIPANTS_FRAGMENT_UPCOMING:
                return mUpcomingMatches.size();
            default:
                return 0;
        }

    }

}
