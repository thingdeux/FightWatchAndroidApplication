package watch.fight.android.fightbrowser.Config;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.OkHttpDownloader;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import watch.fight.android.fightbrowser.Config.models.Config;
import watch.fight.android.fightbrowser.R;
import watch.fight.android.fightbrowser.StreamBrowser.Twitch.models.TwitchStream;
import watch.fight.android.fightbrowser.Utils.JsonFromRaw;
import watch.fight.android.fightbrowser.Utils.NetworkUtils;

/**
 * Created by josh on 9/20/15.
 */
public class ConfigFetcher {
    private static final String TAG = ConfigFetcher.class.getSimpleName();
    private static final String BASE_CONFIG_SERVER_URL = "https://api.fgccompanion.com";

    public static Config getConfigFromServer(Context context) {
        String urlStr = Uri.parse(BASE_CONFIG_SERVER_URL)
                                 .buildUpon()
                                 .appendPath("conf")
                                 .build()
                                 .toString();

        try {
            URL urlConnection = new URL(urlStr);
            Log.v(TAG, "Requested Url from: " + urlStr);
            HttpURLConnection connection = (HttpURLConnection) urlConnection.openConnection();
            connection.connect();
//            int responseCode = connection.getResponseCode();
            InputStream input = connection.getInputStream();
            String response = NetworkUtils.InputStreamToString(input);

            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

            // TODO : Once API Server in place Turn this back on
//            Config config = gson.fromJson(response, Config.class);
            String s = JsonFromRaw.getStringObj(context, R.raw.test_config);
            return gson.fromJson(s, Config.class);
        } catch (MalformedURLException mue) {
            // TODO : Implement last config from DB on fail.
            Log.e(TAG, "MalformedUrlException getting config " + mue);
        } catch (IOException ioe) {
            // TODO : Implement last config from DB on fail.
            Log.e(TAG, "IOException getting config " + ioe);
        }
        return null;
    }

}
