package watch.fight.android.fightbrowser.Config.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by josh on 9/16/15.
 */
public class GameConfig {

    @Expose
    @SerializedName("name")
    private String mGameName;

    @Expose
    @SerializedName("known_streamers")
    private ArrayList<String> mKnownStreamers;

    public String getGameName() {
        return mGameName;
    }

    public ArrayList<String> getKnownStreamers() {
        return mKnownStreamers;
    }
}
