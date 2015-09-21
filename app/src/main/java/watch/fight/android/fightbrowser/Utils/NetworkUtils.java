package watch.fight.android.fightbrowser.Utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by josh on 9/21/15.
 */
public class NetworkUtils {
    private static String TAG = NetworkUtils.class.getSimpleName();

    public static String InputStreamToString(InputStream stream) {
        try {
            BufferedReader r = new BufferedReader(new InputStreamReader(stream));
            StringBuilder total = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                total.append(line);
            }
            return total.toString();
        } catch (IOException ioe) {
            Log.e(TAG, "Unable to convert inputStreamToString");
        }
        return null;
    }
}
