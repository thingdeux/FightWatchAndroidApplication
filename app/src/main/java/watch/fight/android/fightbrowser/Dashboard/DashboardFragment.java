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
import android.widget.RelativeLayout;

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
    private View mLoadingOverlay;
    DashboardAdapter mAdapter;

    @Override
    public void onResume() {
        super.onResume();
        new FetchFeeds.FetchStories(this, this.getActivity()).execute();
        setUILoading();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_dashboard, container, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.dashboard_recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this.getActivity());
        mLoadingOverlay = v.findViewById(R.id.loading_container);

        mAdapter = new DashboardAdapter(new DashboardEntry[0]);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);

        return v;
    }

    public void setUILoading() {
        Log.i(TAG, "DashboardFragment SetUILoading");
        if (mRecyclerView != null) {
            mRecyclerView.setVisibility(View.INVISIBLE);
            mLoadingOverlay.setVisibility(View.VISIBLE);
        }
    }

    public void setUIReady() {
        Log.i(TAG, "DashboardFragment SetUIReady");
        if (mRecyclerView != null) {
            mLoadingOverlay.setVisibility(View.INVISIBLE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }
}
