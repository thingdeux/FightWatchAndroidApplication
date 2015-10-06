package watch.fight.android.fightbrowser.Brackets;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.viewpagerindicator.LinePageIndicator;

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
        initViewPager();
        setUIReady();
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
        FragmentAdapter participantsAdapter = new FragmentAdapter(getSupportFragmentManager());
        ParticipantsFragment whosLeft = ParticipantsFragment.newInstance(ParticipantsFragment.PARTICIPANTS_FRAGMENT_WHOSLEFT);
        ParticipantsFragment upcoming = ParticipantsFragment.newInstance(ParticipantsFragment.PARTICIPANTS_FRAGMENT_UPCOMING);
        ParticipantsFragment battlelog = ParticipantsFragment.newInstance(ParticipantsFragment.PARTICIPANTS_FRAGMENT_BATTLELOG);

        participantsAdapter.addFragment(whosLeft);
        participantsAdapter.addFragment(upcoming);
        participantsAdapter.addFragment(battlelog);

        ViewPager viewPager = (ViewPager) findViewById(R.id.participant_viewPager);
        viewPager.setAdapter(participantsAdapter);

        LinePageIndicator lineIndicator = (LinePageIndicator) findViewById(R.id.participant_viewPager_indicator);
        lineIndicator.setViewPager(viewPager);
    }
}
