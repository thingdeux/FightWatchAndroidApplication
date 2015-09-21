package watch.fight.android.fightbrowser.Dashboard;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import watch.fight.android.fightbrowser.Config.ConfigFetcher;
import watch.fight.android.fightbrowser.Config.models.Config;
import watch.fight.android.fightbrowser.StreamBrowser.BrowserActivity;
import watch.fight.android.fightbrowser.R;
import watch.fight.android.fightbrowser.Utils.JsonFromRaw;

/**
 * Created by josh on 9/14/15.
 */
public class DashboardActivity extends AppCompatActivity implements Handler.Callback {
    private static final String TAG = DashboardActivity.class.getSimpleName();
    private static final int GET_CONFIG_FROM_API = 0;
    private Handler mMessageHandler;

    private class FetchConfig extends AsyncTask<Void, Void, Config> {
        protected Config doInBackground(Void... response) {
            // Call API Server - convert to Config Object return Config instance
            // TODO : REMOVE THIS! Never pass context in async task potential leaks.
            return ConfigFetcher.getConfigFromServer(getParent());
        }

        protected void onPostExecute(Config config) {
            Log.d(TAG, "Got Config: " + config);
        }
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_activity);

        mMessageHandler = new Handler(this);

        if (savedInstanceState == null) {
            DashboardFragment dashboardFragment = new DashboardFragment();

//        dashboardFragment.setArguments(getIntent().getExtras());
//            JSONObject dashboardEntries = getTestJson(R.raw.test_config);
//            Gson g = new Gson();
//            Config c = g.fromJson(dashboardEntries.toString(), Config.class);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.dashboard_main_fragment, dashboardFragment).commit();
        }



    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMessageHandler.removeMessages(GET_CONFIG_FROM_API);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_dashboard, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        switch (id) {
            case R.id.action_streams:
                startActivity(new Intent(this, BrowserActivity.class));
                return true;
            case R.id.action_news:
                return true;
            case R.id.action_preferences:
                return true;
            case R.id.action_events:
                return true;
        }


//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case GET_CONFIG_FROM_API:
                break;
            default:
                return false;
        }
        return true;
    }

}
