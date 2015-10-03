package watch.fight.android.fightbrowser.InformationFeeds;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;

import watch.fight.android.fightbrowser.InformationFeeds.models.Feed;
import watch.fight.android.fightbrowser.Utils.Network.GsonRequest;

/**
 * Created by josh on 10/2/15.
 */
public class InformationFeedsRequestHandlers {
    private static Response.Listener<Feed> InformationFeedResponseListener(final ArrayList<Feed> feeds) {
        // NOTE!! Volley returns responses on the main thread, so keep this light.
        return new Response.Listener<Feed>() {
            @Override
            public void onResponse(Feed response) {
                feeds.add(response);
            }
        };
    }

    private static Response.ErrorListener informationFeedErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VolleyError", "Error Retrieving rss feed! - " + error.toString());
            }
        };
    }

    private static GsonRequest<Feed> createInformationFeedRequest(String url,
                                                                  ArrayList<Feed> feeds) {
        return new GsonRequest<>(
                url,
                Feed.class,
                null,
                InformationFeedResponseListener(feeds),
                informationFeedErrorListener()
        );
    }
}
