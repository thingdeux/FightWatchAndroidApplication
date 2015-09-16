package watch.fight.android.fightbrowser.StreamBrowser.Twitch;

import android.util.Log;

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

    public String getSmallImage() {
        return small;
    }

    public String getMediumImage() {
        return medium;
    }

    public String getLargeImage() {
        return large;
    }

    public String getTemplateImage() {
        return template;
    }

    public String getTemplateImage(int width, int height) {
        String templateToModify = template;
        templateToModify = templateToModify.replace("{width}", "" + width);
        templateToModify = templateToModify.replace("{height}", "" + height);
        Log.d("StringReplace", templateToModify);
        return templateToModify;

    }
}
