package watch.fight.android.fightbrowser.Twitch;

import com.google.gson.annotations.Expose;

/**
 * Created by josh on 9/11/15.
 */
public class TwitchPreviewImageInfo {
    @Expose
    private String small;

    @Expose
    private String medium;

    @Expose
    private String large;

    @Expose
    private String template;

    public String getSmall() {
        return small;
    }

    public String getMedium() {
        return medium;
    }

    public String getLarge() {
        return large;
    }

    public String getTemplate() {
        return template;
    }
}
