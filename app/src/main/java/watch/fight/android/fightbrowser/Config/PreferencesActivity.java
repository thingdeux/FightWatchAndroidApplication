package watch.fight.android.fightbrowser.Config;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.viewpagerindicator.TitlePageIndicator;

import watch.fight.android.fightbrowser.Brackets.ParticipantsFragment;
import watch.fight.android.fightbrowser.R;

/**
 * Created by Joshua on 10/9/15.
 */
public class PreferencesActivity extends AppCompatActivity {
    private static final String[] INDICATOR_TITLE_NAMES = new String[] { "General", "Games", "Feeds"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preferences_activity);

        PreferencesTabAdapter adapter = new PreferencesTabAdapter(getSupportFragmentManager());
        ViewPager mPager = (ViewPager) findViewById(R.id.preferences_viewPager);
        TitlePageIndicator mIndicator = (TitlePageIndicator) findViewById(R.id.preferences_viewpager_indicator);

        mPager.setAdapter(adapter);
        mIndicator.setViewPager(mPager);
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

}
