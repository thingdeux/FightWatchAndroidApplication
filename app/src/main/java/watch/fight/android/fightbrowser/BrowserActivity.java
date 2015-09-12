package watch.fight.android.fightbrowser;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import watch.fight.android.fightbrowser.Twitch.TwitchFeaturedStream;
import watch.fight.android.fightbrowser.Twitch.TwitchHttpLoader;
import watch.fight.android.fightbrowser.Twitch.TwitchStream;
import watch.fight.android.fightbrowser.Twitch.TwitchStreamHolder;
import watch.fight.android.fightbrowser.Twitch.TwitchStreamListAdapter;

public class BrowserActivity extends AppCompatActivity {
    private RecyclerView mRecylerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private boolean haveStreamsLoaded = false;
    private static int RECYCLER_VIEW_GRID_MAX = 2;
    private View mLoadingTextView;
    private TwitchHttpLoader mTwitchLoader;

    private static String TAG = BrowserActivity.TAG;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);

        mLoadingTextView = (View) findViewById(R.id.twitch_loading_container);
        mRecylerView = (RecyclerView) findViewById(R.id.browser_recycler_view);
        mLayoutManager = new GridLayoutManager(this, RECYCLER_VIEW_GRID_MAX);

        mAdapter = new TwitchStreamListAdapter();
        mRecylerView.setAdapter(mAdapter);
        mRecylerView.setLayoutManager(mLayoutManager);
        setUILoading();
        loadTwitchStream();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!haveStreamsLoaded) {
            setUILoading();
            loadTwitchStream();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        // TODO : Clear all cached images and responses.
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
                runOnUiThread(new Runnable() {
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



    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_browser, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public JSONObject getTestJson(int resId) {
        //Get Data From Text Resource File Contains Json Data.
        InputStream inputStream = getResources().openRawResource(resId);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        int ctr;
        try {
            ctr = inputStream.read();
            while (ctr != -1) {
                byteArrayOutputStream.write(ctr);
                ctr = inputStream.read();
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.v("Text Data", byteArrayOutputStream.toString());
        try {
            // Parse the data into jsonobject to get original data in form of json.
            return (new JSONObject(byteArrayOutputStream.toString()));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getTestJsonString(int resId) {
        //Get Data From Text Resource File Contains Json Data.
        InputStream inputStream = getResources().openRawResource(resId);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        int ctr;
        try {
            ctr = inputStream.read();
            while (ctr != -1) {
                byteArrayOutputStream.write(ctr);
                ctr = inputStream.read();
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return(byteArrayOutputStream.toString());
    }

}