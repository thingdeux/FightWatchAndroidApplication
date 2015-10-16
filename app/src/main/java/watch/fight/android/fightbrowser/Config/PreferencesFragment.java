package watch.fight.android.fightbrowser.Config;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.greenrobot.event.EventBus;
import watch.fight.android.fightbrowser.Config.events.PreferenceToggleEvent;
import watch.fight.android.fightbrowser.R;

/**
 * Created by josh on 10/10/15.
 */
public class PreferencesFragment extends Fragment {
    private String TAG = PreferencesFragment.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private PreferencesAdapter mAdapter;
    private static final String PREFERENCES_FRAGMENT_TYPE = "watch.fight.android.fightbrowser.preferences.fragment_type";
    public static final int PREFERENCES_FRAGMENT_GENERAL = 1;
    public static final int PREFERENCES_FRAGMENT_GAMES = 2;
    public static final int PREFERENCES_FRAGMENT_FEEDS = 3;

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

    }

    @Override
    public void onStop() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onPause() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        super.onPause();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        Bundle args = getArguments();
        int fragmentType = 0;

        if (args != null) {
            fragmentType = args.getInt(PREFERENCES_FRAGMENT_TYPE, 0);
        } else {
            Log.v(TAG, "No arguments found in preferences intent");
        }

        View v = inflater.inflate(R.layout.single_recycler_no_loading, container, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.recycler_one);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this.getActivity());

        mAdapter = new PreferencesAdapter(this.getActivity(), fragmentType);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);

        return v;
    }

    public static PreferencesFragment newInstance(final String name) {
        int type = 0;
        switch (name.toLowerCase()) {
            case "general":
                type = PREFERENCES_FRAGMENT_GENERAL;
                break;
            case "games":
                type = PREFERENCES_FRAGMENT_GAMES;
                break;
            case "feeds":
                type = PREFERENCES_FRAGMENT_FEEDS;
                break;
        }

        PreferencesFragment preferencesFragment = new PreferencesFragment();
        Bundle args = new Bundle();
        args.putInt(PREFERENCES_FRAGMENT_TYPE, type);
        preferencesFragment.setArguments(args);

        return preferencesFragment;
    }

    public void onEvent(PreferenceToggleEvent event) {
        Log.d("onEventReceive", "Received Event: " + event.toString());
        mAdapter.toggleSwitchAndReOrder();
        mAdapter.notifyDataSetChanged();
    }

    // Text bind to switch toggle w/ boolean
    // onToggle
    // onSlide
    // Type - Toggle / Slide / Delete
}
