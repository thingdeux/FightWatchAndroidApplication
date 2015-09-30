package watch.fight.android.fightbrowser.Events;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import watch.fight.android.fightbrowser.InformationFeeds.FetchFeeds;
import watch.fight.android.fightbrowser.InformationFeeds.InformationFeedsFragment;
import watch.fight.android.fightbrowser.R;

/**
 * Created by josh on 9/16/15.
 */
public class EventsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.information_feed_activity);

        if (savedInstanceState == null) {
            InformationFeedsFragment informationFeedsFragment = new InformationFeedsFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.information_feed_main_fragment, informationFeedsFragment).commit();
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
        inflater.inflate(R.menu.menu_information_feeds, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                InformationFeedsFragment feedsFragment = (InformationFeedsFragment) getSupportFragmentManager().findFragmentById(
                        R.id.information_feed_main_fragment);

                if (feedsFragment != null) {
                    feedsFragment.onOptionsItemSelected(item);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
