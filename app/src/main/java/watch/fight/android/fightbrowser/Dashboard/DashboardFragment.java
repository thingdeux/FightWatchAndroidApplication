package watch.fight.android.fightbrowser.Dashboard;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.greenrobot.event.EventBus;
import watch.fight.android.fightbrowser.Config.ConfigFetcher;
import watch.fight.android.fightbrowser.Dashboard.events.DashboardUIStateEvent;
import watch.fight.android.fightbrowser.InformationFeeds.FetchFeeds;
import watch.fight.android.fightbrowser.R;

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
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        new ConfigFetcher(this.getActivity(), this).execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.dashboard_fragment, container, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.dashboard_recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this.getActivity());
        mLoadingOverlay = v.findViewById(R.id.loading_container);

        mAdapter = new DashboardAdapter(new DashboardEntry[0]);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);

        return v;
    }

    public void fetchFeeds() {
        new FetchFeeds.FetchStories(this, this.getActivity()).execute();
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

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

    }

    @Override
    public void onStop() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        super.onStop();
    }

    public void onEventMainThread(DashboardUIStateEvent event) {
        if (event.uiState == DashboardUIStateEvent.LOADING) {
            setUILoading();
        } else {
            setUIReady();
        }
    }

}
