package watch.fight.android.fightbrowser.Dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

import watch.fight.android.fightbrowser.Config.PreferencesActivity;
import watch.fight.android.fightbrowser.Events.EventsActivity;
import watch.fight.android.fightbrowser.InformationFeeds.InformationFeedsActivity;
import watch.fight.android.fightbrowser.StreamBrowser.BrowserActivity;
import watch.fight.android.fightbrowser.R;

/**
 * Created by josh on 9/14/15.
 */
public class DashboardActivity extends AppCompatActivity {
    private static final String TAG = DashboardActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());  // Crashlytics Init - Don't Remove

        setContentView(R.layout.dashboard_activity);

        if (savedInstanceState == null) {
            DashboardFragment dashboardFragment = new DashboardFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.dashboard_main_fragment, dashboardFragment).commit();
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

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_dashboard, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_streams:
                startActivity(new Intent(this, BrowserActivity.class));
                return true;
            case R.id.action_news:
                startActivity(new Intent(this, InformationFeedsActivity.class));
                return true;
            case R.id.action_preferences:
                startActivity(new Intent(this, PreferencesActivity.class));
                return true;
            case R.id.action_events:
                startActivity(new Intent(this, EventsActivity.class));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
