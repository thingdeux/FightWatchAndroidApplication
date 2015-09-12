package watch.fight.android.fightbrowser;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import watch.fight.android.fightbrowser.Twitch.TwitchHttpLoader;
import watch.fight.android.fightbrowser.Twitch.TwitchStream;
import watch.fight.android.fightbrowser.Twitch.TwitchStreamHolder;
import watch.fight.android.fightbrowser.Twitch.TwitchStreamListAdapter;

/**
 * Created by josh on 9/12/15.
 */
public class BrowserFragment extends Fragment {
    private RecyclerView mRecylerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private boolean haveStreamsLoaded = false;
    private static int RECYCLER_VIEW_GRID_MAX = 2;
    private View mLoadingTextView;
    private TwitchHttpLoader mTwitchLoader;

    private static String TAG = BrowserFragment.TAG;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_twitch_browser, container, false);

        mLoadingTextView = (View) v.findViewById(R.id.twitch_loading_container);
        mRecylerView = (RecyclerView) v.findViewById(R.id.browser_recycler_view);
        mLayoutManager = new GridLayoutManager(this.getActivity(), RECYCLER_VIEW_GRID_MAX);

        mAdapter = new TwitchStreamListAdapter();
        mRecylerView.setAdapter(mAdapter);
        mRecylerView.setLayoutManager(mLayoutManager);
        setUILoading();
        loadTwitchStream();
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!haveStreamsLoaded) {
            setUILoading();
            loadTwitchStream();
        }
    }

    public void loadTwitchStream() {
        mTwitchLoader = new TwitchHttpLoader();
        mTwitchLoader.setCustomObjectListener(new TwitchHttpLoader.IHttpResponse() {
            @Override
            public void onReceivedResponse(String result) {
                Log.d(TAG, "Received Twitch Response: " + result);
                // Receive the response from the Twitch API, populate the recyclerviewadapter
                Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
                TwitchStream ts = gson.fromJson(result, TwitchStream.class);
                TwitchStreamHolder.getInstance().setFeaturedStreams(ts.getFeatured());
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.notifyDataSetChanged();
                        setUIReady();
                    }
                });
            }
        });

        mTwitchLoader.getTwitchData("https://api.twitch.tv/kraken/streams/featured");


    }

    public void setUILoading() {
        if (mRecylerView != null) {
            mRecylerView.setVisibility(View.INVISIBLE);
            mLoadingTextView.setVisibility(View.VISIBLE);
        }
    }

    public void setUIReady() {
        if (mRecylerView != null) {
            mLoadingTextView.setVisibility(View.INVISIBLE);
            mRecylerView.setVisibility(View.VISIBLE);
        }
    }
}
