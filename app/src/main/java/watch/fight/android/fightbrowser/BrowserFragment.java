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

    private static final String TAG = BrowserFragment.TAG;

    public static final String BROWSER_FRAGMENT_TYPE = "watch.fight.android.fightbrowser.fragment_type";
    public static final String BROWSER_FRAGMENT_GAME = "watch.fight.android.fightbrowser.fragment_type";
    public static final int BROWSER_FRAGMENT_FEATURED_TYPE = 1;
    public static final int BROWSER_FRAGMENT_GAME_SPECIFIC_TYPE = 2;
    public static final int BROWSER_FRAGMENT_POPULAR_TYPE = 3;


    public static BrowserFragment newInstance(int type, String gameName) {
        BrowserFragment browserFragment = new BrowserFragment();
        Bundle args = new Bundle();
        args.putInt(BROWSER_FRAGMENT_TYPE, type);
        args.putString(BROWSER_FRAGMENT_GAME, gameName);
        browserFragment.setArguments(args);

        return browserFragment;
    }

    public static BrowserFragment newInstance(int type) {
        BrowserFragment browserFragment = new BrowserFragment();
        Bundle args = new Bundle();
        args.putInt(BROWSER_FRAGMENT_TYPE, type);
        browserFragment.setArguments(args);

        return browserFragment;
    }

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
        if (savedInstanceState != null) {
            int fragment_type = savedInstanceState.getInt(BROWSER_FRAGMENT_TYPE);
            String gameName = savedInstanceState.getString(BROWSER_FRAGMENT_GAME);

            if (fragment_type > 0) {
                switch (fragment_type) {
                    case BROWSER_FRAGMENT_FEATURED_TYPE:
                        loadTwitchStream("https://api.twitch.tv/kraken/streams/featured?limit=30");
                        break;
                    case BROWSER_FRAGMENT_POPULAR_TYPE:
                        loadTwitchStream("https://api.twitch.tv/kraken/streams/featured");
                        break;
                    case BROWSER_FRAGMENT_GAME_SPECIFIC_TYPE:
                        loadTwitchStream("https://api.twitch.tv/kraken/streams/featured");
                        break;
                    default:
                        break;
                }

            }
        } else {
            loadTwitchStream("https://api.twitch.tv/kraken/streams/featured?limit=30");
        }

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!haveStreamsLoaded) {
            setUILoading();
            loadTwitchStream("https://api.twitch.tv/kraken/streams/featured?limit=30");
        }
    }

    public void loadTwitchStream(String url) {
        // TODO : Catch no response and set Error UI State
        // TODO : Catch No Results and set Empty Streams UI State
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

        mTwitchLoader.getTwitchData(url);


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
