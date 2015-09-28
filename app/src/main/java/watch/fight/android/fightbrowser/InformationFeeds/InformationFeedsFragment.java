package watch.fight.android.fightbrowser.InformationFeeds;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import watch.fight.android.fightbrowser.R;

/**
 * Created by josh on 9/21/15.
 */
public class InformationFeedsFragment extends Fragment {
    private String TAG = InformationFeedsFragment.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private RelativeLayout mLoadingOverlay;
    InformationFeedsAdapter mAdapter;

    @Override
    public void onResume() {
        super.onResume();
        setUILoading();
        new FetchFeeds.FetchStories(this, this.getActivity()).execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_information_feeds, container, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.information_feeds_recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this.getActivity());
        mLoadingOverlay = (RelativeLayout) v.findViewById(R.id.loading_container);
        mAdapter = new InformationFeedsAdapter(this.getActivity());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);

        return v;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                setUILoading();
                new FetchFeeds.FetchStories(this.getActivity(), mAdapter, this, true).execute();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setUILoading() {
        if (mRecyclerView != null) {
            mRecyclerView.setVisibility(View.INVISIBLE);
            mLoadingOverlay.setVisibility(View.VISIBLE);
        }
    }

    public void setUIReady() {
        if (mRecyclerView != null) {
            mLoadingOverlay.setVisibility(View.INVISIBLE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }
}

