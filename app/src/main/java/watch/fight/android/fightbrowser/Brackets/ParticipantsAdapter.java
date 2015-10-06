package watch.fight.android.fightbrowser.Brackets;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import watch.fight.android.fightbrowser.Brackets.models.Match;
import watch.fight.android.fightbrowser.Brackets.models.MatchWrapper;
import watch.fight.android.fightbrowser.Brackets.models.Participant;
import watch.fight.android.fightbrowser.Brackets.models.ParticipantWrapper;
import watch.fight.android.fightbrowser.Events.models.Bracket;
import watch.fight.android.fightbrowser.Events.models.Event;
import watch.fight.android.fightbrowser.R;

/**
 * Created by josh on 10/5/15.
 */
public class ParticipantsAdapter extends RecyclerView.Adapter<ParticipantsAdapter.ViewHolder>{
    private Bracket mBracket;
    private HashMap<String, Participant> mParticipants = new HashMap<>();
    private List<MatchWrapper> mMatches;
    private Context mContext;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView mPlayerOne;
        public TextView mPlayerTwo;
        public TextView mVs;

        public ViewHolder(View v) {
            super(v);
            mPlayerOne = (TextView) v.findViewById(R.id.bracket_player_one);
            mPlayerTwo = (TextView) v.findViewById(R.id.bracket_player_two);
            mVs = (TextView) v.findViewById(R.id.vs_line);
        }
    }

    public ParticipantsAdapter(Context c, Bracket bracket,
                               List<ParticipantWrapper> participants, List<MatchWrapper> matches) {
        mContext = c;
        mBracket = bracket;
        mMatches = matches;
        Log.i("JJDEBUG", "Received Bracket: " + bracket.getBracketUrl());
        BuildParticipantMap(participants);
    }

    public void BuildParticipantMap(List<ParticipantWrapper> participants) {
        if (participants != null) {
            Participant p = new Participant();
            for (int i = 0; i < participants.size(); i++) {
                p = participants.get(i).getParticipant();
                mParticipants.put(p.getId().toString(), p);
            }
        }

    }

    @Override
    public ParticipantsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.bracket_participants_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Match match = mMatches.get(position).getMatch();
        final Participant p1 = mParticipants.get(match.getPlayerOneId());
        final Participant p2 = mParticipants.get(match.getPlayerTwoId());

        holder.mPlayerOne.setText(p1.getName());
        holder.mPlayerTwo.setText(p2.getName());
    }

    @Override
    public int getItemCount() { return mMatches.size(); }

}
