package watch.fight.android.fightbrowser.Config.events;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by josh on 10/15/15.
 */
public class PreferenceToggleEvent {
    public static final int ENABLE_REORDER = 0;
    public static final int ENABLE_TOGGLE = 1;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({ENABLE_REORDER, ENABLE_TOGGLE})
    public @interface PreferenceToggle {}

    @PreferenceToggle private int BUTTON_TO_ACTIVATE;

    public PreferenceToggleEvent(@PreferenceToggle int toEnable) {
        this.BUTTON_TO_ACTIVATE = toEnable;
    }

}
