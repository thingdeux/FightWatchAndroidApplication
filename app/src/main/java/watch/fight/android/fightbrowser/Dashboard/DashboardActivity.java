package watch.fight.android.fightbrowser.Dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import watch.fight.android.fightbrowser.Config.models.Config;
import watch.fight.android.fightbrowser.StreamBrowser.BrowserActivity;
import watch.fight.android.fightbrowser.R;

/**
 * Created by josh on 9/14/15.
 */
public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_activity);

        if (savedInstanceState == null) {
            DashboardFragment dashboardFragment = new DashboardFragment();
//        dashboardFragment.setArguments(getIntent().getExtras());
//            JSONObject dashboardEntries = getTestJson(R.raw.test_config);
//            Gson g = new Gson();
//            Config c = g.fromJson(dashboardEntries.toString(), Config.class);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.dashboard_main_fragment, dashboardFragment).commit();
        }



    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_dashboard, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        switch (id) {
            case R.id.action_streams:
                startActivity(new Intent(this, BrowserActivity.class));
                return true;
            case R.id.action_news:
                return true;
            case R.id.action_preferences:
                return true;
            case R.id.action_events:
                return true;
        }


//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    public JSONObject getTestJson(int resId) {
        //Get Data From Text Resource File Contains Json Data.
        InputStream inputStream = getResources().openRawResource(resId);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        int ctr;
        try {
            ctr = inputStream.read();
            while (ctr != -1) {
                byteArrayOutputStream.write(ctr);
                ctr = inputStream.read();
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.v("Text Data", byteArrayOutputStream.toString());
        try {
            // Parse the data into jsonobject to get original data in form of json.
            return (new JSONObject(byteArrayOutputStream.toString()));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
