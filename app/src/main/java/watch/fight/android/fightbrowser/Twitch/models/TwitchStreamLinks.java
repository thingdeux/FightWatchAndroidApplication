package watch.fight.android.fightbrowser.Twitch.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by josh on 9/11/15.
 */
public class TwitchStreamLinks {
    @Expose
    @SerializedName("self")
    private String self;

    @Expose
    @SerializedName("next")
    private String next;

    public String getSelf() { return self; }

    public String getNext() { return next; }

}
