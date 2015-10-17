package watch.fight.android.fightbrowser.Brackets.events;

import android.support.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by josh on 10/17/15.
 */
public class ParticipantMenuEvent {
    public static final int REFRESH = 0;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({REFRESH})
    public @interface MenuOption {}

    @MenuOption private int MENU_OPTION;

    public ParticipantMenuEvent(@MenuOption int option) {
        this.MENU_OPTION = option;
    }
}
