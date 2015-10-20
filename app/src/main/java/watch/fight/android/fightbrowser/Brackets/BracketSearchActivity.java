package watch.fight.android.fightbrowser.Brackets;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.android.volley.VolleyError;

import watch.fight.android.fightbrowser.Brackets.Network.BracketSubmission;
import watch.fight.android.fightbrowser.Brackets.models.Tournament;
import watch.fight.android.fightbrowser.Brackets.models.TournamentWrapper;
import watch.fight.android.fightbrowser.Events.models.Bracket;
import watch.fight.android.fightbrowser.Events.models.DB.BracketDB;
import watch.fight.android.fightbrowser.Events.models.DB.EventDB;
import watch.fight.android.fightbrowser.Events.models.Event;
import watch.fight.android.fightbrowser.R;
import watch.fight.android.fightbrowser.Utils.Network.IVolleyResponse;
import watch.fight.android.fightbrowser.Utils.StringUtils;

/**
 * Created by josh on 10/19/15.
 */
public class BracketSearchActivity extends AppCompatActivity
        implements IVolleyResponse<TournamentWrapper> {
    public static final String TAG = BracketSearchActivity.class.getSimpleName();
    public static final String BRACKET_EVENT_ID = "watch.fight.android.fightbrowser.bracket.bracketsearchactivity.event_id";
    private Context mContext;
    private Event mEvent;
    private WebView mWebView;
    private FloatingActionButton mEnabledFAB;
    private FloatingActionButton mDisabledFAB;
    private TournamentWrapper foundTournament;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Long eventId = intent.getLongExtra(BRACKET_EVENT_ID, -1);
        if (eventId > 0) {
            mEvent = EventDB.getInstance(this).getEvent(eventId);
        }
        setContentView(R.layout.bracket_search_activity);

        mDisabledFAB = (FloatingActionButton) findViewById(R.id.bracket_add_floating_button_disabled);
        mEnabledFAB = (FloatingActionButton) findViewById(R.id.bracket_add_floating_button);
        mEnabledFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (foundTournament != null && mEvent != null) {
                    BracketDB.getInstance(mContext).addUserBracket(foundTournament, mEvent);
                    if (foundTournament.getTournament().getName() != null) {
                        String tournamentName = StringUtils.limitCharacters(foundTournament.getTournament().getName(), 35, true);
                        String toastMessage = String.format(getResources().getString(R.string.bracket_added_toast_message), tournamentName);
                        Toast.makeText(mContext, toastMessage, Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        mWebView = (WebView) findViewById(R.id.search_webview);
        mWebView.loadUrl("http://challonge.com/tournaments?q=");
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
        setFABState(false);
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
                } else {
                    Log.v(TAG, "Checking url: " + url);
                    BracketSubmission.checkIfValidChallongeBracket(url, mContext);
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
    public void onSuccess(TournamentWrapper response) {
        if (response.getTournament() != null && response.getTournament().getName() != null) {
            Log.i(TAG, "Found Tournament: " + response.getTournament().getName());
        }
        foundTournament = response;
        setFABState(true);
    }

    @Override
    public void onFailure(VolleyError error) {
        Log.e(TAG, "Bracket Parse Error: " + error);
        setFABState(false);
    }

    private void setFABState(boolean isEnabled) {
        if (isEnabled) {
            mDisabledFAB.hide();
            mEnabledFAB.show();
        } else {
            mDisabledFAB.show();
            mEnabledFAB.hide();
        }

    }

}
