package watch.fight.android.fightbrowser.Brackets;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.VolleyError;

import watch.fight.android.fightbrowser.Brackets.models.TournamentWrapper;
import watch.fight.android.fightbrowser.Events.models.Bracket;
import watch.fight.android.fightbrowser.Events.models.DB.BracketDB;
import watch.fight.android.fightbrowser.R;
import watch.fight.android.fightbrowser.Utils.Network.IVolleyResponse;

/**
 * Created by josh on 10/5/15.
 */
public class ParticipantsActivity extends AppCompatActivity
        implements IVolleyResponse<TournamentWrapper> {
    public static String BRACKET_ID = "watch.fight.android.fightbrowser.brackets.participants.bracket";
    private RecyclerView mRecyclerView;
    private ParticipantsAdapter mAdapter;
    private Bracket mBracket;

    public void onSuccess(TournamentWrapper response) {
        mRecyclerView = (RecyclerView) findViewById(R.id.bracket_recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 3);


        mAdapter = new ParticipantsAdapter(this,
                mBracket,
                response.getTournament().getParticipants(),
                response.getTournament().getMatches()
                );
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    public void onFailure(VolleyError error) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        Long BracketId = intent.getLongExtra(BRACKET_ID, -1);

        mBracket = BracketDB.getInstance(this).getBracket(BracketId);
        if (mBracket != null) {
            // Make call to Challonge - pass response to onSuccess CB Above and build recyclerView
            ChallongeNetworkHandlers.getBracketTournamentInformation(this, mBracket, this, true, true);
        } else {
            // TODO : Set Error State
        }
        setContentView(R.layout.bracket_activity);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public static Intent NewInstance(Context activity, Long bracketid) {
        Intent intent = new Intent(activity, BracketActivity.class);
        intent.putExtra(BRACKET_ID, bracketid);
        return intent;
    }
}
