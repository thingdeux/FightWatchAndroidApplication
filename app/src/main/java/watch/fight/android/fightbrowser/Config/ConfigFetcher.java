package watch.fight.android.fightbrowser.Config;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;


import watch.fight.android.fightbrowser.Config.models.Config;
import watch.fight.android.fightbrowser.Config.models.DB.GameDB;
import watch.fight.android.fightbrowser.Config.models.GameConfig;
import watch.fight.android.fightbrowser.Dashboard.DashboardFragment;
import watch.fight.android.fightbrowser.Events.models.DB.BracketDB;
import watch.fight.android.fightbrowser.Events.models.DB.EventDB;
import watch.fight.android.fightbrowser.Events.models.Event;
import watch.fight.android.fightbrowser.InformationFeeds.models.DB.FeedDB;
import watch.fight.android.fightbrowser.InformationFeeds.models.DB.StoryTrackerDB;
import watch.fight.android.fightbrowser.R;
import watch.fight.android.fightbrowser.StreamBrowser.models.StreamerDB;
import watch.fight.android.fightbrowser.Utils.DateParser;
import watch.fight.android.fightbrowser.Utils.JsonFromRaw;
import watch.fight.android.fightbrowser.Utils.Network.ParseUtils;
import watch.fight.android.fightbrowser.Utils.SharedPreferences;

/**
 * Created by josh on 9/20/15.
 */
public class ConfigFetcher extends AsyncTask<Void, Void, Config> {
    private static final int CONFIG_CHECK_FREQUENCY_IN_HOURS = 24;
    private static final String TAG = ConfigFetcher.class.getSimpleName();
    private static final String BASE_CONFIG_SERVER_URL = "https://api.fgccompanion.com";
    private Context mContext;
    private DashboardFragment mDashboardFragment;

    public ConfigFetcher(Context c) {
        mContext = c;
    }

    public ConfigFetcher(Context c, DashboardFragment df) {
        mContext = c;
        mDashboardFragment = df;
    }

    protected Config doInBackground(Void... response) {
        // Prune old 'Mark as read' story trackers
        StoryTrackerDB.getInstance(mContext).pruneOldTrackers();
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
            return config;
        }
        return null;
    }

    protected void onPostExecute(Config config) {
        if (config != null) {
            Log.v(TAG, "Received new config data");
            if (mDashboardFragment != null) {
                mDashboardFragment.fetchFeeds();
            }
        } else {
            // Can be null if config isn't old enough to check for new data
            // TODO : Make sure some configuration exists if this is null - if not throw error to fragment.
        }

    }

    private static Config getConfigFromServer(Context context) {
        String urlStr = Uri.parse(BASE_CONFIG_SERVER_URL)
                .buildUpon()
                .appendPath("conf")
                .build()
                .toString();
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        try {
            URL urlConnection = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) urlConnection.openConnection();
            // TODO : Actually make this call when the API is ready!
            connection.connect();
            int responseCode = connection.getResponseCode();
            InputStream input = connection.getInputStream();
            String response = ParseUtils.InputStreamToString(input);
            return gson.fromJson(response, Config.class);
        } catch (MalformedURLException mue) {
            // TODO : Implement last config from DB on fail.
            Log.e(TAG, "MalformedUrlException getting config " + mue);
        } catch (IOException ioe) {
            Log.e(TAG, "IOException getting config " + ioe);
        }
        return null;
    }

    private static Config getTestConfig(Context context) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        String s = JsonFromRaw.getStringObj(context, R.raw.test_config);
        return gson.fromJson(s, Config.class);
    }


}
