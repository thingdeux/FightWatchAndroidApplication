package watch.fight.android.fightbrowser.InformationFeeds.models.DB;

import android.database.Cursor;
import android.database.CursorWrapper;

/**
 * Created by josh on 10/8/15.
 */
public class StoryTrackerCursorWrapper extends CursorWrapper {
    public StoryTrackerCursorWrapper(Cursor cursor) { super(cursor); }

    public StoryTracker getStoryTracker() {
        String url = getString(getColumnIndex(StoryTrackerDBSchema.StoryTrackerTable.Cols.URL));
        Long published_date = getLong(getColumnIndex(StoryTrackerDBSchema.StoryTrackerTable.Cols.DATE_ADDED));

        StoryTracker storyTracker = new StoryTracker();
        storyTracker.setUrl(url);
        storyTracker.setDateAdded(published_date);

        return storyTracker;
    }
}
