package watch.fight.android.fightbrowser.Twitch;

import com.google.gson.annotations.Expose;

/**
 * Created by josh on 9/11/15.
 */
public class TwitchStream {
    @Expose
    private TwitchStreamLinks _links;

    @Expose
    private TwitchFeaturedStream[] featured;

    public TwitchStreamLinks get_links() {
        return _links;
    }

    public TwitchFeaturedStream[] getFeatured() {
        return featured;
    }
}