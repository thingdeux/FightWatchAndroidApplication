package watch.fight.android.fightbrowser.Brackets;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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
        public Button mViewParticipants;

        public ViewHolder(View v) {
            super(v);
            mBracketName = (TextView) v.findViewById(R.id.bracket_name);
            mViewParticipants = (Button) v.findViewById(R.id.bracket_view_participants);
        }

    }

    public BracketAdapter(Context c, Event event) {
        mContext = c;
        mEvent = event;
        mBrackets = event.getStoredBrackets(c);
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

    }

    @Override
    public int getItemCount() { return mBrackets.size(); }

}

