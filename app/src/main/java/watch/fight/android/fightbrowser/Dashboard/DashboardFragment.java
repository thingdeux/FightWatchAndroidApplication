package watch.fight.android.fightbrowser.Dashboard;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import watch.fight.android.fightbrowser.Config.ConfigFetcher;
import watch.fight.android.fightbrowser.Config.models.Config;
import watch.fight.android.fightbrowser.InformationFeeds.FetchFeeds;
import watch.fight.android.fightbrowser.InformationFeeds.models.Story;
import watch.fight.android.fightbrowser.R;
import watch.fight.android.fightbrowser.Utils.DateParser;
import watch.fight.android.fightbrowser.Utils.NetworkUtils;
import watch.fight.android.fightbrowser.Utils.SharedPreferences;

/**
 * Created by josh on 9/15/15.
 */
public class DashboardFragment extends Fragment {
    private String TAG = DashboardFragment.class.getSimpleName();
    private RecyclerView mRecyclerView;
    DashboardAdapter mAdapter;

    @Override
    public void onResume() {
        super.onResume();
        new DashboardBuilder().execute(new DashboardBuilder.DashboardBuilderValues(getActivity(), mAdapter));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_dashboard, container, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.dashboard_recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this.getActivity());
        mAdapter = new DashboardAdapter(new DashboardEntry[1]);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);

        return v;
    }
}
