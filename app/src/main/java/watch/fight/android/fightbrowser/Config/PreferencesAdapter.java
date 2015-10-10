package watch.fight.android.fightbrowser.Config;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import watch.fight.android.fightbrowser.R;

import static watch.fight.android.fightbrowser.Config.PreferencesFragment.*;

/**
 * Created by josh on 10/10/15.
 */
public class PreferencesAdapter extends RecyclerView.Adapter<PreferencesAdapter.ViewHolder> {
    private Context mContext;
    private int mFragmentType;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView mPlayerOne;

        public ViewHolder(View v, int fragmentType) {
            super(v);
            switch (fragmentType) {
                case PREFERENCES_FRAGMENT_FEEDS:
                    break;
                case PREFERENCES_FRAGMENT_GAMES:
                    break;
                case PREFERENCES_FRAGMENT_GENERAL:
                    break;
            }
        }
    }

        public PreferencesAdapter(Context c, int fragmentType) {
            mContext = c;
            mFragmentType = fragmentType;
        }

        @Override
        public PreferencesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v;
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.preferences_item, parent, false);
            return new ViewHolder(v, mFragmentType);

        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
//            switch (mFragmentType) {
//                case PARTICIPANTS_FRAGMENT_BATTLELOG:
//                    bindBattleLog(holder, position);
//                    break;
//                case PARTICIPANTS_FRAGMENT_WHOSLEFT:
//                    bindWhosLeft(holder, position);
//                    break;
//                case PARTICIPANTS_FRAGMENT_UPCOMING:
//                    bindUpcoming(holder, position);
//                    break;
//                case PARTICIPANTS_FRAGMENT_ROSTER:
//                    bindRoster(holder, position);
//                    break;
//            }
            holder.mPlayerOne.setText("Yup");

        }

        @Override
        public int getItemCount() {
            switch (mFragmentType) {
                case PREFERENCES_FRAGMENT_FEEDS:
//                    return mMatches.size();
                    return 0;
                case PREFERENCES_FRAGMENT_GENERAL:
                    return 0;
//                    return mActiveParticipants.size();
                case PREFERENCES_FRAGMENT_GAMES:
                    return 0;
//                    return mUpcomingMatches.size();
            }
            return 0;
        }
}
