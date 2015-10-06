package watch.fight.android.fightbrowser.Events;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import watch.fight.android.fightbrowser.R;

/**
 * Created by josh on 9/29/15.
 */
public class EventsFragment extends Fragment {
    private String TAG = EventsFragment.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private View mLoadingOverlay;
    EventsAdapter mAdapter;

    @Override
    public void onResume() {
        super.onResume();
//        setUILoading();
        setUIReady();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.events_fragment, container, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.events_recycler_view);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this.getActivity());
//        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this.getActivity(), 2);

        mLoadingOverlay = (View) v.findViewById(R.id.loading_container);
        mAdapter = new EventsAdapter(this.getActivity());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);

        return v;
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
