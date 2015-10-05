package watch.fight.android.fightbrowser.Brackets;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import watch.fight.android.fightbrowser.Brackets.models.TournamentWrapper;
import watch.fight.android.fightbrowser.Utils.Network.GsonRequest;
import watch.fight.android.fightbrowser.Utils.Network.NetworkRequest;

public class ChallongeNetworkHandlers {
    private static Response.Listener<TournamentWrapper> ChallongeSuccessListener() {
        return new Response.Listener<TournamentWrapper>() {
            @Override
            public void onResponse(TournamentWrapper response) {
            }
        };
    }

    private static Response.ErrorListener ChallongeErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        };
    }

    public static void getTournament(Context context) {
        ChallongeAPI api = ChallongeAPI.getInstance(context.getApplicationContext());
        RequestQueue queue = NetworkRequest.getInstance(context.getApplicationContext()).getRequestQueue();
        queue.add(new GsonRequest<TournamentWrapper>(
                api.getTournamentUri("nextlevel-nlbc140usf4", false, false).toString(),
                TournamentWrapper.class,
                null,
                ChallongeSuccessListener(),
                ChallongeErrorListener()
        ));
    }


}