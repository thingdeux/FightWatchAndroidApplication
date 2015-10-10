package watch.fight.android.fightbrowser.Brackets;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import watch.fight.android.fightbrowser.R;

/**
 * Created by josh on 10/5/15.
 */
public class ParticipantsFragment extends Fragment {
    private String TAG = ParticipantsFragment.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private ParticipantsAdapter mAdapter;
    private static final String PARTICIPANTS_FRAGMENT_TYPE = "watch.fight.android.fightbrowser.participants.fragment_type";
    public static final int PARTICIPANTS_FRAGMENT_WHOSLEFT = 1;
    public static final int PARTICIPANTS_FRAGMENT_BATTLELOG = 2;
    public static final int PARTICIPANTS_FRAGMENT_UPCOMING = 3;
    public static final int PARTICIPANTS_FRAGMENT_ROSTER = 4;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        Bundle args = getArguments();
        int fragmentType = 0;

        if (args != null) {
            fragmentType = args.getInt(PARTICIPANTS_FRAGMENT_TYPE, 0);
        } else {
            Log.v(TAG, "No arguments found in intent");
        }

        View v = inflater.inflate(R.layout.bracket_participants_fragment, container, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.participants_recycler_view);
        TextView footer = (TextView) v.findViewById(R.id.bracket_footer_message);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this.getActivity());
        mAdapter = new ParticipantsAdapter(this.getActivity(), fragmentType, footer);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public static ParticipantsFragment newInstance(int type) {
        ParticipantsFragment participantsFragment = new ParticipantsFragment();
        Bundle args = new Bundle();
        args.putInt(PARTICIPANTS_FRAGMENT_TYPE, type);
        participantsFragment.setArguments(args);

        return participantsFragment;
    }

    public static ParticipantsFragment newInstance(final String name) {
        int type = 0;
        switch (name.toLowerCase()) {
            case "who's left":
                type = PARTICIPANTS_FRAGMENT_WHOSLEFT;
                break;
            case "upcoming matches":
                type = PARTICIPANTS_FRAGMENT_UPCOMING;
                break;
            case "battlelog":
                type = PARTICIPANTS_FRAGMENT_BATTLELOG;
                break;
            case "player roster":
                type = PARTICIPANTS_FRAGMENT_ROSTER;
                break;
        }

        ParticipantsFragment participantsFragment = new ParticipantsFragment();
        Bundle args = new Bundle();
        args.putInt(PARTICIPANTS_FRAGMENT_TYPE, type);
        participantsFragment.setArguments(args);

        return participantsFragment;
    }


}
