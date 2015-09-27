package watch.fight.android.fightbrowser.InformationFeeds;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import watch.fight.android.fightbrowser.R;
import watch.fight.android.fightbrowser.StreamBrowser.BrowserActivity;

/**
 * Created by josh on 9/16/15.
 */
public class InformationFeedsActivity extends AppCompatActivity {
    private static final String TAG = InformationFeedsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_activity);

        new FetchFeeds.FetchStories(this).execute();

        if (savedInstanceState == null) {
            InformationFeedsFragment informationFeedsFragment = new InformationFeedsFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.dashboard_main_fragment, informationFeedsFragment).commit();
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
                return true;
            case R.id.action_preferences:
                return true;
            case R.id.action_events:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
