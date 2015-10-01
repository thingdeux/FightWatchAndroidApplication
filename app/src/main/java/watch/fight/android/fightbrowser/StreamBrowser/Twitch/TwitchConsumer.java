package watch.fight.android.fightbrowser.StreamBrowser.Twitch;

import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;

import java.net.HttpURLConnection;
import java.net.URL;

import watch.fight.android.fightbrowser.Utils.Network.ParseUtils;

/**
 * Created by josh on 9/12/15.
 */
public class TwitchConsumer extends AsyncTask<Void, Void, String> {
    private static final String TAG = TwitchConsumer.class.getSimpleName();
    private String mUrl;
    private TwitchHttpLoader.IHttpResponse mCallback;

    public TwitchConsumer(String url, TwitchHttpLoader.IHttpResponse callback) {
        mUrl = url;
        mCallback = callback;
    }

    @Override
    protected String doInBackground(Void... params) {
        try {
            URL urlConnection = new URL(mUrl);
            Log.d(TAG, "Requested Url from: " + mUrl);
            HttpURLConnection connection = (HttpURLConnection) urlConnection.openConnection();
            connection.setDoInput(true);
            connection.connect();
            int response = connection.getResponseCode();
            InputStream input = connection.getInputStream();
            return ParseUtils.InputStreamToString(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
//        Log.v(TAG, "Received Response code from Twitch: " + result);
        mCallback.onReceivedResponse(result);
    }

}
