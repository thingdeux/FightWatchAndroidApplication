package watch.fight.android.fightbrowser;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class BrowserActivity extends AppCompatActivity {
    private void initPaging() {
        // Create Fragments
//        BrowserFragment twitchBrowser = BrowserFragment.newInstance(BrowserFragment.BROWSER_FRAGMENT_FEATURED_TYPE);
        BrowserFragment gameSpecificBrowser = BrowserFragment.newInstance(BrowserFragment.BROWSER_FRAGMENT_GAME_SPECIFIC_TYPE, "Hearthstone");
        BrowserFragment gameSpecificBrowser2 = BrowserFragment.newInstance(BrowserFragment.BROWSER_FRAGMENT_GAME_SPECIFIC_TYPE, "Starcraft");

        // Setup FragmentManager
        BrowserPagerAdapter browserAdapter = new BrowserPagerAdapter(getSupportFragmentManager());
//        browserAdapter.addFragment(twitchBrowser);
        browserAdapter.addFragment(gameSpecificBrowser);
        browserAdapter.addFragment(gameSpecificBrowser2);

        ViewPager viewPager = (ViewPager) findViewById(R.id.browser_viewPager);
        viewPager.setAdapter(browserAdapter);

    }

    @Override
    public void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
        setContentView(R.layout.browser_activity);
        initPaging();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_browser, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
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