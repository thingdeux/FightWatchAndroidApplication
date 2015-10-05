package watch.fight.android.fightbrowser.Brackets.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by josh on 10/5/15.
 */
public class MatchWrapper {
    /* Challonge has each object wrapped in a sub object
       Ex: {
          "participant": {
            "id",
            "name"
          }
       This is the wrapper
     */

    @Expose
    @SerializedName("matches")
    List<Match> mMatches;

    @Expose
    @SerializedName("participant")
    Match mMatch;
}
