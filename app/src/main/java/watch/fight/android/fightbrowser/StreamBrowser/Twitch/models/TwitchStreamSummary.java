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

    @Expose
    @SerializedName("_links")
    private TwitchStreamLinks mLinks;

    public Long getViewers() {
        return mViewers;
    }

    public Long getChannels() {
        return mChannels;
    }

    public TwitchStreamLinks getLinks() {
        return mLinks;
    }

    public String getGameNameFromQuery() {
        // Example Query: https://api.twitch.tv/kraken/streams/summary?game=Ultimate+Marvel+vs.+Capcom+3"
        // Splitting it to get to 'summary?game=" then replacing that with ""
        String fullQuery = mLinks.getSelf();
        String[] splitString = fullQuery.split("/");
        fullQuery = splitString[splitString.length - 1].replace("summary?game=", "");
        return fullQuery.replace("+", " ");
    }
}
