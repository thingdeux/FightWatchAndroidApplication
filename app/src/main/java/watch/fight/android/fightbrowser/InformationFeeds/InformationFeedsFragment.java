package watch.fight.android.fightbrowser.InformationFeeds;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import watch.fight.android.fightbrowser.Dashboard.DashboardAdapter;
import watch.fight.android.fightbrowser.Dashboard.DashboardBuilder;
import watch.fight.android.fightbrowser.Dashboard.DashboardEntry;
import watch.fight.android.fightbrowser.InformationFeeds.models.Story;
import watch.fight.android.fightbrowser.R;

/**
 * Created by josh on 9/21/15.
 */
public class InformationFeedsFragment extends Fragment {
    private String TAG = InformationFeedsFragment.class.getSimpleName();
    private RecyclerView mRecyclerView;
    InformationFeedsAdapter mAdapter;

    @Override
    public void onResume() {
        super.onResume();
//        new DashboardBuilder().execute(new DashboardBuilder.DashboardBuilderValues(getActivity(), mAdapter));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_information_feeds, container, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.information_feeds_recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this.getActivity());
        mAdapter = new InformationFeedsAdapter(this.getActivity());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);

        return v;
    }
}
