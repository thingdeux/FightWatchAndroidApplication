package watch.fight.android.fightbrowser.Dashboard;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import watch.fight.android.fightbrowser.R;

/**
 * Created by josh on 9/15/15.
 */
public class DashboardFragment extends Fragment {
    private RecyclerView mRecyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dashboard, container, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.dashboard_recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this.getActivity());
        DashboardAdapter mAdapter = new DashboardAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);
        return v;
    }
}
