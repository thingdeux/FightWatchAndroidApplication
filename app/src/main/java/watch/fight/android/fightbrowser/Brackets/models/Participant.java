package watch.fight.android.fightbrowser.Brackets.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by josh on 10/5/15.
 */
public class Participant {
    @Expose
    @SerializedName("id")
    private Long mId;

    @Expose
    @SerializedName("active")
    private Boolean mIsActive;

    @Expose
    @SerializedName("username")
    private String mUsername;

    @Expose
    @SerializedName("name")
    private String mName;

    @Expose
    @SerializedName("tournament_id")
    private Long mTournamentId;

    public Long getId() {
        return mId;
    }

    public Boolean getIsActive() {
        return mIsActive;
    }

    public String getUsername() {
        return mUsername;
    }

    public String getName() {
        return mName;
    }

    public Long getTournamentId() {
        return mTournamentId;
    }
}
