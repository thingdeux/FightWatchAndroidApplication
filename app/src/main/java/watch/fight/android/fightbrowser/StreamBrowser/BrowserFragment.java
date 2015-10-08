package watch.fight.android.fightbrowser.StreamBrowser;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.UUID;

import watch.fight.android.fightbrowser.R;
import watch.fight.android.fightbrowser.StreamBrowser.Twitch.TwitchHttpLoader;
import watch.fight.android.fightbrowser.StreamBrowser.Twitch.models.TwitchStream;
import watch.fight.android.fightbrowser.StreamBrowser.Twitch.TwitchStreamHolder;
import watch.fight.android.fightbrowser.StreamBrowser.Twitch.TwitchStreamListAdapter;

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
    private UUID mFragmentID = UUID.randomUUID();
    private TextView mRecylerViewTitle;

    private static final String TAG = BrowserFragment.class.getSimpleName();

    public static final String BROWSER_FRAGMENT_TYPE = "watch.fight.android.fightbrowser.fragment_type";
    public static final String BROWSER_FRAGMENT_GAME = "watch.fight.android.fightbrowser.fragment_game";
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
//        Log.v(TAG, "onCreateView Called");
        View v = inflater.inflate(R.layout.browser_fragment, container, false);

        mLoadingTextView = v.findViewById(R.id.twitch_loading_container);
        mRecylerView = (RecyclerView) v.findViewById(R.id.browser_recycler_view);
        mLayoutManager = new GridLayoutManager(this.getActivity(), RECYCLER_VIEW_GRID_MAX);
        mRecylerViewTitle = (TextView) v.findViewById(R.id.browser_recycler_view_title);

        mAdapter = new TwitchStreamListAdapter(mFragmentID);
        mRecylerView.setAdapter(mAdapter);
        mRecylerView.setLayoutManager(mLayoutManager);
        setUILoading();

        Bundle args = getArguments();

        if (args != null) {

            int fragment_type = args.getInt(BROWSER_FRAGMENT_TYPE, 0);
            String gameName = args.getString(BROWSER_FRAGMENT_GAME, null);

            if (fragment_type > 0) {
                switch (fragment_type) {
                    case BROWSER_FRAGMENT_FEATURED_TYPE:
                        mRecylerViewTitle.setText("Featured");
                        loadTwitchStream("https://api.twitch.tv/kraken/streams/featured?limit=30", fragment_type);
                        break;
                    case BROWSER_FRAGMENT_POPULAR_TYPE:
                        mRecylerViewTitle.setText("Popular");
                        loadTwitchStream("https://api.twitch.tv/kraken/streams/featured", fragment_type);
                        break;
                    case BROWSER_FRAGMENT_GAME_SPECIFIC_TYPE:
                        if (gameName != null) {
                            mRecylerViewTitle.setText(gameName);
                            loadTwitchStream("https://api.twitch.tv/kraken/search/streams?q=" + gameName.replace(" ", "%20") + "&limit=30", fragment_type);
                        } else {
                            throw new IllegalArgumentException("Game name required for a game specific fragment");
                        }
                        break;
                }
                haveStreamsLoaded = true;
            }
        } else {
            Log.v(TAG, "No arguments found in intent");
            loadTwitchStream("https://api.twitch.tv/kraken/streams/featured?limit=30", 0);
            haveStreamsLoaded = true;
        }

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        // TODO : Query Twitch Again if it's been more than .... 2 mins from last call.
        // TODO : Save UUID onResume and state changes
//        Log.v(TAG, "onResume Called");
//        if (!haveStreamsLoaded) {
//            setUILoading();
//            loadTwitchStream("https://api.twitch.tv/kraken/streams/featured?limit=30");
//        }
    }

    public void loadTwitchStream(String url, final int fragmentType) {
        // TODO : Catch no response and set Error UI State
        mTwitchLoader = new TwitchHttpLoader();
        mTwitchLoader.setCustomObjectListener(new TwitchHttpLoader.IHttpResponse() {
            @Override
            public void onReceivedResponse(String result) {
                // Receive the response from the Twitch API, populate the recyclerviewadapter
                // TODO : Catch No Results and set Empty Streams UI State
                Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
                TwitchStream ts = gson.fromJson(result, TwitchStream.class);

                switch (fragmentType) {
                    case BROWSER_FRAGMENT_FEATURED_TYPE:
                        TwitchStreamHolder.getInstance(mFragmentID).setStreams(ts.getStreamsFromFeatured());
                        break;
                    case BROWSER_FRAGMENT_GAME_SPECIFIC_TYPE:
                        TwitchStreamHolder.getInstance(mFragmentID).setStreams(ts.getStreams());
                        break;
                    case BROWSER_FRAGMENT_POPULAR_TYPE:
                        break;
                }

                if (getActivity() != null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.notifyDataSetChanged();
                            setUIReady();
                        }
                    });
                }

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
