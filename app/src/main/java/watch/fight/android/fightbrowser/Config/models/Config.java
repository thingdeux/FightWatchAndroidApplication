package watch.fight.android.fightbrowser.Config.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;

import watch.fight.android.fightbrowser.Events.models.Event;
import watch.fight.android.fightbrowser.InformationFeeds.models.Feed;

/**
 * Created by josh on 9/16/15.
 */
public class Config {
    @Expose
    @SerializedName("games")
    private ArrayList<GameConfig> mGames;

    @Expose
    @SerializedName("rss_feeds")
    private ArrayList<Feed> mFeeds;

    @Expose
    @SerializedName("events")
    private ArrayList<Event> mEvents;

    @Expose
    @SerializedName("updated")
    private String mLastUpdated;

    public ArrayList<GameConfig> getGames() {
        return mGames;
    }

    public ArrayList<Feed> getFeeds() {
        return mFeeds;
    }

    public ArrayList<Event> getEvents() {
        return mEvents;
    }

    public String getLastUpdated() {
        return mLastUpdated;
    }
}
