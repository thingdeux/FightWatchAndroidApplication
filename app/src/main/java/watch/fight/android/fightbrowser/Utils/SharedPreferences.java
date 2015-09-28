package watch.fight.android.fightbrowser.Utils;

import android.content.Context;
import android.preference.PreferenceManager;

import java.util.Set;

/**
 * Created by josh on 9/20/15.
 */
public class SharedPreferences {
    public static String FAVORITE_STREAMS = "FavoriteStreams";
    public static String BLOCKED_STREAMS = "BlockedStreams";
    public static String CONFIG_LAST_UPDATED = "ConfigLastUpdated";
    public static String FEEDS_LAST_UPDATED = "FeedsLastUpdated";
    public static String TWITCH_STREAMS_LAST_UPDATED = "TwitchStreamsLastUpdated";

    public static void setFavoriteStreams(Context context, Set<String> favorites) { setStringSet(context, FAVORITE_STREAMS, favorites); }
    public static void setBlockedStreams(Context context, Set<String> blocked) { setStringSet(context, BLOCKED_STREAMS, blocked); }
    public static void setConfigLastUpdated(Context context, long lastUpdated) { setLong(context, CONFIG_LAST_UPDATED, lastUpdated); }
    public static void setFeedsLastUpdated(Context context, long lastUpdated) { setLong(context, FEEDS_LAST_UPDATED, lastUpdated); }
    public static void setFeedsLastUpdatedToNow(Context context) { setLong(context, FEEDS_LAST_UPDATED, System.currentTimeMillis()); }

    public static void setTwitchStreamsLastUpdated(Context context, long lastUpdated) { setLong(context, TWITCH_STREAMS_LAST_UPDATED, lastUpdated);
    }

    public static Set<String> getFavoriteStreams(Context context)  { return getStringSet(context, FAVORITE_STREAMS); }
    public static Set<String> getBlockedStreams(Context context) { return getStringSet(context, BLOCKED_STREAMS); }
    public static long getConfigLastUpdated(Context context) { return getLong(context, CONFIG_LAST_UPDATED); }
    public static long getFeedsLastUpdated(Context context) { return getLong(context, FEEDS_LAST_UPDATED); }
    public static long getTwitchStreamsLastUpdated(Context context) { return getLong(context, TWITCH_STREAMS_LAST_UPDATED); }


    private static long getLong(Context context, String name) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getLong(name, -1);
    }

    private static void setLong(Context context, String name, long value) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putLong(name, value)
                .apply();
    }


    private static String getString(Context context, String name) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(name, null);
    }

    private static void setString(Context context, String name, String value) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(name, value)
                .apply();
    }

    private static Set<String> getStringSet(Context context, String name) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getStringSet(name, null);
    }

    private static void setStringSet(Context context, String name, Set<String> values) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putStringSet(name, values)
                .apply();
    }


}
