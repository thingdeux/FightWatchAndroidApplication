package watch.fight.android.fightbrowser.Dashboard.events;

/**
 * Created by josh on 10/20/15.
 */
public class DashboardUIStateEvent {
    public static final int LOADING = 0;
    public static final int READY = 1;
    public int uiState;

    public DashboardUIStateEvent(int stateToSet) {
        this.uiState = stateToSet;
    }
}
