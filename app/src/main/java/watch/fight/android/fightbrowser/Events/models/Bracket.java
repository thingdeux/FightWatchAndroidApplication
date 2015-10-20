package watch.fight.android.fightbrowser.Events.models;

import android.content.Context;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import watch.fight.android.fightbrowser.Brackets.models.Tournament;
import watch.fight.android.fightbrowser.Brackets.models.TournamentWrapper;
import watch.fight.android.fightbrowser.Events.models.DB.BracketDB;

/**
 * Created by josh on 9/16/15.
 */
public class Bracket {

    @Expose
    @SerializedName("bracket_name")
    private String mBracketName;

    @Expose
    @SerializedName("bracket_url")
    private String mBracketUrl;

    @Expose
    @SerializedName("is_verified")
    private Boolean mIsVerified;

    @Expose
    @SerializedName("bracket_type")
    private String mBracketType;

    private Boolean mIsUserAdded;

    private Long mRelatedEvent;

    private Integer mId;

    public Boolean getIsVerified() {
        return mIsVerified;
    }

    public void setIsVerified(Boolean isVerified) {
        mIsVerified = isVerified;
    }

    public Integer getId() {
        return mId;
    }

    public void setId(Integer id) {
        mId = id;
    }

    public Long getRelatedEvent() {
        return mRelatedEvent;
    }

    public void setRelatedEvent(Long relatedEvent) {
        mRelatedEvent = relatedEvent;
    }

    public String getBracketName() {
        return mBracketName;
    }

    public String getBracketUrl() {
        return mBracketUrl;
    }

    public void setBracketName(String bracketName) {
        mBracketName = bracketName;
    }

    public void setBracketUrl(String bracketUrl) {
        mBracketUrl = bracketUrl;
    }

    public String getBracketType() {
        return mBracketType;
    }

    public void setBracketType(String bracketType) {
        mBracketType = bracketType;
    }

    public Boolean getIsUserAdded() {
        return mIsUserAdded;
    }

    public void setIsUserAdded(Boolean isUserAdded) {
        mIsUserAdded = isUserAdded;
    }

}
