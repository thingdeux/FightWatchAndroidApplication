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
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;

import watch.fight.android.fightbrowser.Config.ConfigFetcher;
import watch.fight.android.fightbrowser.Config.models.Config;
import watch.fight.android.fightbrowser.Config.models.DB.GameDB;
import watch.fight.android.fightbrowser.Config.models.GameConfig;
import watch.fight.android.fightbrowser.Events.EventsActivity;
import watch.fight.android.fightbrowser.Events.models.Bracket;
import watch.fight.android.fightbrowser.Events.models.DB.BracketDB;
import watch.fight.android.fightbrowser.Events.models.DB.BracketDBHelper;
import watch.fight.android.fightbrowser.Events.models.DB.EventDB;
import watch.fight.android.fightbrowser.Events.models.Event;
import watch.fight.android.fightbrowser.InformationFeeds.InformationFeedsActivity;
import watch.fight.android.fightbrowser.InformationFeeds.models.DB.FeedDB;
import watch.fight.android.fightbrowser.StreamBrowser.BrowserActivity;
import watch.fight.android.fightbrowser.R;
import watch.fight.android.fightbrowser.StreamBrowser.models.Streamer;
import watch.fight.android.fightbrowser.StreamBrowser.models.StreamerDB;
import watch.fight.android.fightbrowser.Utils.DateParser;
import watch.fight.android.fightbrowser.Utils.SharedPreferences;

/**
 * Created by josh on 9/14/15.
 */
public class DashboardActivity extends AppCompatActivity {
    private static final String TAG = DashboardActivity.class.getSimpleName();
    private static final int CONFIG_CHECK_FREQUENCY_IN_HOURS = 24;

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
            date.add(Calendar.HOUR, CONFIG_CHECK_FREQUENCY_IN_HOURS);
            GregorianCalendar today = new GregorianCalendar();

            // If today > LastConfigUpdate + 24 hours -- Only check for new config once a day
            if (today.after(date)) {
                // TODO : Actually make API call here instead of just getting test config.
                Config config = ConfigFetcher.getTestConfig(mContext);

                if (config != null) {
                    List<GameConfig> receivedGames = config.getGames();
                    // TODO : Only process if updated flag in response is > last update or null.
                    // TODO : Savelastupdate date as long in sharedprefs
                    if (receivedGames != null && receivedGames.size() > 0) {
                        GameDB gameDB = GameDB.getInstance(mContext);
                        List<GameConfig> existingDBGames = gameDB.getAllGames();

                        // Place Each ID in a hashset to reduce cost of checks below
                        HashSet<Integer> gameSet = new HashSet<>();
                        for (int i = 0; i < existingDBGames.size(); i++) {
                            gameSet.add(existingDBGames.get(i).getId());
                        }

                        for (int i=0; i < receivedGames.size(); i++) {
                            if (!gameSet.contains(receivedGames.get(i).getId())) {
                                // If DB key is not found in the DB - create it.
                                GameConfig game = receivedGames.get(i);
                                gameDB.addGame(game);
                                if (game.getKnownStreamers() != null && game.getKnownStreamers().size() > 0) {
                                    StreamerDB.getInstance(mContext).addStreamers(game.getKnownStreamers(), game);
                                }
                            }
                        }
                    }

                    if (config.getFeeds() != null) {
                        FeedDB.getInstance(mContext).deleteAllFeeds();
                        FeedDB.getInstance(mContext).addFeeds(config.getFeeds());
                    }

                    if (config.getEvents() != null) {
                        List<Event> events = config.getEvents();
                        EventDB.getInstance(mContext).deleteAllEvents();  // TODO : Will actually check for lastUpdated being > thn
                        EventDB.getInstance(mContext).addEvents(events);

                        for (int i =0; i < config.getEvents().size(); i++) {
                            if (events.get(i).getBrackets() != null) {
                                BracketDB.getInstance(mContext).addBrackets(events.get(i).getBrackets(), events.get(i));
                            }
                        }

                    }
                }
                SharedPreferences.setConfigLastUpdated(mContext, System.currentTimeMillis());

                // TODO : REMOVE THIS
                List<GameConfig> games = GameDB.getInstance(mContext).getAllGames();
                for (GameConfig c : games) {
                    if (c.getKnownStreamersFromDB(mContext) != null) {
                        Log.i("JJDEBUG", "Game: " + c.getGameName());
                        for (Streamer s : c.getKnownStreamersFromDB(mContext)) {
                            Log.i("JJDEBUG", "Streamer " + s.getName());
                        }
                    }
                }
                // To here
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
//        Fabric.with(this, new Crashlytics());  // Crashlytics Init - Don't Remove
        new FetchConfig(this).execute();

        setContentView(R.layout.dashboard_activity);

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
        int id = item.getItemId();

        switch (id) {
            case R.id.action_streams:
                startActivity(new Intent(this, BrowserActivity.class));
                return true;
            case R.id.action_news:
                startActivity(new Intent(this, InformationFeedsActivity.class));
                return true;
            case R.id.action_preferences:
                Toast.makeText(this, "Not Yet Implement", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_events:
                startActivity(new Intent(this, EventsActivity.class));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
