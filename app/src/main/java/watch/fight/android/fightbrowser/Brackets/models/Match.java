package watch.fight.android.fightbrowser.Brackets.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by josh on 10/5/15.
 */
public class Match {
    @Expose
    @SerializedName("id")
    private Long mId;

    @Expose
    @SerializedName("tournament_id")
    private Long mTournamentId;

    @Expose
    @SerializedName("created_at")
    private String mCreatedAt;

    @Expose
    @SerializedName("started_at")
    private String mStartedAt;

    // Ex: 2015-01-19T16:57:17-05:00

    @Expose
    @SerializedName("identifier")
    private String mIdentifier;

    @Expose
    @SerializedName("player1_id")
    private String mPlayerOneId;

    @Expose
    @SerializedName("player2_id")
    private String mPlayerTwoId;

    @Expose
    @SerializedName("round")
    private int mRound;

    @Expose
    @SerializedName("state")
    private String mState;
    // Example Open / Done?

    @Expose
    @SerializedName("winner_id")
    private Long mWinnerId;

    @Expose
    @SerializedName("scores_csv")
    private String mScores;

    public Long getId() {
        return mId;
    }

    public Long getTournamentId() {
        return mTournamentId;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public String getStartedAt() {
        return mStartedAt;
    }

    public String getIdentifier() {
        return mIdentifier;
    }

    public String getPlayerOneId() {
        return mPlayerOneId;
    }

    public String getPlayerTwoId() {
        return mPlayerTwoId;
    }

    public int getRound() {
        return mRound;
    }

    public String getState() {
        return mState;
    }

    public Long getWinnerId() {
        return mWinnerId;
    }

    public String getScores() {
        return mScores;
    }
}
