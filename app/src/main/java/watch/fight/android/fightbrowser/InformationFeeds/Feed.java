package watch.fight.android.fightbrowser.InformationFeeds;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by josh on 9/16/15.
 */
public class Feed {
    @Expose
    @SerializedName("id")
    private long id;

    @Expose
    @SerializedName("name")
    private String mName;

    @Expose
    @SerializedName("rss_url")
    private long mRSSUrl;

    @Expose
    @SerializedName("website_url")
    private long mWebUrl;

    public long getId() {
        return id;
    }

    public String getName() {
        return mName;
    }

    public long getRSSUrl() {
        return mRSSUrl;
    }

    public long getWebUrl() {
        return mWebUrl;
    }
}
