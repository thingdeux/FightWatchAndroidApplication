package watch.fight.android.fightbrowser.StreamBrowser.Twitch;

import android.util.Log;

import java.util.HashMap;
import java.util.UUID;

import watch.fight.android.fightbrowser.StreamBrowser.Twitch.models.TwitchFeaturedStream;
import watch.fight.android.fightbrowser.StreamBrowser.Twitch.models.TwitchStreamInfo;

/**
 * Created by josh on 9/12/15.
 */
public class TwitchStreamHolder {
//    private static TwitchStreamHolder mStreamHolder = null;
    private static HashMap<UUID, TwitchStreamHolder> mStreamHolder = new HashMap<>();
    private TwitchFeaturedStream[] mFeaturedStreams;
    private TwitchStreamInfo[] mStreams;

    public static TwitchStreamHolder getInstance(UUID fragmentId) {
        if (mStreamHolder.get(fragmentId) == null) {
            TwitchStreamHolder tsh = new TwitchStreamHolder();
            tsh.setStreams(new TwitchStreamInfo[0]);
            mStreamHolder.put(fragmentId, tsh);
        }
        return mStreamHolder.get(fragmentId);
    }

    protected TwitchStreamHolder() {
    }

    public TwitchStreamInfo[] getStreams() { return mStreams; }

    public TwitchStreamInfo getStream(int position) { return mStreams[position]; }

    public void setStreams(TwitchStreamInfo[] streams) { mStreams = streams; }

    public TwitchFeaturedStream[] getFeatured() {
        return mFeaturedStreams;
    }

    public TwitchFeaturedStream getFeatured(int position) {
        return mFeaturedStreams[position];
    }

    public void setFeaturedStreams(TwitchFeaturedStream[] featured) {
        Log.d("TwitchStreamHolder", "Setting " + featured.length + " streams");
        mFeaturedStreams = featured;
    }
}
