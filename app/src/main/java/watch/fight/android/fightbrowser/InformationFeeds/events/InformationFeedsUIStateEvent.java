package watch.fight.android.fightbrowser.InformationFeeds.events;

/**
 * Created by Josh on 10/20/15.
 */
public class InformationFeedsUIStateEvent {
    public static final int LOADING = 0;
    public static final int NORMAL = 1;
    public static final int NO_NEW_FEEDS = 2;

    public int stateToSet = 1;

    public InformationFeedsUIStateEvent(int state) {
        stateToSet = state;
    }
}
