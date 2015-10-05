package watch.fight.android.fightbrowser.Config.models;

import android.content.Context;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import watch.fight.android.fightbrowser.StreamBrowser.models.Streamer;
import watch.fight.android.fightbrowser.StreamBrowser.models.StreamerDB;

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

    private Boolean isFiltered;

    private Long mDateAdded;

    private int mOrdinal;

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

    public List<Streamer> getKnownStreamersFromDB (Context context) {
        return StreamerDB.getInstance(context).getStreamers(getId());
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

    public Boolean getIsFiltered() {
        return isFiltered;
    }

    public void setIsFiltered(Boolean isFiltered) {
        this.isFiltered = isFiltered;
    }

    public int getOrdinal() {
        return mOrdinal;
    }

    public void setOrdinal(int ordinal) {
        mOrdinal = ordinal;
    }
}
