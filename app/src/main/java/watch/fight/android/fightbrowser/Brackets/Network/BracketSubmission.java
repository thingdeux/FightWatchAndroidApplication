package watch.fight.android.fightbrowser.Brackets.Network;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import watch.fight.android.fightbrowser.Brackets.BracketSearchActivity;
import watch.fight.android.fightbrowser.Events.models.Bracket;
import watch.fight.android.fightbrowser.Events.models.Event;
import watch.fight.android.fightbrowser.Utils.Network.NetworkRequest;
import watch.fight.android.fightbrowser.Utils.Network.PutRequest;

/**
 * Created by Josh on 10/18/15.
 */
public abstract class BracketSubmission {
    private static final String TAG = BracketSubmission.class.getSimpleName();

    private static JSONObject createSubmissionObj(Event event, Bracket bracket,
                                                 boolean isValidated, String reporter) {

        /* EXAMPLE PUT REQUEST
        Url: /brackets/<event_id>
        {
            "bracket_name": "SoCal Regionals Killer Instinct Top 32",
            "bracket_url": "http://challonge.com/SocalRegionalsBBCS2",
            "reporter_name": "12321312",
            "is_validated": true
        }
        */

        if (event != null && bracket != null) {
            try {
                JSONObject putObj = new JSONObject();
                putObj.put("bracket_name", bracket.getBracketName());
                putObj.put("bracket_url", bracket.getBracketUrl());
                if (reporter != null) {
                    putObj.put("reporter_name", reporter);
                }
                putObj.put("is_validated", isValidated);

                return putObj;
            } catch (JSONException exception) {
                Log.e(TAG, "Error building JSON Request: " + exception);
            }

        }
        return null;
    }

    public static void submitBracket(Context context, Event event, Bracket bracket,
                                     boolean isValidated, String reporter) {

        String putUrl = String.format("https://api.fgccompanion/brackets/%s",
                "" + event.getId());
        JSONObject putObj = createSubmissionObj(event, bracket, isValidated, reporter);

        // TODO : May be useful to add a response listener to catch 404's or other errors at some point.
        // Fire and forget for now.
        PutRequest<JSONObject> submitRequest = new PutRequest<>(putUrl, JSONObject.class, null,
                putObj, null, null);
        RequestQueue queue = NetworkRequest.getInstance(context).getRequestQueue();
        queue.add(submitRequest);
    }

    public static boolean isValidSearchQuery(String url) {
        return (url != null && url.contains("q="));
    }

    public static void checkIfValidChallongeBracket(String url, Context context) {
        BracketSearchActivity bsa = (BracketSearchActivity) context;
        ChallongeNetworkHandlers.getTournamentInformation(context, url, bsa, false, false);
    }

}

