package watch.fight.android.fightbrowser.Events.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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
    @SerializedName("bracket_type")
    private String mBracketType;

    private Long mRelatedEvent;

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
}
