package watch.fight.android.fightbrowser.Events.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

import watch.fight.android.fightbrowser.Utils.DateParser;

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

    @Expose
    @SerializedName("streams")
    private List<String> mStream;

    @Expose
    @SerializedName("brackets")
    private List<ChallongeBracket> mBrackets;

    public List<ChallongeBracket> getBrackets() {
        // Try to acquire the brackets from the fgccompanion server else use challonge search api
        return null;
    }

    public Date getDate() {
        return DateParser.getDateFromStr(mDate);
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getEventName() {
        return mEventName;
    }

    public void setEventName(String eventName) {
        mEventName = eventName;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public void setBrackets(List<ChallongeBracket> brackets) {
        mBrackets = brackets;
    }

    public List<String> getStream() {
        return mStream;
    }

    public void setStream(List<String> stream) {
        mStream = stream;
    }
}
