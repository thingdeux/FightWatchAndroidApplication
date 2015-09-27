package watch.fight.android.fightbrowser.InformationFeeds;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import watch.fight.android.fightbrowser.R;

/**
 * Created by josh on 9/16/15.
 */
public class InformationFeedsActivity extends AppCompatActivity {
    private static final String TAG = InformationFeedsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_activity);

        new FetchFeeds.FetchStories(this).execute();

        if (savedInstanceState == null) {
            InformationFeedsFragment informationFeedsFragment = new InformationFeedsFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.dashboard_main_fragment, informationFeedsFragment).commit();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_information_feeds, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                Toast.makeText(this, "Not yet Implemented", Toast.LENGTH_LONG).show();
//                getSupportFragmentManager().findFragmentById(
//                        R.id.information_feed_main_fragment).onOptionsItemSelected(item);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
