package watch.fight.android.fightbrowser.Config.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import watch.fight.android.fightbrowser.Events.models.Event;
import watch.fight.android.fightbrowser.InformationFeeds.Feed;

/**
 * Created by josh on 9/16/15.
 */
public class Config {
    @Expose
    @SerializedName("rss_feeds")
    private ArrayList<Feed> mFeeds;

    @Expose
    @SerializedName("events")
    private ArrayList<Event> mEvents;
}
