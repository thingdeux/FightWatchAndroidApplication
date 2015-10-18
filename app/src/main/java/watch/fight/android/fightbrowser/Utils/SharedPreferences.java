package watch.fight.android.fightbrowser.Utils;

import android.content.Context;
import android.preference.PreferenceManager;

import java.util.Set;

/**
 * Created by josh on 9/20/15.
 */
public class SharedPreferences {
    public static final String CONFIG_LAST_UPDATED = "ConfigLastUpdated";
    public static final String FEEDS_LAST_UPDATED = "FeedsLastUpdated";
    public static final String TWITCH_STREAMS_LAST_UPDATED = "TwitchStreamsLastUpdated";
    public static final String TO_WEBVIEW_OR_NOT_TO_WEBVIEW = "WebviewChoice";
    public static final String DARK_VIEW = "DarkView";
    public static final String SHOW_FILTERED_FEEDS = "ShowFilteredFeeds";

    public static void setConfigLastUpdated(Context context, long lastUpdated) { setLong(context, CONFIG_LAST_UPDATED, lastUpdated); }
    public static void setFeedsLastUpdated(Context context, long lastUpdated) { setLong(context, FEEDS_LAST_UPDATED, lastUpdated); }
    public static void setFeedsLastUpdatedToNow(Context context) { setLong(context, FEEDS_LAST_UPDATED, System.currentTimeMillis()); }
    public static void setToWebviewOrNotToWebview(Context context, Boolean value) { setBool(context, TO_WEBVIEW_OR_NOT_TO_WEBVIEW, value); }
    public static void setDarkView(Context context, Boolean value) { setBool(context, DARK_VIEW, value); }
    public static void setShowFilteredFeeds(Context context, Boolean value) { setBool(context, SHOW_FILTERED_FEEDS, value); }
    public static void setTwitchStreamsLastUpdated(Context context, long lastUpdated) { setLong(context, TWITCH_STREAMS_LAST_UPDATED, lastUpdated); }

    public static long getConfigLastUpdated(Context context) { return getLong(context, CONFIG_LAST_UPDATED); }
    public static long getFeedsLastUpdated(Context context) { return getLong(context, FEEDS_LAST_UPDATED); }
    public static long getTwitchStreamsLastUpdated(Context context) { return getLong(context, TWITCH_STREAMS_LAST_UPDATED); }
    public static Boolean getShowFilteredFeeds(Context context) { return getBoolean(context, SHOW_FILTERED_FEEDS); }
    public static Boolean getToWebViewOrNotToWebView(Context context) { return getBoolean(context, TO_WEBVIEW_OR_NOT_TO_WEBVIEW); }
    public static Boolean getDarkView(Context context) { return getBoolean(context, DARK_VIEW); }

    public static void toggleShowFilteredFeeds(Context context) {
        setBool(context, SHOW_FILTERED_FEEDS, !getShowFilteredFeeds(context));
    }

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

    private static void setBool(Context context, String name, Boolean value) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(name, value)
                .apply();
    }

    private static String getString(Context context, String name) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(name, null);
    }

    private static Boolean getBoolean(Context context, String name) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(name, false);
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
