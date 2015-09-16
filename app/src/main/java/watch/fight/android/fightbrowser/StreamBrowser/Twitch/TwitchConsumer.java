package watch.fight.android.fightbrowser.StreamBrowser.Twitch;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

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
            String contentAsString = readIt(input);
            Log.v(TAG, "Received Response code from Twitch: " + response);
            Log.d(TAG, "Calling callback: " + mCallback.toString());
            mCallback.onReceivedResponse(contentAsString);
            return input.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
    }

    // TODO : Should probably use a handy dandy http library instead of doing this. Will Refactor
    // Reads an InputStream and converts it to a String.
    public String readIt(InputStream stream) throws IOException, UnsupportedEncodingException {
        BufferedReader r = new BufferedReader(new InputStreamReader(stream));
        StringBuilder total = new StringBuilder();
        String line;
        while ((line = r.readLine()) != null) {
            total.append(line);
        }
        return total.toString();
    }

}
