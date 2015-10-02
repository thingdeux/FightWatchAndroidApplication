package watch.fight.android.fightbrowser.InformationFeeds.models.DB;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.net.Uri;

import watch.fight.android.fightbrowser.InformationFeeds.models.Story;
import watch.fight.android.fightbrowser.Utils.DateParser;

/**
 * Created by josh on 9/26/15.
 */
public class StoryCursorWrapper extends CursorWrapper {
    public StoryCursorWrapper(Cursor cursor) { super(cursor); }

    public Story getStory() {
        String siteName = getString(getColumnIndex(StoryDBSchema.StoryTable.Cols.SITE_NAME));
        String title = getString(getColumnIndex(StoryDBSchema.StoryTable.Cols.TITLE));
        String description = getString(getColumnIndex(StoryDBSchema.StoryTable.Cols.DESCRIPTION));
        String url = getString(getColumnIndex(StoryDBSchema.StoryTable.Cols.URL));
        String author = getString(getColumnIndex(StoryDBSchema.StoryTable.Cols.AUTHOR));
        Long published_date = getLong(getColumnIndex(StoryDBSchema.StoryTable.Cols.PUBLISHED_DATE));
        String last_updated = getString(getColumnIndex(StoryDBSchema.StoryTable.Cols.LAST_UPDATED));
        String thumbnail = getString(getColumnIndex(StoryDBSchema.StoryTable.Cols.THUMBNAIL));

        Story story = new Story();
        story.setSiteName(siteName);
        story.setTitle(title);
        story.setDescription(description);
        story.setUrl(Uri.parse(url));
        story.setAuthor(author);
        story.setPublishedDate(DateParser.epochToDate(published_date));
        story.setLastUpdated(last_updated);
        story.setThumbnail(thumbnail);

        return story;
    }
}
