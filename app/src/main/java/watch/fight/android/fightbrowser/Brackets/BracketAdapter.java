package watch.fight.android.fightbrowser.Brackets;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import watch.fight.android.fightbrowser.Brackets.events.BracketSuggestEvent;
import watch.fight.android.fightbrowser.Config.events.PreferenceToggleEvent;
import watch.fight.android.fightbrowser.Events.models.Bracket;
import watch.fight.android.fightbrowser.Events.models.Event;
import watch.fight.android.fightbrowser.R;
import watch.fight.android.fightbrowser.Utils.Dialogs.BasicAlertDialog;


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
        public Button mVerifyButton;

        public ViewHolder(View v) {
            super(v);

            mBracketName = (TextView) v.findViewById(R.id.bracket_name);
            mBracketIsVerified = (TextView) v.findViewById(R.id.bracket_is_verified);
            mViewParticipants = (Button) v.findViewById(R.id.bracket_view_participants);
            mVerifyButton = (Button) v.findViewById(R.id.bracket_verify);
        }
    }

    public BracketAdapter(Context c, Event event) {
        mContext = c;
        mEvent = event;
        if (event != null && event.getStoredBrackets(c) != null) {
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


        if (bracket.getIsVerified()) {
            holder.mVerifyButton.setEnabled(false);
            holder.mVerifyButton.setVisibility(View.INVISIBLE);
        } else {
            int resourceID = (bracket.getIsUserAdded()) ?
                    R.string.bracket_suggest_button_name : R.string.bracket_plus_one_button_name;

            holder.mVerifyButton.setEnabled(true);
            holder.mVerifyButton.setVisibility(View.VISIBLE);
            holder.mVerifyButton.setText(resourceID);

            holder.mVerifyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int suggestionType = (bracket.getIsUserAdded()) ?
                            BracketSuggestEvent.NEW_BRACKET : BracketSuggestEvent.VERIFY_CURRENT_BRACKET;

                    EventBus.getDefault().post(new BracketSuggestEvent(mBrackets.get(position), suggestionType));
                }
            });
        }


        holder.mBracketName.setText(bracket.getBracketName());

        bindBracketVerified(holder, bracket);
        holder.mViewParticipants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(ParticipantsActivity.NewInstance(mContext, bracket.getId()));
            }
        });
    }

    public void refreshBracketContents() {
        if (mEvent != null && mEvent.getStoredBrackets(mContext) != null) {
            mBrackets = mEvent.getStoredBrackets(mContext);
            notifyDataSetChanged();
        }
    }

    public void bindBracketVerified(ViewHolder holder, final Bracket bracket) {
        Resources resources = mContext.getResources();

        if (bracket.getIsUserAdded()) {
            holder.mBracketIsVerified.setText(resources.getString(R.string.bracket_user_header));
        } else {
            holder.mBracketIsVerified.setText((bracket.getIsVerified()) ?
            resources.getString(R.string.bracket_verified_header) : resources.getString(R.string.bracket_community_header));
        }
    }

    @Override
    public int getItemCount() { return mBrackets.size(); }

}

