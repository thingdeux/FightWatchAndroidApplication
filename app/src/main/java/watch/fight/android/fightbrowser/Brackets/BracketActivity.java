package watch.fight.android.fightbrowser.Brackets;

import android.app.Activity;
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

import de.greenrobot.event.EventBus;
import watch.fight.android.fightbrowser.Brackets.Network.BracketSubmission;
import watch.fight.android.fightbrowser.Brackets.events.BracketSuggestEvent;
import watch.fight.android.fightbrowser.Config.events.PreferenceToggleEvent;
import watch.fight.android.fightbrowser.Events.models.Bracket;
import watch.fight.android.fightbrowser.Events.models.DB.EventDB;
import watch.fight.android.fightbrowser.Events.models.Event;
import watch.fight.android.fightbrowser.R;
import watch.fight.android.fightbrowser.Utils.Dialogs.BasicAlertDialog;
import watch.fight.android.fightbrowser.Utils.Network.PutRequest;


/**
 * Created by josh on 10/5/15.
 */
public class BracketActivity extends AppCompatActivity implements BasicAlertDialog.BasicAlertButtonListener {
    public static String BRACKET_EVENT_ID = "watch.fight.android.fightbrowser.bracket.event_id";
    private RecyclerView mRecyclerView;
    private BracketAdapter mAdapter;
    private ImageButton mFloatingButton;
    private Event mEvent;
    private Context mContext;
    private Bracket mBracket;
    private Integer mVerifyType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        mContext = this;
        Long EventId = intent.getLongExtra(BRACKET_EVENT_ID, -1);
        mEvent = EventDB.getInstance(this).getEvent(EventId);

        setContentView(R.layout.bracket_fragment);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_one);
        mFloatingButton = (ImageButton) findViewById((R.id.bracket_floating_button));

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);

        mAdapter = new BracketAdapter(this, mEvent);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mFloatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(BracketSearchActivity.NewInstance(mContext, mEvent.getId()));
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

    }

    @Override
    public void onStop() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        if (mAdapter != null) {
            mAdapter.refreshBracketContents();
            Log.i("BracketActivity", "RefreshedBracketContents");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
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

    public void onEvent(BracketSuggestEvent event) {
        mBracket = event.mBracket;
        mVerifyType = event.submissionType;

        if (mVerifyType == BracketSuggestEvent.NEW_BRACKET) {
            BasicAlertDialog bad = BasicAlertDialog.newInstance(R.string.dialog_submit_suggestion_header,
                    R.string.dialog_suggest_custom_bracket_content);
            bad.show(getSupportFragmentManager(), "Bracket Suggest");
        } else {
            BasicAlertDialog bad = BasicAlertDialog.newInstance(R.string.dialog_approve_suggestion_header,
                    R.string.dialog_approve_community_bracket_content);
            bad.show(getSupportFragmentManager(), "Bracket Approve");
        }

    }

    @Override
    public void onOk() {
        if (mBracket != null) {
            Log.i("BracketActivity", "Would be sending: " + mBracket.getBracketName());
        }
        mBracket = null;
    }

    @Override
    public void onCancel() {
        // Intentionally blank
    }



}
