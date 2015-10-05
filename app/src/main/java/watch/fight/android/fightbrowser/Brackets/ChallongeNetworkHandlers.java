package watch.fight.android.fightbrowser.Brackets;

import android.content.Context;
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

import watch.fight.android.fightbrowser.Brackets.models.Tournament;
import watch.fight.android.fightbrowser.InformationFeeds.models.Feed;
import watch.fight.android.fightbrowser.Utils.Network.NetworkRequest;


/**
 * Created by josh on 10/5/15.
 */
//public class ChallongeNetworkHandlers {
//    private static Response.Listener<Tournament> TournamentListener(final Context context) {
//        return new Response.Listener<Tournament>() {
//            @Override
//            public void onResponse(RSSFeed response) {
//                Log.v("VolleyResponse", "Got Response");
//            }
//        };
//    }
//
//    private static Response.ErrorListener informationFeedErrorListener(final String url, final Context context) {
//        return new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                NetworkRequest.getInstance(context.getApplicationContext()).IncrementPendingRssRequests();
//                Log.e("VolleyError", "Error Retrieving rss feed (" + url + ")! - " + error.toString());
//            }
//        };
//    }
//
//    public static VolleyRSSRequest<RSSFeed> createInformationFeedRequest(Feed feed, Context context) {
//        NetworkRequest.getInstance(context.getApplicationContext()).IncrementPendingRssRequests();
//        return new VolleyRSSRequest<>(
//                feed.getRSSUrl(),
//                RSSFeed.class,
//                InformationFeedResponseListener(feed, context),
//                informationFeedErrorListener(feed.getRSSUrl(), context)
//        );
//    }
//
//    public static class ChallongeTournamentRequest<T> extends Request<T> {
//
//        private final Class<T> clazz;
//        private final Map<String, String> headers;
//        private final Response.Listener<T> listener;
//
//        public VolleyRSSRequest(String url, Class<T> clazz,
//                                Response.Listener<T> listener, Response.ErrorListener errorListener) {
//            this(url, clazz, null, listener, errorListener);
//        }
//
//        public VolleyRSSRequest(String url, Class<T> clazz, Map<String, String> headers,
//                                Response.Listener<T> listener, Response.ErrorListener errorListener) {
//            super(Request.Method.GET, url, errorListener);
//            this.clazz = clazz;
//            this.headers = headers;
//            this.listener = listener;
//        }
//
//
//        @Override
//        public Map<String, String> getHeaders() throws AuthFailureError {
//            Map headers = new HashMap();
//
//            // Add custom header for reddit rss feeds
//            String userAgent = "android:watch.fight.android.fightbrowser:v0.1.3 (by /u/thingdeux)";
//            headers.put(CoreProtocolPNames.USER_AGENT, userAgent);
//            return headers;
//        }
//
//
//        @Override
//        protected void deliverResponse(T response) {
//            listener.onResponse(response);
//        }
//
//        @Override
//        protected Response<T> parseNetworkResponse(NetworkResponse response)
//        {
//            try {
//
//                String data = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
//                return Response.success(parseFeed(data, clazz),
//                        HttpHeaderParser.parseCacheHeaders(response));
//            }
//            catch (UnsupportedEncodingException e) {
//                return Response.error(new ParseError(e));
//            }
//            catch (Exception e) {
//                return Response.error(new VolleyError(e.getMessage()));
//            }
//        }
//
//        private <T> T parseFeed(String response, Class<T> T) {
//            return T.cast(mParser.parse(response));
//        }
//}
