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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.viewpagerindicator.TitlePageIndicator;


import watch.fight.android.fightbrowser.Brackets.Network.ChallongeNetworkHandlers;
import watch.fight.android.fightbrowser.Brackets.models.TournamentWrapper;
import watch.fight.android.fightbrowser.Events.models.Bracket;
import watch.fight.android.fightbrowser.Events.models.DB.BracketDB;
import watch.fight.android.fightbrowser.R;
import watch.fight.android.fightbrowser.Utils.Dialogs.BasicAlertDialog;
import watch.fight.android.fightbrowser.Utils.Network.IVolleyResponse;

/**
 * Created by josh on 10/5/15.
 */
public class ParticipantsActivity extends AppCompatActivity
        implements IVolleyResponse<TournamentWrapper>,  BasicAlertDialog.BasicAlertButtonListener {
    private static final String[] INDICATOR_TITLE_NAMES = new String[] { "Who's Left", "Battlelog", "Upcoming Matches", "Player Roster"};
    private static final int WHOS_LEFT_TAB = 0;
    private static final int BATTLELOG_TAB = 1;
    private static final int UPCOMING_MATCHES_TAB = 2;
    private static final int PLAYER_ROSTER_TAB = 3;
    public static String BRACKET_ID = "watch.fight.android.fightbrowser.brackets.participants.bracket";
    private View mParticipantsContainer;
    private Bracket mBracket;
    private View mLoadingContainer;
    private ViewPager mViewPager;

    // TODO : IVolleyResponse makes this Activity way too unyieldy, refactor

    // Response received from Challonge
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

        setContentView(R.layout.bracket_participants_activity);
        mParticipantsContainer = findViewById(R.id.participants_container);
        mLoadingContainer = findViewById(R.id.loading_container);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();
        Integer BracketId = intent.getIntExtra(BRACKET_ID, -1);
        mBracket = BracketDB.getInstance(this).getBracket(BracketId);
        getBracketsFromChallonge();

        ActionBar ap = getSupportActionBar();
        if (ap != null) {
            ap.setElevation(0);
            if (mBracket != null && mBracket.getBracketName() != null) {
                ap.setTitle(mBracket.getBracketName());
            }
        }
        setUILoading();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ParticipantsHolder.getInstance(this).wipe();
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
        mViewPager = (ViewPager) findViewById(R.id.participant_viewPager);
        TitlePageIndicator mIndicator = (TitlePageIndicator) findViewById(R.id.participant_viewPager_indicator);

        mViewPager.setAdapter(adapter);
        mIndicator.setViewPager(mViewPager);
        determineViewPagerStartTab();
    }

    // Extension of pager adapter to name tabs in tabpageindicator for viewpagernotification
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

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_participants, menu);

        if (mBracket != null) {
            MenuItem f = menu.findItem(R.id.participants_action_delete);
            f.setVisible(mBracket.getIsUserAdded());
        }
        return true;
    }

    public void determineViewPagerStartTab() {
        ParticipantsHolder holder = ParticipantsHolder.getInstance(this);
        if (holder != null) {
            if (!holder.isTournamentActive()) {
                if (holder.getMatches() != null && holder.getMatches().size() > 0) {
                    // Send Viewpager to 'Battlelog' if the tournament isn't active and there are matches.
                    mViewPager.setCurrentItem(BATTLELOG_TAB);
                } else {
                    // Send Viewpager to 'Player Roster' otherwise.
                    mViewPager.setCurrentItem(PLAYER_ROSTER_TAB);
                }
            }
        }

    }

    public void getBracketsFromChallonge() {
        if (mBracket != null) {
            // Make call to Challonge - pass response to onSuccess CB Above and build recyclerView
            ChallongeNetworkHandlers.getBracketTournamentInformation(this, mBracket, this, true, true);
        } else {
            setErrorState();
        }
        setUILoading();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.participants_action_refresh:
                getBracketsFromChallonge();
                return true;
            case R.id.participants_action_delete:
                BasicAlertDialog d = BasicAlertDialog.newInstance(R.string.dialog_are_you_sure_header, R.string.dialog_delete_bracket_content);
                d.show(getSupportFragmentManager(), "Bracket Delete");

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Dialog window callbacks
    @Override
    public void onOk() {
        BracketDB.getInstance(this).deleteOneBracket(mBracket);
        Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onCancel() {
        // Intentionally blank - do nothing on cancel
    }
}
