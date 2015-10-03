package watch.fight.android.fightbrowser.InformationFeeds;

import android.content.Context;
import android.os.Looper;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;

import org.apache.http.params.CoreProtocolPNames;
import org.mcsoxford.rss.RSSConfig;
import org.mcsoxford.rss.RSSFeed;
import org.mcsoxford.rss.RSSParser;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import watch.fight.android.fightbrowser.InformationFeeds.models.DB.StoryDB;
import watch.fight.android.fightbrowser.InformationFeeds.models.Feed;
import watch.fight.android.fightbrowser.InformationFeeds.models.Story;
import watch.fight.android.fightbrowser.Utils.Network.GsonRequest;
import watch.fight.android.fightbrowser.Utils.Network.ParseUtils;

/**
 * Created by josh on 10/2/15.
 */
public class InformationFeedsNetworkHandlers {
    private static Response.Listener<RSSFeed> InformationFeedResponseListener(final Feed parentFeed, final Context context) {
        // NOTE!! Volley returns responses on the main thread, so keep this light.
        // TODO : Profile this under extreme cases - may not want this done on the UI Thread.
        return new Response.Listener<RSSFeed>() {
            @Override
            public void onResponse(RSSFeed response) {
                Log.v("VolleyResponse", "Got Response");
                ArrayList<Story> stories = ParseUtils.parseRSS(response, parentFeed);
                if (stories != null) {
                    StoryDB DB = StoryDB.getInstance(context.getApplicationContext());
                    // Delete all stories from the given site and add the new updates
                    // will only delete if the feed has been succesfully gathered
                    DB.deleteStoriesBySiteName(parentFeed.getName());
                    DB.addStories(stories);
                } else {
                    Log.e("ProcessFeed", "Received error on " + parentFeed.getName());
                }
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

    public static VolleyRSSRequest<RSSFeed> createInformationFeedRequest(Feed feed, Context context) {
        return new VolleyRSSRequest<>(
                feed,
                RSSFeed.class,
                InformationFeedResponseListener(feed, context),
                informationFeedErrorListener()
        );
    }

    public static class VolleyRSSRequest<T> extends Request<T> {

        private static final RSSParser mParser = new RSSParser(new RSSConfig());
        private final Class<T> clazz;
        private final Map<String, String> headers;
        private final Response.Listener<T> listener;
        private Feed mFeed;

        public VolleyRSSRequest(Feed feed, Class<T> clazz,
                                Response.Listener<T> listener, Response.ErrorListener errorListener) {
            this(feed, clazz, null, listener, errorListener);
        }

        public VolleyRSSRequest(Feed feed, Class<T> clazz, Map<String, String> headers,
                                Response.Listener<T> listener, Response.ErrorListener errorListener) {
            super(Request.Method.GET, feed.getRSSUrl(), errorListener);
            this.mFeed = feed;
            this.clazz = clazz;
            this.headers = headers;
            this.listener = listener;
        }


        @Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            Map headers = new HashMap();

            // Add custom header for reddit rss feeds
            String userAgent = "android:watch.fight.android.fightbrowser:v0.1.3 (by /u/thingdeux)";
            headers.put(CoreProtocolPNames.USER_AGENT, userAgent);
            return headers;
        }


        @Override
        protected void deliverResponse(T response) {
            listener.onResponse(response);
        }

        @Override
        protected Response<T> parseNetworkResponse(NetworkResponse response)
        {
            try {

                String data = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                return Response.success(parseFeed(data, clazz),
                        HttpHeaderParser.parseCacheHeaders(response));
            }
            catch (UnsupportedEncodingException e) {
                return Response.error(new ParseError(e));
            }
            catch (Exception e) {
                return Response.error(new VolleyError(e.getMessage()));
            }
        }

        private <T> T parseFeed(String response, Class<T> T) {
            return T.cast(mParser.parse(response));
        }
    }
}
