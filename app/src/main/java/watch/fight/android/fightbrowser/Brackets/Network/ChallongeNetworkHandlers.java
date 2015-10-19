package watch.fight.android.fightbrowser.Brackets.Network;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import watch.fight.android.fightbrowser.Brackets.models.TournamentWrapper;
import watch.fight.android.fightbrowser.Events.models.Bracket;
import watch.fight.android.fightbrowser.Utils.Network.GsonRequest;
import watch.fight.android.fightbrowser.Utils.Network.IVolleyResponse;
import watch.fight.android.fightbrowser.Utils.Network.NetworkRequest;

public class ChallongeNetworkHandlers {
    private static Response.Listener<TournamentWrapper> ChallongeSuccessListener(final IVolleyResponse<TournamentWrapper> callback) {
        return new Response.Listener<TournamentWrapper>() {
            @Override
            public void onResponse(TournamentWrapper response) {
                callback.onSuccess(response);
            }
        };
    }

    private static Response.ErrorListener ChallongeErrorListener(final IVolleyResponse<TournamentWrapper> callback) {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFailure(error);
            }
        };
    }

    public static void getBracketTournamentInformation(Context context, Bracket bracket, IVolleyResponse<TournamentWrapper> callback,
                                             Boolean includeParticipants, Boolean includeMatches) {
        ChallongeAPI api = ChallongeAPI.getInstance(context.getApplicationContext());
        RequestQueue queue = NetworkRequest.getInstance(context.getApplicationContext()).getRequestQueue();
        queue.add(new GsonRequest<TournamentWrapper>(
                api.getTournamentUri(bracket.getBracketUrl(), includeParticipants, includeMatches).toString(),
                TournamentWrapper.class,
                null,
                ChallongeSuccessListener(callback),
                ChallongeErrorListener(callback)
        ));
    }


}