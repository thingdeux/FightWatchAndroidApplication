package watch.fight.android.fightbrowser.Brackets;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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
    private List<ParticipantWrapper> mParticipants;
    private List<MatchWrapper> mMatches;
    private Context mContext;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public Button mPlayerOne;
        public Button mPlayerTwo;

        public ViewHolder(View v) {
            super(v);
            mPlayerOne = (Button) v.findViewById(R.id.bracket_player_one);
            mPlayerTwo = (Button) v.findViewById(R.id.bracket_player_two);
        }
    }

    public ParticipantsAdapter(Context c, Bracket bracket,
                               List<ParticipantWrapper> participants, List<MatchWrapper> matches) {
        mContext = c;
        mBracket = bracket;
        mParticipants = participants;
        mMatches = matches;
    }

    @Override
    public ParticipantsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.bracket_participants_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Match match = mMatches.get(position).getMatch();
        holder.mPlayerOne.setText(match.getPlayerOneId());
        holder.mPlayerTwo.setText(match.getPlayerTwoId());


    }

    @Override
    public int getItemCount() { return mMatches.size(); }

}
