package watch.fight.android.fightbrowser.InformationFeeds;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;

import org.mcsoxford.rss.RSSConfig;
import org.mcsoxford.rss.RSSFeed;
import org.mcsoxford.rss.RSSParser;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Map;

import watch.fight.android.fightbrowser.InformationFeeds.models.Feed;
import watch.fight.android.fightbrowser.Utils.Network.GsonRequest;

/**
 * Created by josh on 10/2/15.
 */
public class InformationFeedsNetworkHandlers {
    private static Response.Listener<RSSFeed> InformationFeedResponseListener() {
        // NOTE!! Volley returns responses on the main thread, so keep this light.
        return new Response.Listener<RSSFeed>() {
            @Override
            public void onResponse(RSSFeed response) {
                Log.i("VolleyResponse", "Got Response");
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

    public static VolleyRSSRequest<RSSFeed> createInformationFeedRequest(String url) {
        return new VolleyRSSRequest<>(
                url,
                RSSFeed.class,
                InformationFeedResponseListener(),
                informationFeedErrorListener()
        );
    }

    public static class VolleyRSSRequest<T> extends Request<T> {

        private static final RSSParser mParser = new RSSParser(new RSSConfig());
        private final Class<T> clazz;
        private final Map<String, String> headers;
        private final Response.Listener<T> listener;

        public VolleyRSSRequest(String url, Class<T> clazz,
                                Response.Listener<T> listener, Response.ErrorListener errorListener) {
            this(url, clazz, null, listener, errorListener);
        }

        public VolleyRSSRequest(String url, Class<T> clazz, Map<String, String> headers,
                                Response.Listener<T> listener, Response.ErrorListener errorListener) {
            super(Request.Method.GET, url, errorListener);
            this.clazz = clazz;
            this.headers = headers;
            this.listener = listener;
        }


        @Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            return headers != null ? headers : super.getHeaders();
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
