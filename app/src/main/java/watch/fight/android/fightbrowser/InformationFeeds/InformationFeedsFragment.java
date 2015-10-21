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

import java.util.List;

import de.greenrobot.event.EventBus;
import watch.fight.android.fightbrowser.InformationFeeds.events.InformationFeedsUIStateEvent;
import watch.fight.android.fightbrowser.InformationFeeds.models.DB.StoryDB;
import watch.fight.android.fightbrowser.InformationFeeds.models.DB.StoryTrackerDB;
import watch.fight.android.fightbrowser.InformationFeeds.models.Story;
import watch.fight.android.fightbrowser.R;

/**
 * Created by josh on 9/21/15.
 */
public class InformationFeedsFragment extends Fragment {
    private String TAG = InformationFeedsFragment.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private View mLoadingOverlay;
    private View mNoNewFeeds;
    InformationFeedsAdapter mAdapter;

    @Override
    public void onResume() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        super.onResume();
        setUILoading();
        if (mAdapter != null) {
            new FetchFeeds.FetchStories(this.getActivity(), mAdapter, this, true).execute();
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
        View v = inflater.inflate(R.layout.information_feeds_fragment, container, false);
        mNoNewFeeds = v.findViewById(R.id.no_feeds_overlay);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.information_feeds_recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this.getActivity());
        mLoadingOverlay = v.findViewById(R.id.loading_container);
        mAdapter = new InformationFeedsAdapter(this.getActivity());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);

        return v;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.participants_action_refresh:
                setUILoading();
                new FetchFeeds.FetchStories(this.getActivity(), mAdapter, this, true).execute();
                return true;
            case R.id.action_mark_all_as_read:
                // Set all records to marked as read - refresh adapter
                Log.d(TAG, "Attempting to mark all stories as read");
                List<Story> stories = StoryDB.getInstance(this.getActivity()).getAllUnfilteredStories();
                StoryTrackerDB.getInstance(this.getActivity()).addStoryTrackers(stories);
                mAdapter.clearStories();
                mAdapter.notifyDataSetChanged();
                return true;
            case R.id.action_show_all:
                new FetchFeeds.FetchStories(this.getActivity(), mAdapter, this, false).execute();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setUILoading() {
        if (mRecyclerView != null) {
            mNoNewFeeds.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.INVISIBLE);
            mLoadingOverlay.setVisibility(View.VISIBLE);
        }
    }

    public void setUIReady() {
        if (mRecyclerView != null) {
            mNoNewFeeds.setVisibility(View.GONE);
            mLoadingOverlay.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    public void setUINoNewFeeds() {
        if (mRecyclerView != null) {
            mNoNewFeeds.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.INVISIBLE);
            mLoadingOverlay.setVisibility(View.GONE);
        }
    }

    public void onEventMainThread(InformationFeedsUIStateEvent event) {
        switch (event.stateToSet) {
            case InformationFeedsUIStateEvent.NO_NEW_FEEDS:
                mNoNewFeeds.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.INVISIBLE);
                break;
            case InformationFeedsUIStateEvent.LOADING:
                setUILoading();
                break;
            case InformationFeedsUIStateEvent.NORMAL:
                setUIReady();
                break;

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

}

