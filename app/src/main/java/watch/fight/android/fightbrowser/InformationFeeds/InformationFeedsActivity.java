package watch.fight.android.fightbrowser.InformationFeeds;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.List;

import watch.fight.android.fightbrowser.InformationFeeds.models.DB.StoryDB;
import watch.fight.android.fightbrowser.InformationFeeds.models.DB.StoryTrackerDB;
import watch.fight.android.fightbrowser.InformationFeeds.models.Story;
import watch.fight.android.fightbrowser.R;
import watch.fight.android.fightbrowser.Utils.SharedPreferences;

/**
 * Created by josh on 9/16/15.
 */
public class InformationFeedsActivity extends AppCompatActivity {
    private static final String TAG = InformationFeedsActivity.class.getSimpleName();
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.information_feed_activity);

        new FetchFeeds.FetchStories(this).execute();

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
        this.menu = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_information_feeds, menu);
        setViewAll(SharedPreferences.getShowFilteredFeeds(this));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        InformationFeedsFragment feedsFragment = (InformationFeedsFragment) getSupportFragmentManager().findFragmentById(
                R.id.information_feed_main_fragment);
        switch (item.getItemId()) {
            case R.id.action_refresh:

                if (feedsFragment != null) {
                    feedsFragment.onOptionsItemSelected(item);
                }
                return true;

            case R.id.action_mark_all_as_read:
                if (feedsFragment != null) {
                    feedsFragment.onOptionsItemSelected(item);
                }

                return true;

            case R.id.action_show_all:
                SharedPreferences.toggleShowFilteredFeeds(this);
                setViewAll(SharedPreferences.getShowFilteredFeeds(this));

                if (feedsFragment != null) {
                    feedsFragment.onOptionsItemSelected(item);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setViewAll(boolean isEnabled) {
        if (!isEnabled) {
            MenuItem showAll = menu.findItem(R.id.action_show_all);
            showAll.setTitle(R.string.information_menu_show_all_enable);
            showAll.setIcon(R.drawable.ic_view_all_on);
        } else {
            MenuItem showAll = menu.findItem(R.id.action_show_all);
            showAll.setTitle(R.string.information_menu_show_all_disable);
            showAll.setIcon(R.drawable.ic_view_all_off);
        }
    }

}
