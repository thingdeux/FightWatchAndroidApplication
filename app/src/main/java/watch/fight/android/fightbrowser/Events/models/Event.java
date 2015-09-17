package watch.fight.android.fightbrowser.Events.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.sql.Date;
import java.util.List;

/**
 * Created by josh on 9/16/15.
 */
public class Event {

    @Expose
    @SerializedName("id")
    private long mId;

    @Expose
    @SerializedName("event_name")
    private String mEventName;

    @Expose
    @SerializedName("date")
    private String mDate;

    private List<ChallongeBracket> mBrackets;

    private List<ChallongeBracket> getBrackets() {
        // Try to acquire the brackets from the fgccompanion server else use challonge search api
        return null;
    }

}
