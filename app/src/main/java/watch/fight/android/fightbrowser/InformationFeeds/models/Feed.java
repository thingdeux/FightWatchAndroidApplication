package watch.fight.android.fightbrowser.InformationFeeds.models;

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
    private String mRSSUrl;

    @Expose
    @SerializedName("website_url")
    private String mWebUrl;

    public long getId() {
        return id;
    }

    public String getName() {
        return mName;
    }

    public String getRSSUrl() {
        return mRSSUrl;
    }

    public String getWebUrl() {
        return mWebUrl;
    }
}
