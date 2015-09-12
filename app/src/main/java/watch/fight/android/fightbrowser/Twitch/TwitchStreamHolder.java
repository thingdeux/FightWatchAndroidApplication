package watch.fight.android.fightbrowser.Twitch;

import android.util.Log;

/**
 * Created by josh on 9/12/15.
 */
public class TwitchStreamHolder {
    private static TwitchStreamHolder mStreamHolder = null;
    private TwitchFeaturedStream[] mStreams;

    public static TwitchStreamHolder getInstance() {
        if (mStreamHolder == null) {
            TwitchStreamHolder tsh = new TwitchStreamHolder();
            tsh.setFeaturedStreams(new TwitchFeaturedStream[0]);
            mStreamHolder = tsh;
        }
        return mStreamHolder;
    }

    protected TwitchStreamHolder() {
    }

    public TwitchFeaturedStream[] getFeatured() {
        return mStreams;
    }

    public TwitchFeaturedStream getFeatured(int position) {
        return mStreams[position];
    }

    public void setFeaturedStreams(TwitchFeaturedStream[] featured) {
        Log.d("TwitchStreamHolder", "Setting " + featured.length + " streams");
        mStreams = featured;
    }
}
