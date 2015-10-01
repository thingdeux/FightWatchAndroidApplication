package watch.fight.android.fightbrowser.Config;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


import watch.fight.android.fightbrowser.Config.models.Config;
import watch.fight.android.fightbrowser.R;
import watch.fight.android.fightbrowser.Utils.JsonFromRaw;
import watch.fight.android.fightbrowser.Utils.Network.ParseUtils;

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

    public static Config getTestConfig(Context context) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        String s = JsonFromRaw.getStringObj(context, R.raw.test_config);
        return gson.fromJson(s, Config.class);
    }

}
