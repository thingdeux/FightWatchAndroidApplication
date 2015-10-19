package watch.fight.android.fightbrowser.Brackets;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import watch.fight.android.fightbrowser.Brackets.Network.BracketSubmission;
import watch.fight.android.fightbrowser.Events.models.DB.EventDB;
import watch.fight.android.fightbrowser.Events.models.Event;
import watch.fight.android.fightbrowser.R;

/**
 * Created by josh on 10/19/15.
 */
public class BracketSearchActivity extends AppCompatActivity
        implements Response.Listener<String>, Response.ErrorListener {
    public static final String TAG = BracketSearchActivity.class.getSimpleName();
    public static final String BRACKET_EVENT_ID = "watch.fight.android.fightbrowser.bracket.bracketsearchactivity.event_id";
    private Context mContext;
    private Event mEvent;
    private WebView mWebView;
    private FloatingActionButton mFloatingAddButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Long eventId = intent.getLongExtra(BRACKET_EVENT_ID, -1);
        if (eventId > 0) {
            mEvent = EventDB.getInstance(this).getEvent(eventId);
        }
        setContentView(R.layout.bracket_search_activity);

        mFloatingAddButton = (FloatingActionButton) findViewById(R.id.bracket_add_floating_button);

        mWebView = (WebView) findViewById(R.id.search_webview);
        mWebView.loadUrl("http://challonge.com/tournaments");
        mWebView.getSettings().setJavaScriptEnabled(false);
        mContext = this;
        setupWebViewClient();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ActionBar ap = getSupportActionBar();
        if (ap != null) {
            ap.setElevation(0);
        }
        mFloatingAddButton.setEnabled(false);
        mFloatingAddButton.hide();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public static Intent NewInstance(Context activity, Long eventId) {
        Intent intent = new Intent(activity, BracketSearchActivity.class);
        intent.putExtra(BRACKET_EVENT_ID, eventId);
        return intent;
    }

    public void setupWebViewClient() {
        WebViewClient client = new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (BracketSubmission.isValidSearchQuery(url)) {
                    mWebView.loadUrl(url);
                } else if (BracketSubmission.isValidChallongeBracket(url, mContext)) {
                    Log.i(TAG, "Checking url: " + url);
                }
                return true;
            }
        };
        mWebView.setWebViewClient(client);
    }

    // These callbacks are hit after a link is clicked on the Challonge page in the webview.
    // The link attempts to parse out and hit a tournament api call, if it can success is returned.
    // And the floating action button is displayed. If not error.
    @Override
    public void onResponse(String response) {
        mFloatingAddButton.setEnabled(true);
        mFloatingAddButton.show();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e(TAG, "Bracket Parse Error: " + error);
        mFloatingAddButton.setEnabled(false);
        mFloatingAddButton.hide();
    }
}
