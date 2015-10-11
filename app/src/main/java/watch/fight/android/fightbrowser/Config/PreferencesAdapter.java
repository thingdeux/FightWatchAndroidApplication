package watch.fight.android.fightbrowser.Config;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import watch.fight.android.fightbrowser.Config.models.DB.GameDB;
import watch.fight.android.fightbrowser.Config.models.GameConfig;
import watch.fight.android.fightbrowser.InformationFeeds.models.DB.FeedDB;
import watch.fight.android.fightbrowser.InformationFeeds.models.Feed;
import watch.fight.android.fightbrowser.R;
import watch.fight.android.fightbrowser.Utils.SharedPrefManager;

import static watch.fight.android.fightbrowser.Config.PreferencesFragment.*;

/**
 * Created by josh on 10/10/15.
 */
public class PreferencesAdapter extends RecyclerView.Adapter<PreferencesAdapter.ViewHolder> {
    private Context mContext;
    private int mFragmentType;
    private List<GameConfig> mGames;
    private List<Feed> mFeeds;
    private List<SharedPrefManager> mSharedPrefManager;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView mPrefLabel;
        public Switch mToggleSwitch;

        public ViewHolder(View v, int fragmentType) {
            super(v);
            mPrefLabel = (TextView) v.findViewById(R.id.preferences_item);
            mToggleSwitch = (Switch) v.findViewById(R.id.preferences_toggle_switch);
        }
    }

        public PreferencesAdapter(Context c, int fragmentType) {
            mContext = c;
            mFragmentType = fragmentType;

            switch (fragmentType) {
                case PREFERENCES_FRAGMENT_FEEDS:
                    mFeeds = FeedDB.getInstance(mContext).getAllFeeds();
                    break;
                case PREFERENCES_FRAGMENT_GAMES:
                    mGames = GameDB.getInstance(mContext).getAllGames();
                    break;
                case PREFERENCES_FRAGMENT_GENERAL:
                    mSharedPrefManager = new ArrayList<>();
                    mSharedPrefManager.add(new SharedPrefManager(mContext, SharedPrefManager.WEB_VIEW_PREF));
                    mSharedPrefManager.add(new SharedPrefManager(mContext, SharedPrefManager.DARK_MODE));
                    break;
            }
        }

        @Override
        public PreferencesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v;
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.preferences_item, parent, false);
            return new ViewHolder(v, mFragmentType);

        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            switch (mFragmentType) {
                case PREFERENCES_FRAGMENT_FEEDS:
                    bindFeed(holder, position);
                    break;
                case PREFERENCES_FRAGMENT_GAMES:
                    bindGame(holder, position);
                    break;
                case PREFERENCES_FRAGMENT_GENERAL:
                    SharedPrefManager sharedPref = mSharedPrefManager.get(position);
                    if (sharedPref != null) {
                        holder.mPrefLabel.setText(sharedPref.toString());
                        holder.mToggleSwitch.setChecked(sharedPref.getValue());
                    }
                    break;
            }
        }

    public void bindFeed(final ViewHolder holder, final int position) {
        final Feed feed = mFeeds.get(position);
        if (feed != null && feed.getName() != null) {
            holder.mPrefLabel.setText(feed.getName());
            holder.mToggleSwitch.setChecked(!feed.getIsFiltered());
            holder.mToggleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    FeedDB.getInstance(mContext).setFiltered(feed.getId(), !isChecked);
                }
            });
        }
    }

        public void bindGame(final ViewHolder holder, final int position) {
            final GameConfig game = mGames.get(position);
            if (game != null && game.getGameName() != null) {
                holder.mPrefLabel.setText(game.getGameName());
                holder.mToggleSwitch.setChecked(!game.getIsFiltered());
                holder.mToggleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        GameDB.getInstance(mContext).setFiltered(game.getId(), !isChecked);
                    }
                });
            }
        }



        @Override
        public int getItemCount() {
            switch (mFragmentType) {
                case PREFERENCES_FRAGMENT_FEEDS:
                    if (mFeeds != null) {
                        return mFeeds.size();
                    }
                case PREFERENCES_FRAGMENT_GENERAL:
                    return mSharedPrefManager.size();
                case PREFERENCES_FRAGMENT_GAMES:
                    if (mGames != null) {
                        return mGames.size();
                    }
            }
            return 0;
        }
}
