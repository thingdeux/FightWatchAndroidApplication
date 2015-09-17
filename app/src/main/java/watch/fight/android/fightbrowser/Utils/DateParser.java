package watch.fight.android.fightbrowser.Utils;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by josh on 9/17/15.
 */
public class DateParser {
    // Should return the clients locale
    public static DateFormat m_ISO8601Local = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());

    public static Date getDateFromStr(String dateString) {
        if (dateString != null) {
            try {
                return m_ISO8601Local.parse(dateString);
            } catch (ParseException e) {
                Log.e("DateParser", "Error Parsing Date String: " + dateString + " - " + e);
            }
        }
        return null;
    }
}
