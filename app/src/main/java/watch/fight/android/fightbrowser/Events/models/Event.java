package watch.fight.android.fightbrowser.Events.models;

import android.content.Context;
import android.net.Uri;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

import watch.fight.android.fightbrowser.Events.models.DB.BracketDB;
import watch.fight.android.fightbrowser.Utils.DateParser;

/**
 * Created by josh on 9/16/15.
 */
public class Event {

    @Expose
    @SerializedName("id")
    private long mId;

    @Expose
    @SerializedName("name")
    private String mEventName;

    @Expose
    @SerializedName("website")
    private String mWebsite;

    @Expose
    @SerializedName("start_date")
    private Long mStartDate;

    @Expose
    @SerializedName("end_date")
    private Long mEndDate;

    @Expose
    @SerializedName("header_image_url")
    private String mHeaderImageUrl;

    @Expose
    @SerializedName("flavor_text")
    private String mFlavorText;

    @Expose
    @SerializedName("streams")
    private List<String> mStreams;

    @Expose
    @SerializedName("brackets")
    private List<Bracket> mBrackets;

    public List<Bracket> getBrackets() {
        return mBrackets;
    }

    public List<Bracket> getStoredBrackets(Context c) {
        return BracketDB.getInstance(c).getBrackets(getId());
    }

    public Date getStartDateObj() { return DateParser.epochToDate(mStartDate); }

    public Date getEndDateObj() { return DateParser.epochToDate(mEndDate); }
    public Long getStartDate() { return mStartDate; }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getEventName() {
        return mEventName;
    }

    public void setEventName(String eventName) {
        mEventName = eventName;
    }

    public void setStartDate(Long startDate) {
        mStartDate = startDate;
    }

    public void setBrackets(List<Bracket> brackets) {
        mBrackets = brackets;
    }

    public List<String> getStreams() {
        return mStreams;
    }

    public void setStreams(List<String> streams) {
        mStreams = streams;
    }

    public Long getEndDate() {
        return mEndDate;
    }

    public void setEndDate(Long endDate) {
        mEndDate = endDate;
    }

    public String getHeaderImageUrl() {
        return mHeaderImageUrl;
    }

    public Uri getHeaderImageAsUri() {
        return Uri.parse(mHeaderImageUrl);
    }

    public void setHeaderImageUrl(String headerImageUrl) {
        mHeaderImageUrl = headerImageUrl;
    }

    public String getWebsite() {
        return mWebsite;
    }

    public Uri getWebsiteAsUri() {
        if (mWebsite != null) {
            return Uri.parse(mWebsite);
        } else {
            return null;
        }
    }

    public void setWebsite(String website) {
        mWebsite = website;
    }

    public String getFlavorText() {
        return mFlavorText;
    }

    public void setFlavorText(String flavorText) {
        mFlavorText = flavorText;
    }
}
