package watch.fight.android.fightbrowser.InformationFeeds.models.DB;

import java.util.Date;

import watch.fight.android.fightbrowser.Utils.DateParser;

/**
 * Created for the purpose of marking certain stories as read and filtering them.
 * There's no need to store more than 300 rows of these.
 * Will serialize into a cheaply searchable hashset and filter on !contains
 */
public class StoryTracker {
    private String mUrl;
    private Date mDateAdded;

    public StoryTracker() {}

    public StoryTracker(String url) {
        setUrl(url);
        setDateAdded(System.currentTimeMillis());
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public Date getDateAdded() {
        return mDateAdded;
    }

    public void setDateAdded(Long dateAdded) {
        mDateAdded = DateParser.epochToDate(dateAdded);
    }
}
