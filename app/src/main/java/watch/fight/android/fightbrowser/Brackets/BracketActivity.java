package watch.fight.android.fightbrowser.Brackets;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;


import java.util.List;

import watch.fight.android.fightbrowser.Brackets.Network.BracketSubmission;
import watch.fight.android.fightbrowser.Events.models.Bracket;
import watch.fight.android.fightbrowser.Events.models.DB.EventDB;
import watch.fight.android.fightbrowser.Events.models.Event;
import watch.fight.android.fightbrowser.R;
import watch.fight.android.fightbrowser.Utils.Network.PutRequest;


/**
 * Created by josh on 10/5/15.
 */
public class BracketActivity extends AppCompatActivity {
    public static String BRACKET_EVENT_ID = "watch.fight.android.fightbrowser.bracket.event_id";
    private RecyclerView mRecyclerView;
    private BracketAdapter mAdapter;
    private ImageButton mFloatingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        Long EventId = intent.getLongExtra(BRACKET_EVENT_ID, -1);
        Event event = EventDB.getInstance(this).getEvent(EventId);

        setContentView(R.layout.bracket_fragment);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_one);
        mFloatingButton = (ImageButton) findViewById((R.id.bracket_floating_button));
        final Context c = this;

        mFloatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("CLICKED IT", "Clicked my Floating Action Button");
                // TODO : REMOVE - TEST
                Event event = EventDB.getInstance(c).getAllEvents().get(0);
                Bracket bracket = new Bracket();
                bracket.setBracketName("TEST BRACKET");
                bracket.setBracketUrl("http://127.0.0.1/Test");

                BracketSubmission.submitBracket(c, event, bracket, false, "Josh");
            }
        });

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);

        mAdapter = new BracketAdapter(this, event);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);

    }

    @Override
    protected void onResume() {
        super.onResume();
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

    public static Intent NewInstance(Context activity, Long eventId) {
        Intent intent = new Intent(activity, BracketActivity.class);
        intent.putExtra(BRACKET_EVENT_ID, eventId);
        return intent;
    }
}
