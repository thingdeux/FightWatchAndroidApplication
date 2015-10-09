package watch.fight.android.fightbrowser.Utils;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by josh on 9/17/15.
 */
public class DateParser {
    // Should return the clients locale
    public static DateFormat m_ISO8601Local = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());
    public static DateFormat simpleDate = new SimpleDateFormat("ccc dd h:mm a", Locale.getDefault());

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

    public static Date epochToDate(long epochTime) {
        return new Date(epochTime);
    }

    public static Long dateToEpoch(Date date) {
        if (date != null) {
            return date.getTime();
        } else {
            return null;
        }
    }

    public static Long dateToEpoch(GregorianCalendar date) {
        if (date != null) {
            return date.getTimeInMillis();
        } else {
            return null;
        }
    }

    public static GregorianCalendar epochToGregorian(long epochTime) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTimeInMillis(epochTime);
        return cal;
    }

    public static String dateToSimpleDateStr(Date date) {
        if (date != null) {
            return simpleDate.format(date).toString();
        }
        return null;
    }
}
