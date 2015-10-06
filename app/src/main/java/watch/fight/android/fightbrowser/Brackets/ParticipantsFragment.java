package watch.fight.android.fightbrowser.Brackets;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import watch.fight.android.fightbrowser.R;

/**
 * Created by josh on 10/5/15.
 */
public class ParticipantsFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private ParticipantsAdapter mAdapter;
    private static final String PARTICIPANTS_FRAGMENT_TYPE = "watch.fight.android.fightbrowser.participants.fragment_type";
    public static int PARTICIPANTS_FRAGMENT_WHOSLEFT = 1;
    public static int PARTICIPANTS_FRAGMENT_BATTLELOG = 2;
    public static int PARTICIPANTS_FRAGMENT_UPCOMING = 3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bracket_participants_fragment, container, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.participants_recycler_view);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this.getActivity());
        mAdapter = new ParticipantsAdapter(this.getActivity());
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


}
