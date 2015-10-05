package watch.fight.android.fightbrowser.Brackets;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;


import java.util.List;

import watch.fight.android.fightbrowser.Events.models.Bracket;
import watch.fight.android.fightbrowser.Events.models.DB.EventDB;
import watch.fight.android.fightbrowser.Events.models.Event;
import watch.fight.android.fightbrowser.R;


/**
 * Created by josh on 10/5/15.
 */
public class BracketActivity extends AppCompatActivity {
    public static String BRACKET_EVENT_ID = "watch.fight.android.fightbrowser.bracket.event_id";
    private RecyclerView mRecyclerView;
    private BracketAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        Long EventId = intent.getLongExtra(BRACKET_EVENT_ID, -1);

        // Null/Size check unnecessary - done before assigning the intent
        Event event = EventDB.getInstance(this).getEvent(EventId);

        setContentView(R.layout.bracket_activity);
        mRecyclerView = (RecyclerView) findViewById(R.id.bracket_recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 3);

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

    public static Intent NewInstance(Context activity, Long eventId) {
        Intent intent = new Intent(activity, BracketActivity.class);
        intent.putExtra(BRACKET_EVENT_ID, eventId);
        return intent;
    }
}