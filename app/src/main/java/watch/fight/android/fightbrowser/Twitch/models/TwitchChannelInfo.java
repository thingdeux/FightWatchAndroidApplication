package watch.fight.android.fightbrowser.Twitch.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by josh on 9/11/15.
 */
public class TwitchChannelInfo {
    @Expose
    @SerializedName("mature")
    private Boolean isMature;

    @Expose
    private String status;

    @Expose
    private String broadcaster_language;

    @Expose
    private String display_name;

    @Expose
    private String game;

    @Expose
    private String delay;

    @Expose
    private String _id;

    @Expose
    private String name;

    @Expose
    private String created_at;

    @Expose
    private String updated_at;

    @Expose
    private String logo;

    @Expose
    private String banner;

    @Expose
    private String video_banner;

    @Expose
    private String profile_banner;

    @Expose
    private String url;

    @Expose
    private int views = 0;

    @Expose
    private int followers = 0;

    public Boolean isMature() {
        return isMature;
    }

    public String getStatus() {
        return status;
    }

    public String getBroadcaster_language() {
        return broadcaster_language;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public String getGame() {
        return game;
    }

    public String getDelay() {
        return delay;
    }

    public String get_id() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public String getLogo() {
        return logo;
    }

    public String getBanner() {
        return banner;
    }

    public String getVideo_banner() {
        return video_banner;
    }

    public String getProfile_banner() {
        return profile_banner;
    }

    public String getUrl() {
        return url;
    }

    public int getViews() {
        return views;
    }

    public int getFollowers() {
        return followers;
    }
}
