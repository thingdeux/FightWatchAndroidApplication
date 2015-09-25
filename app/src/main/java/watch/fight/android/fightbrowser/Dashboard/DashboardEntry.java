package watch.fight.android.fightbrowser.Dashboard;

import android.net.Uri;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by josh on 9/15/15.
 */
public class DashboardEntry {
    public static int RSS_FEED_TYPE = 0;
    public static int TWITCH_STREAM_COUNT = 1;
    public static int EVENT_TYPE = 1;

    private String mHeader;
    private String mContent;
    private int mType;
    private Uri mFeedHeaderImage;


    public String getHeader() {
        return mHeader;
    }

    public void setHeader(String header) {
        mHeader = header;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public int getType() {
        return mType;
    }

    public void setType(int type) {
        mType = type;
    }

    public Uri getFeedHeaderImage() {
        return mFeedHeaderImage;
    }

    public void setFeedHeaderImage(Uri feedHeaderImage) {
        mFeedHeaderImage = feedHeaderImage;
    }
}
