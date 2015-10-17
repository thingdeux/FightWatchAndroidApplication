package watch.fight.android.fightbrowser.Config;

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
    @PreferenceToggle private int activeItemState;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preferences_activity);
        activeItemState = ENABLE_TOGGLE;

        PreferencesTabAdapter adapter = new PreferencesTabAdapter(getSupportFragmentManager());
        ViewPager mPager = (ViewPager) findViewById(R.id.preferences_viewPager);
        TitlePageIndicator mIndicator = (TitlePageIndicator) findViewById(R.id.preferences_viewpager_indicator);

        mPager.setAdapter(adapter);
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
                int changeTo = (activeItemState == PreferenceToggleEvent.ENABLE_REORDER) ? ENABLE_TOGGLE : ENABLE_REORDER;
                EventBus.getDefault().post(new PreferenceToggleEvent(activeItemState));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
