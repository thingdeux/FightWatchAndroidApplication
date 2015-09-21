package watch.fight.android.fightbrowser.Config;

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
    public static void setConfigLastUpdated(Context context, String lastUpdated) { setString(context, CONFIG_LAST_UPDATED, lastUpdated); }
    public static void setFeedsLastUpdated(Context context, String lastUpdated) { setString(context, FEEDS_LAST_UPDATED, lastUpdated); }
    public static void setTwitchStreamsLastUpdated(Context context, String lastUpdated) { setString(context, TWITCH_STREAMS_LAST_UPDATED, lastUpdated); }

    public static Set<String> getFavoriteStreams(Context context)  { return getStringSet(context, FAVORITE_STREAMS); }
    public static Set<String> getBlockedStreams(Context context) { return getStringSet(context, BLOCKED_STREAMS); }
    public static String getConfigLastUpdated(Context context) { return getString(context, CONFIG_LAST_UPDATED); }
    public static String getFeedsLastUpdated(Context context) { return getString(context, FEEDS_LAST_UPDATED); }
    public static String getTwitchStreamsLastUpdated(Context context) { return getString(context, TWITCH_STREAMS_LAST_UPDATED); }


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
