package watch.fight.android.fightbrowser.Twitch.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by josh on 9/11/15.
 */
public class TwitchStream {
    @Expose
    private TwitchStreamLinks _links;

    @Expose
    @SerializedName("_total")
    private int total;

    @Expose
    private TwitchFeaturedStream[] featured;

    @Expose
    @SerializedName("streams")
    private TwitchStreamInfo[] streams;

    public TwitchStreamLinks get_links() {
        return _links;
    }

    public int getTotal() {
        return total;
    }

    public TwitchFeaturedStream[] getFeaturedStreams() {
        return featured;
    }

    public TwitchStreamInfo[] getStreamsFromFeatured() {
        TwitchStreamInfo[] channels = new TwitchStreamInfo[featured.length];
        for (int i = 0; i < featured.length; i++) {
            channels[i] = featured[i].getStream();
        }
        return channels;
    }

    public TwitchStreamInfo[] getStreams() {
        return streams;
    }
}