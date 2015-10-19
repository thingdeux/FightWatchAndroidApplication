package watch.fight.android.fightbrowser.Brackets;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import watch.fight.android.fightbrowser.Events.models.DB.EventDB;
import watch.fight.android.fightbrowser.Events.models.Event;
import watch.fight.android.fightbrowser.R;

/**
 * Created by josh on 10/19/15.
 */
public class BracketSearchActivity extends AppCompatActivity {
    public static final String TAG = BracketSearchActivity.class.getSimpleName();
    public static final String BRACKET_EVENT_ID = "watch.fight.android.fightbrowser.bracket.bracketsearchactivity.event_id";
    private Event mEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        Long eventId = intent.getLongExtra(BRACKET_EVENT_ID, -1);
        if (eventId > 0) {
            mEvent = EventDB.getInstance(this).getEvent(eventId);
        }

        setContentView(R.layout.bracket_search_activity);

//        mFloatingButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // TODO : REMOVE - TEST
//                Event event = EventDB.getInstance(c).getAllEvents().get(0);
//                Bracket bracket = new Bracket();
//                bracket.setBracketName("TEST BRACKET");
//                bracket.setBracketUrl("http://127.0.0.1/Test");
//                BracketSubmission.submitBracket(c, event, bracket, false, "Josh");
//            }
//        });
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
    }

    public static Intent NewInstance(Context activity, Long eventId) {
        Intent intent = new Intent(activity, BracketSearchActivity.class);
        intent.putExtra(BRACKET_EVENT_ID, eventId);
        return intent;
    }
}
