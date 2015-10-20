package watch.fight.android.fightbrowser.StreamBrowser;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.viewpagerindicator.LinePageIndicator;

import java.util.ArrayList;
import java.util.List;

import watch.fight.android.fightbrowser.Config.models.DB.GameDB;
import watch.fight.android.fightbrowser.Config.models.GameConfig;
import watch.fight.android.fightbrowser.R;
import watch.fight.android.fightbrowser.Utils.FragmentAdapter;


public class BrowserActivity extends AppCompatActivity {
    private void initStreams() {
        List<GameConfig> games = GameDB.getInstance(this).getAllGames();
        ArrayList<BrowserFragment> fragments = new ArrayList<BrowserFragment>();

        // Create Fragments
        BrowserFragment twitchBrowser = BrowserFragment.newInstance(BrowserFragment.BROWSER_FRAGMENT_FEATURED_TYPE);

        if (games != null && games.size() > 0) {
            for (int i = 0; i < games.size(); i++) {
                if (!games.get(i).getIsFiltered()) {
                    fragments.add(BrowserFragment.newInstance(BrowserFragment.BROWSER_FRAGMENT_GAME_SPECIFIC_TYPE, games.get(i).getGameName()));
                }
            }
        }

        // Setup FragmentManager
        FragmentAdapter browserAdapter = new FragmentAdapter(getSupportFragmentManager());
        for (int i = 0; i < fragments.size(); i++) {
            browserAdapter.addFragment(fragments.get(i));
        }
        // Add Featured fragment at the end
        browserAdapter.addFragment(twitchBrowser);

        ViewPager viewPager = (ViewPager) findViewById(R.id.browser_viewPager);
        viewPager.setAdapter(browserAdapter);

        LinePageIndicator lineIndicator = (LinePageIndicator) findViewById(R.id.browser_viewPager_indicator);
        lineIndicator.setViewPager(viewPager);

//        final float density = getResources().getDisplayMetrics().density;
//        lineIndicator.setStrokeWidth(4 * density);
//        lineIndicator.setLineWidth(30 * density);

    }

    @Override
    public void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
        setContentView(R.layout.browser_activity);
        initStreams();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_browser, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_filter:
                Toast.makeText(this, "Not Yet Implement", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.participants_action_refresh:
                Toast.makeText(this, "Not Yet Implement", Toast.LENGTH_SHORT).show();
                return true;

        }

        return super.onOptionsItemSelected(item);
    }


//    public JSONObject getTestJson(int resId) {
//        //Get Data From Text Resource File Contains Json Data.
//        InputStream inputStream = getResources().openRawResource(resId);
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//
//        int ctr;
//        try {
//            ctr = inputStream.read();
//            while (ctr != -1) {
//                byteArrayOutputStream.write(ctr);
//                ctr = inputStream.read();
//            }
//            inputStream.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Log.v("Text Data", byteArrayOutputStream.toString());
//        try {
//            // Parse the data into jsonobject to get original data in form of json.
//            return (new JSONObject(byteArrayOutputStream.toString()));
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public String getTestJsonString(int resId) {
//        //Get Data From Text Resource File Contains Json Data.
//        InputStream inputStream = getResources().openRawResource(resId);
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//
//        int ctr;
//        try {
//            ctr = inputStream.read();
//            while (ctr != -1) {
//                byteArrayOutputStream.write(ctr);
//                ctr = inputStream.read();
//            }
//            inputStream.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return(byteArrayOutputStream.toString());
//    }

}