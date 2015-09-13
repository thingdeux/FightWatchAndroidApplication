package watch.fight.android.fightbrowser.Twitch.models;

import com.google.gson.annotations.Expose;

import java.math.BigInteger;

import watch.fight.android.fightbrowser.Twitch.TwitchPreviewImageInfo;

/**
 * Created by josh on 9/11/15.
 */
public class TwitchStreamInfo {
    @Expose
    private String game;

    @Expose
    private int viewers;

    @Expose
    private String created_at;

    @Expose
    private int video_height;

    @Expose
    private BigInteger _id;

    @Expose
    private TwitchChannelInfo channel;

    @Expose
    private TwitchPreviewImageInfo preview;

    @Expose
    private TwitchStreamLinks _links;

    public String getGame() {
        return game;
    }

    public int getViewers() {
        return viewers;
    }

    public String getCreated_at() {
        return created_at;
    }

    public int getVideo_height() {
        return video_height;
    }

    public BigInteger get_id() {
        return _id;
    }

    public TwitchChannelInfo getChannel() {
        return channel;
    }

    public TwitchPreviewImageInfo getPreview() {
        return preview;
    }

    public TwitchStreamLinks get_links() {
        return _links;
    }
}
