package watch.fight.android.fightbrowser.Brackets.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by josh on 10/5/15.
 */
public class Tournament {
    @Expose
    @SerializedName("created_at")
    private String mCreatedAt;

    @Expose
    @SerializedName("completed_at")
    private String mCompletedAt;

    // FORMAT: "2015-01-19T16:47:30-05:00"

    @Expose
    @SerializedName("description")
    private String mDescription;

    @Expose
    @SerializedName("name")
    private String mName;

    @Expose
    @SerializedName("game_id")
    private String mGameId;

    @Expose
    @SerializedName("id")
    private Long mId;

    @Expose
    @SerializedName("participants_count")
    private Long mParticipantsCount;

    @Expose
    @SerializedName("state")
    private String mState;

    @Expose
    @SerializedName("subdomain")
    private String mSubdomain;

    @Expose
    @SerializedName("full_challonge_url")
    private String mFullChallongeUrl;

    @Expose
    @SerializedName("live_image_url")
    private String mLiveImageUrl;

    @Expose
    @SerializedName("game_name")
    private String mGameName;

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public String getCompletedAt() {
        return mCompletedAt;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getName() {
        return mName;
    }

    public String getGameId() {
        return mGameId;
    }

    public Long getId() {
        return mId;
    }

    public Long getParticipantsCount() {
        return mParticipantsCount;
    }

    public String getState() {
        return mState;
    }

    public String getSubdomain() {
        return mSubdomain;
    }

    public String getFullChallongeUrl() {
        return mFullChallongeUrl;
    }

    public String getLiveImageUrl() {
        return mLiveImageUrl;
    }

    public String getGameName() {
        return mGameName;
    }
}
