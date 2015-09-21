package watch.fight.android.fightbrowser.Utils;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by josh on 9/21/15.
 */
public class JsonFromRaw {
    public static JSONObject getJsonObj(Context context, int resId) {
        //Get Data From Text Resource File Contains Json Data.
        InputStream inputStream = context.getResources().openRawResource(resId);
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
//        Log.v("Text Data", byteArrayOutputStream.toString());
        try {
            // Parse the data into jsonobject to get original data in form of json.
            return (new JSONObject(byteArrayOutputStream.toString()));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getStringObj(Context context, int resId) {
        return getJsonObj(context, resId).toString();
    }
}
