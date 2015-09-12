package watch.fight.android.fightbrowser;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import watch.fight.android.fightbrowser.Twitch.TwitchStream;
import watch.fight.android.fightbrowser.Twitch.TwitchStreamListAdapter;

public class BrowserActivity extends AppCompatActivity {
    private RecyclerView mRecylerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private static String TAG = BrowserActivity.TAG;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);

        // Create Twitch Test Data
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        String testJsonString = getTestJsonString(R.raw.test_twitch);
        Log.v(TAG, testJsonString);

        TwitchStream ts = gson.fromJson(testJsonString, TwitchStream.class);
        mRecylerView = (RecyclerView) findViewById(R.id.browser_recycler_view);
        mLayoutManager = new LinearLayoutManager(this);
        mRecylerView.setLayoutManager(mLayoutManager);

        mAdapter = new TwitchStreamListAdapter(ts.getFeatured());
        mRecylerView.setAdapter(mAdapter);

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
