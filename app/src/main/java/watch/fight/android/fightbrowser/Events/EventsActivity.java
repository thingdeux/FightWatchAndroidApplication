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

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

}
