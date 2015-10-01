package watch.fight.android.fightbrowser.StreamBrowser.Twitch.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by josh on 9/30/15.
 */
public class TwitchStreamSummary {
    @Expose
    @SerializedName("viewers")
    private Long mViewers;

    @Expose
    @SerializedName("channels")
    private Long mChannels;

    public Long getViewers() {
        return mViewers;
    }

    public Long getChannels() {
        return mChannels;
    }
}
