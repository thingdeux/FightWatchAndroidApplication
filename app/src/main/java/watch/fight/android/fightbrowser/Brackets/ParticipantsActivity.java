package watch.fight.android.fightbrowser.Brackets;

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
import android.view.View;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.viewpagerindicator.TitlePageIndicator;

import watch.fight.android.fightbrowser.Brackets.models.TournamentWrapper;
import watch.fight.android.fightbrowser.Events.models.Bracket;
import watch.fight.android.fightbrowser.Events.models.DB.BracketDB;
import watch.fight.android.fightbrowser.R;
import watch.fight.android.fightbrowser.Utils.FragmentAdapter;
import watch.fight.android.fightbrowser.Utils.Network.IVolleyResponse;

/**
 * Created by josh on 10/5/15.
 */
public class ParticipantsActivity extends AppCompatActivity
        implements IVolleyResponse<TournamentWrapper> {
    private static final String[] INDICATOR_TITLE_NAMES = new String[] { "Who's Left", "Battlelog", "Upcoming Matches"};
    public static String BRACKET_ID = "watch.fight.android.fightbrowser.brackets.participants.bracket";
    private View mParticipantsContainer;
    private Bracket mBracket;
    private View mLoadingContainer;

    public void onSuccess(TournamentWrapper response) {
        Log.i("VolleySuccess", "Received Challonge Success");

        // Each ViewPager will act on the same data, retrieved once from Challonge then set in this singleton.
        ParticipantsHolder holder = ParticipantsHolder.getInstance(this);
        holder.setTournamentWrapper(response);
        holder.setBracket(mBracket);
        setUIReady();
        initViewPager();
    }

    public void onFailure(VolleyError error) {
        Log.e("VolleyFailure", "Error Retrieving Challonge Feed: " + error);
        setErrorState();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        Integer BracketId = intent.getIntExtra(BRACKET_ID, -1);

        mBracket = BracketDB.getInstance(this).getBracket(BracketId);
        if (mBracket != null) {
            // Make call to Challonge - pass response to onSuccess CB Above and build recyclerView
            ChallongeNetworkHandlers.getBracketTournamentInformation(this, mBracket, this, true, true);
        } else {
            // TODO : Set Error State
        }
        setContentView(R.layout.bracket_participants_activity);
        mParticipantsContainer = findViewById(R.id.participants_container);
        mLoadingContainer = findViewById(R.id.loading_container);
        ActionBar ap = getSupportActionBar();
        if (ap != null) {
            ap.setElevation(0);
        }

        setUILoading();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public static Intent NewInstance(Context activity, Integer bracketid) {
        Intent intent = new Intent(activity, ParticipantsActivity.class);
        intent.putExtra(BRACKET_ID, bracketid);
        return intent;
    }

    private void setUILoading() {
        if (mParticipantsContainer != null) {
            mParticipantsContainer.setVisibility(View.INVISIBLE);
            mLoadingContainer.setVisibility(View.VISIBLE);
        }
    }

    private void setUIReady() {
        if (mParticipantsContainer != null) {
            mLoadingContainer.setVisibility(View.INVISIBLE);
            mParticipantsContainer.setVisibility(View.VISIBLE);
        }
    }

    private void setErrorState() {
        mLoadingContainer.findViewById(R.id.loading_spinner).setVisibility(View.GONE);
        mLoadingContainer.findViewById(R.id.loading_title).setVisibility(View.GONE);
        mLoadingContainer.findViewById(R.id.loading_subtitle).setVisibility(View.GONE);

        TextView title = (TextView) mLoadingContainer.findViewById(R.id.loading_error);
        title.setVisibility(View.VISIBLE);
        title.setText(R.string.challonge_participants_loading_error);
    }

    public void initViewPager() {
        // Setup FragmentManager
        ParticipantTabAdapter adapter = new ParticipantTabAdapter(getSupportFragmentManager());
        ViewPager mPager = (ViewPager) findViewById(R.id.participant_viewPager);
        TitlePageIndicator mIndicator = (TitlePageIndicator) findViewById(R.id.participant_viewPager_indicator);

        mPager.setAdapter(adapter);
        mIndicator.setViewPager(mPager);
    }

    // Extension of pager adapter to name tabs in tabpageindicator
    class ParticipantTabAdapter extends FragmentPagerAdapter {
        public ParticipantTabAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return ParticipantsFragment.newInstance(INDICATOR_TITLE_NAMES[position % INDICATOR_TITLE_NAMES.length]);
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
