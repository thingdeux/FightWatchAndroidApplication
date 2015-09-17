package watch.fight.android.fightbrowser.Events.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by josh on 9/16/15.
 */
public class ChallongeBracket {

    @Expose
    @SerializedName("bracket_name")
    private String mBracketName;

    @Expose
    @SerializedName("bracket_url")
    private String mBracketUrl;

    public String getBracketName() {
        return mBracketName;
    }

    public String getBracketUrl() {
        return mBracketUrl;
    }
}
