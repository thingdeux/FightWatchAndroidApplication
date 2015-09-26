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

    @Expose
    @SerializedName("feed_image_url")
    private String mFeedImageUrl;

    private String mLastUpdated;

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

    public String getFeedImageUrl() {
        return mFeedImageUrl;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        mName = name;
    }

    public void setRSSUrl(String RSSUrl) {
        mRSSUrl = RSSUrl;
    }

    public void setWebUrl(String webUrl) {
        mWebUrl = webUrl;
    }

    public void setFeedImageUrl(String feedImageUrl) {
        mFeedImageUrl = feedImageUrl;
    }

    public String getLastUpdated() {
        return mLastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        mLastUpdated = lastUpdated;
    }

    // TODO : Image Download and store in long term cache
    @Override
    public String toString() {
        return "ID: " + getId() +
                " Name: " + getName() +
                " RssUrl: " + getRSSUrl();
    }
}
