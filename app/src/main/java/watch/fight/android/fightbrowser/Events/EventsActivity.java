package watch.fight.android.fightbrowser.Events;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import watch.fight.android.fightbrowser.Brackets.ChallongeAPI;
import watch.fight.android.fightbrowser.Brackets.models.Tournament;
import watch.fight.android.fightbrowser.Brackets.models.TournamentWrapper;
import watch.fight.android.fightbrowser.InformationFeeds.InformationFeedsFragment;
import watch.fight.android.fightbrowser.R;
import watch.fight.android.fightbrowser.Utils.Network.GsonRequest;
import watch.fight.android.fightbrowser.Utils.Network.NetworkRequest;

/**
 * Created by josh on 9/16/15.
 */
public class EventsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.events_activity);

        if (savedInstanceState == null) {
            EventsFragment eventsFragment = new EventsFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.events_fragment, eventsFragment).commit();
        }

    }

    private Response.Listener<TournamentWrapper> ChallongeSuccessListener() {
        return new Response.Listener<TournamentWrapper>() {
            @Override
            public void onResponse(TournamentWrapper response) {
                Log.i("JJDEBUG", "Got Response");
                Log.i("JJDEBUG", "Name " + response.getTournament().getName());
            }
        };
    }

    private Response.ErrorListener ChallongeErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("JJDEBUG", "Got Error: " + error);
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        ChallongeAPI api = ChallongeAPI.getInstance(this.getApplicationContext());
        RequestQueue queue = NetworkRequest.getInstance(this.getApplicationContext()).getRequestQueue();
        queue.add(new GsonRequest<TournamentWrapper>(
                        api.getTournamentUri("nextlevel-nlbc140usf4", false, false).toString(),
                        TournamentWrapper.class,
                        null,
                        ChallongeSuccessListener(),
                        ChallongeErrorListener()
                ));
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

}
