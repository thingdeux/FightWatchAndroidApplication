package watch.fight.android.fightbrowser.Dashboard;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import watch.fight.android.fightbrowser.Config.ConfigFetcher;
import watch.fight.android.fightbrowser.Config.models.Config;
import watch.fight.android.fightbrowser.StreamBrowser.BrowserActivity;
import watch.fight.android.fightbrowser.R;
import watch.fight.android.fightbrowser.Utils.DateParser;
import watch.fight.android.fightbrowser.Utils.SharedPreferences;

/**
 * Created by josh on 9/14/15.
 */
public class DashboardActivity extends AppCompatActivity {
    private static final String TAG = DashboardActivity.class.getSimpleName();
    private static final int GET_CONFIG_FROM_API = 0;

    private class FetchConfig extends AsyncTask<Void, Void, Config> {
        private Context mContext;
        public FetchConfig(Context c) {
            mContext = c;
        }

        protected Config doInBackground(Void... response) {
            // TODO: If on first start and DB is empty - App will ship with last updated fixtures
            // The "fixtures" will set the app to the latest state.
            // Call API Server - convert to Config Object return Config instance
            GregorianCalendar date = DateParser.epochToGregorian(SharedPreferences.getConfigLastUpdated(mContext));
            date.add(Calendar.HOUR, 24);
            GregorianCalendar today = new GregorianCalendar();


            // If today > LastConfigUpdate + 24 hours -- Only check for new config once a day
            if (today.after(date)) {
                Config config = ConfigFetcher.getTestConfig(mContext);
                if (config != null) {
                    SharedPreferences.setConfigLastUpdated(mContext, System.currentTimeMillis());
                }
                return config;
            }
            return null;
        }

        protected void onPostExecute(Config config) {
            if (config != null) {
                Log.v(TAG, "Received new config data");
            }
            // Can be null if config isn't old enough to check for new data
        }
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_activity);

        new FetchConfig(this).execute();

        if (savedInstanceState == null) {
            DashboardFragment dashboardFragment = new DashboardFragment();

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

        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public boolean handleMessage(Message msg) {
//        switch (msg.what) {
//            case GET_CONFIG_FROM_API:
//                break;
//            default:
//                return false;
//        }
//        return true;
//    }

}
