package watch.fight.android.fightbrowser.StreamBrowser;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.LinePageIndicator;
import com.viewpagerindicator.TitlePageIndicator;

import watch.fight.android.fightbrowser.R;
import watch.fight.android.fightbrowser.Utils.FragmentAdapter;


public class BrowserActivity extends AppCompatActivity {
    private void initStreams() {
        // Create Fragments
        BrowserFragment twitchBrowser = BrowserFragment.newInstance(BrowserFragment.BROWSER_FRAGMENT_FEATURED_TYPE);
        BrowserFragment gameSpecificBrowser = BrowserFragment.newInstance(BrowserFragment.BROWSER_FRAGMENT_GAME_SPECIFIC_TYPE, "Street Fighter");
        BrowserFragment gameSpecificBrowser2 = BrowserFragment.newInstance(BrowserFragment.BROWSER_FRAGMENT_GAME_SPECIFIC_TYPE, "Mortal Kombat");
        BrowserFragment gameSpecificBrowser3 = BrowserFragment.newInstance(BrowserFragment.BROWSER_FRAGMENT_GAME_SPECIFIC_TYPE, "Smash Brothers");
        BrowserFragment gameSpecificBrowser4 = BrowserFragment.newInstance(BrowserFragment.BROWSER_FRAGMENT_GAME_SPECIFIC_TYPE, "Killer Instinct");
        BrowserFragment gameSpecificBrowser5 = BrowserFragment.newInstance(BrowserFragment.BROWSER_FRAGMENT_GAME_SPECIFIC_TYPE, "Ultimate Marvel");

        // Setup FragmentManager
        FragmentAdapter browserAdapter = new FragmentAdapter(getSupportFragmentManager());
        browserAdapter.addFragment(gameSpecificBrowser);
        browserAdapter.addFragment(gameSpecificBrowser2);
        browserAdapter.addFragment(gameSpecificBrowser3);
        browserAdapter.addFragment(gameSpecificBrowser4);
        browserAdapter.addFragment(gameSpecificBrowser5);
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
            case R.id.action_refresh:
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