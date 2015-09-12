package watch.fight.android.fightbrowser.Twitch;

import com.google.gson.annotations.Expose;

/**
 * Created by josh on 9/11/15.
 */
public class TwitchFeaturedStream {
    @Expose
    private String image;

    @Expose
    private String text;

    @Expose
    private String title;

    @Expose
    private TwitchStreamInfo stream;

    public String getImage() {
        return image;
    }

    public String getText() {
        return text;
    }

    public String getTitle() {
        return title;
    }

    public TwitchStreamInfo getStream() {
        return stream;
    }
}
