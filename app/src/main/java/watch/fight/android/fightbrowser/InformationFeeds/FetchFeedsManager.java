package watch.fight.android.fightbrowser.InformationFeeds;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

import watch.fight.android.fightbrowser.InformationFeeds.models.Story;
import watch.fight.android.fightbrowser.Utils.NetworkUtils;

/**
 * Created by josh on 9/22/15.
 */
public class FetchFeedsManager {
    /**
     * Thread manager for work queues and thread pools used to download
     * RSS Feeds in parallel.
     */
    private static FetchFeedsManager mManager;
    private static int NUMBER_OF_COURES = Runtime.getRuntime().availableProcessors();
    private BlockingDeque<Runnable> mJobQueue;

    public static FetchFeedsManager getInstance() {
        if (mManager == null) {
            mManager = new FetchFeedsManager();
            mManager.mJobQueue = new LinkedBlockingDeque<Runnable>();
            return mManager;
        } else {
            return mManager;
        }
    }

    private FetchFeedsManager () {
    }
}
