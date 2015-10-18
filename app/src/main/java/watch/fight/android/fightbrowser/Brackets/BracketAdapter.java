package watch.fight.android.fightbrowser.Brackets;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import watch.fight.android.fightbrowser.Events.models.Bracket;
import watch.fight.android.fightbrowser.Events.models.Event;
import watch.fight.android.fightbrowser.R;


/**
 * Created by josh on 9/16/15.
 */
public class BracketAdapter extends RecyclerView.Adapter<BracketAdapter.ViewHolder>{
    private Event mEvent;
    private List<Bracket> mBrackets;
    private Context mContext;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView mBracketName;
        public TextView mBracketIsVerified;
        public Button mViewParticipants;

        public ViewHolder(View v) {
            super(v);

            mBracketName = (TextView) v.findViewById(R.id.bracket_name);
            mBracketIsVerified = (TextView) v.findViewById(R.id.bracket_is_verified);
            mViewParticipants = (Button) v.findViewById(R.id.bracket_view_participants);
        }
    }

    public BracketAdapter(Context c, Event event) {
        mContext = c;
        mEvent = event;
        if (event.getStoredBrackets(c) != null) {
            mBrackets = event.getStoredBrackets(c);
        } else {
            mBrackets = new ArrayList<>();
        }
    }

    @Override
    public BracketAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.bracket_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Bracket bracket = mBrackets.get(position);

        holder.mBracketName.setText(bracket.getBracketName());
        // TODO : REPLACE WITH LOCALIZED VALUES
        holder.mBracketIsVerified.setText(
                (bracket.getIsVerified()) ? "Verified Bracket" : "Community Suggested Bracket"
        );
        holder.mViewParticipants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(ParticipantsActivity.NewInstance(mContext, bracket.getId()));
            }
        });
    }

    @Override
    public int getItemCount() { return mBrackets.size(); }

}

