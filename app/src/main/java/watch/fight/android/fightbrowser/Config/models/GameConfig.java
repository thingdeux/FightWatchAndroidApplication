package watch.fight.android.fightbrowser.Config.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by josh on 9/16/15.
 */
public class GameConfig {

    @Expose
    @SerializedName("id")
    private Integer mId;

    @Expose
    @SerializedName("name")
    private String mGameName;

    @Expose
    @SerializedName("known_streamers")
    private ArrayList<String> mKnownStreamers;

    private Long mDateAdded;

    public String getGameName() {
        return mGameName;
    }

    public ArrayList<String> getKnownStreamers() {
        return mKnownStreamers;
    }

    public void setGameName(String gameName) {
        mGameName = gameName;
    }

    public void setKnownStreamers(ArrayList<String> knownStreamers) {
        mKnownStreamers = knownStreamers;
    }

    public Long getDateAdded() {
        return mDateAdded;
    }

    public void setDateAdded(Long dateAdded) {
        mDateAdded = dateAdded;
    }

    public Integer getId() {
        return mId;
    }

    public void setId(Integer id) {
        mId = id;
    }
}
