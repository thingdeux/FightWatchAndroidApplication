package watch.fight.android.fightbrowser.Config;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.viewpagerindicator.TitlePageIndicator;

import de.greenrobot.event.EventBus;
import watch.fight.android.fightbrowser.Config.events.PreferenceToggleEvent;
import watch.fight.android.fightbrowser.R;

import static watch.fight.android.fightbrowser.Config.events.PreferenceToggleEvent.*;

/**
 * Created by Joshua on 10/9/15.
 */
public class PreferencesActivity extends AppCompatActivity {
    private static final String[] INDICATOR_TITLE_NAMES = new String[] { "General", "Games", "Feeds"};
    private static final String PREFERENCES_STARTING_WINDOW = "watch.fight.android.fightbrowser.Config.PreferencesActivity.StartingWindow";

    // The number of these ints isn't arbitrary, it coincides with the indicator title names above.
    // When an intent is received for one of these the viewpager will jump to the corresponding
    // Position from the title names.
    public static final int FILTER_GENERAL = 0;
    public static final int FILTER_GAMES = 1;
    public static final int FILTER_FEEDS = 2;
    private MenuItem mReorderMenuItem;

    public static Intent NewInstance(Context context, int preferenceType) {
        Intent intent = new Intent(context, PreferencesActivity.class);
        intent.putExtra(PREFERENCES_STARTING_WINDOW, preferenceType);
        return intent;
    }

    @PreferenceToggle private int activeItemState;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preferences_activity);
        activeItemState = ENABLE_TOGGLE;
        int pagerLocation = FILTER_GENERAL;

        Intent intent = getIntent();
        if (intent != null) {
            pagerLocation = intent.getIntExtra(PREFERENCES_STARTING_WINDOW, 0);
        }

        PreferencesTabAdapter adapter = new PreferencesTabAdapter(getSupportFragmentManager());
        ViewPager mPager = (ViewPager) findViewById(R.id.preferences_viewPager);
        TitlePageIndicator mIndicator = (TitlePageIndicator) findViewById(R.id.preferences_viewpager_indicator);

        mPager.setAdapter(adapter);
        mPager.setCurrentItem(pagerLocation);
        mIndicator.setViewPager(mPager);
    }

    @Override
    protected void onResume() {
        super.onResume();

        ActionBar ap = getSupportActionBar();
        if (ap != null) {
            ap.setElevation(0);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    // Extension of pager adapter to name tabs in tabpageindicator
    class PreferencesTabAdapter extends FragmentPagerAdapter {
        public PreferencesTabAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PreferencesFragment.newInstance(INDICATOR_TITLE_NAMES[position % INDICATOR_TITLE_NAMES.length]);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return INDICATOR_TITLE_NAMES[position % INDICATOR_TITLE_NAMES.length].toUpperCase();
        }

        @Override
        public int getCount() {
            return INDICATOR_TITLE_NAMES.length;
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_preferences, menu);
        mReorderMenuItem = menu.findItem(R.id.toggle_reorder_or_switch);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // This menu item will toggle re-order mode or hide mode for preferences.
        // Reorder will allow the user to shift the position of feeds and games,
        // Hide will allow them to filter them entirely.
        int id = item.getItemId();

        switch (id) {
            case R.id.toggle_reorder_or_switch:
                activeItemState = (activeItemState == PreferenceToggleEvent.ENABLE_REORDER) ? ENABLE_TOGGLE : ENABLE_REORDER;
                updateButtonName();
                EventBus.getDefault().post(new PreferenceToggleEvent(activeItemState));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void updateButtonName() {
        if (mReorderMenuItem != null) {
            mReorderMenuItem.setTitle(
                    (activeItemState != PreferenceToggleEvent.ENABLE_REORDER)
                            ? R.string.preferences_action_toggle_reorder : R.string.preferences_action_toggle_filter);
        }
    }

}
