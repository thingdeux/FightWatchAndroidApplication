package watch.fight.android.fightbrowser.InformationFeeds.models;

import android.net.Uri;

import java.util.Date;

/**
 * Created by josh on 9/22/15.
 */
public class Story {
    private String mSiteName;
    private String mTitle;
    private String mDescription;
    private Uri mUrl;
    private String mAuthor;
    private Date mPublishedDate;
    private String mLastUpdated;
    private String mThumbnail;

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String author) {
        mAuthor = author;
    }

    public Uri getUrl() {
        return mUrl;
    }

    public void setUrl(Uri url) {
        mUrl = url;
    }

    public Date getPublishedDate() {
        return mPublishedDate;
    }

    public void setPublishedDate(Date publishedDate) {
        mPublishedDate = publishedDate;
    }

    public String getLastUpdated() {
        return mLastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        mLastUpdated = lastUpdated;
    }

    public String getSiteName() {
        return mSiteName;
    }

    public void setSiteName(String siteName) {
        mSiteName = siteName;
    }

    public String getThumbnail() {
        return mThumbnail;
    }

    public void setThumbnail(String thumbnail) {
        mThumbnail = thumbnail;
    }
}
