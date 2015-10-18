package watch.fight.android.fightbrowser.Config;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
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
    private boolean isSwitchShowing = true;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView mPrefLabel;
        public Switch mToggleSwitch;
        public ImageButton mUpArrow;
        public ImageButton mDownArrow;
        public View mArrowContainer;

        public ViewHolder(View v) {
            super(v);
            mArrowContainer = v.findViewById(R.id.preferences_arrow_container);
            mPrefLabel = (TextView) v.findViewById(R.id.preferences_item);
            mToggleSwitch = (Switch) v.findViewById(R.id.preferences_toggle_switch);
            mUpArrow = (ImageButton) v.findViewById(R.id.preferences_up_arrow);
            mDownArrow = (ImageButton) v.findViewById(R.id.preferences_down_arrow);
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
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        switch (mFragmentType) {
            case PREFERENCES_FRAGMENT_FEEDS:
                bindFeed(holder, position);
                break;
            case PREFERENCES_FRAGMENT_GAMES:
                checkSwitchArrowAnimation(holder, position);
                bindGame(holder, position);
                break;
            case PREFERENCES_FRAGMENT_GENERAL:
                bindGeneral(holder, position);
                break;
        }
    }

    public void bindGeneral(final ViewHolder holder, final int position) {
        final SharedPrefManager sharedPref = mSharedPrefManager.get(position);
        if (sharedPref != null) {
            Log.i("PrefCheckAssigner", sharedPref.toString() + ": " + sharedPref.getValue());
            holder.mPrefLabel.setText(sharedPref.toString());
            holder.mToggleSwitch.setChecked(sharedPref.getValue());
            holder.mToggleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    sharedPref.setValue(isChecked);
                }
            });
        }
    }

    public void bindFeed(final ViewHolder holder, final int position) {
        holder.mToggleSwitch.setOnCheckedChangeListener(null);

        final Feed feed = mFeeds.get(position);
        if (feed != null && feed.getName() != null) {
            holder.mPrefLabel.setText(feed.getName());
            holder.mToggleSwitch.setChecked(!feed.getIsFiltered());
            holder.mToggleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    FeedDB.getInstance(mContext).setFiltered(feed.getId(), !isChecked);
                    // Because I'm not refreshing the dataset on button change, update the data used by the adapter.
                    feed.setIsFiltered(!isChecked);
                }
            });
        }
    }

    public void bindGame(final ViewHolder holder, final int position) {
        holder.mToggleSwitch.setOnCheckedChangeListener(null);

        final GameConfig game = mGames.get(position);
        if (game != null && game.getGameName() != null) {
            holder.mPrefLabel.setText(game.getGameName());
            holder.mToggleSwitch.setChecked(!game.getIsFiltered());
            holder.mToggleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    GameDB.getInstance(mContext).setFiltered(game.getId(), !isChecked);
                    // Because I'm not refreshing the dataset on button change, update the data used by the adapter.
                    game.setIsFiltered(!isChecked);
                }
            });

        }
        bindArrow(holder.mUpArrow, holder, true);
        bindArrow(holder.mDownArrow, holder, false);
    }

    public void bindArrow(ImageButton button, final ViewHolder holder, final boolean isUpArrow) {

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int position = holder.getAdapterPosition();
                final GameConfig game = mGames.get(position);
                if (isUpArrow) {
                    if (position > 0 && game.getId() != null) {
                        GameConfig aboveGame = mGames.get(position - 1);
                        int aboveOrdinal = aboveGame.getOrdinal();
                        GameDB.getInstance(mContext).updateOrdinal(game.getId(), aboveGame.getId(), true);

                        game.setOrdinal(aboveOrdinal);
                        aboveGame.setOrdinal(position);
                        Collections.swap(mGames, position, position - 1);
                        notifyItemMoved(position, position - 1);
                    }
                } else {
                    if (position >= 0 && game.getId() != null && position < getItemCount() - 1) {
                        GameConfig belowGame = mGames.get(position + 1);
                        int belowOrdinal = belowGame.getOrdinal();
                        GameDB.getInstance(mContext).updateOrdinal(game.getId(), belowGame.getId(), false);

                        belowGame.setOrdinal(game.getOrdinal());
                        game.setOrdinal(belowOrdinal);
                        Collections.swap(mGames, position, position + 1);
                        notifyItemMoved(position, position + 1);
                    }
                }
            }
        });
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

    public void toggleSwitchAndReOrder() {
        isSwitchShowing = !isSwitchShowing;
        // Workaround due to notifydatasetchanged on the adapter not firing bind on all objects each time.
        mGames = GameDB.getInstance(mContext).getAllGames();
        for (int i = 0; i < getItemCount(); i++) {
            this.notifyItemChanged(i);
        }
    }

    private void checkSwitchArrowAnimation(final ViewHolder holder, final int position) {
        if (isSwitchShowing) {
            if (holder.mToggleSwitch.getVisibility() != View.VISIBLE) {
                holder.mArrowContainer.setVisibility(View.GONE);
                holder.mToggleSwitch.setVisibility(View.VISIBLE);
            }
        } else {
            if (holder.mToggleSwitch.getVisibility() != View.GONE) {
                holder.mToggleSwitch.setVisibility(View.GONE);
                holder.mArrowContainer.setVisibility(View.VISIBLE);
            }
        }
    }

//    private void animateArrows(final ViewHolder holder, final int position) {
//        int animationDelay = (position < getItemCount()) ? 1000 + (position*25) : 500;
//        if (!isSwitchShowing) {
//            if (holder.mArrowContainer.getVisibility() != View.VISIBLE) {
//                holder.mArrowContainer.setAlpha(0f);
//                holder.mArrowContainer.setVisibility(View.VISIBLE);
//                holder.mArrowContainer.animate()
//                        .alpha(100f)
//                        .setDuration(animationDelay);
//            }
//        } else {
//            if (holder.mArrowContainer.getVisibility() != View.GONE) {
//                holder.mArrowContainer.animate()
//                        .alpha(0f)
//                        .setDuration(animationDelay)
//                        .setListener(new AnimatorListenerAdapter() {
//                            @Override
//                            public void onAnimationEnd(Animator animation) {
//                                super.onAnimationEnd(animation);
//                                holder.mArrowContainer.setVisibility(View.GONE);
//                            }
//                        })
//                ;
//            }
//
//        }
//    }
}
