package watch.fight.android.fightbrowser.Brackets;

import android.content.Context;
import android.net.Uri;

import watch.fight.android.fightbrowser.R;

/**
 * Created by josh on 10/5/15.
 */
public class ChallongeAPI {
    private static ChallongeAPI mChallongeAPI;
    private String API_KEY;

    private static String API_URL = "api.challonge.com";
    private static String API_VERSION = "v1";
    private static String FORMAT = ".json";
    private static String GET_TOURNAMENT = "tournaments";
    private static String GET_PARTICIPANTS = "participants";
    private static String GET_MATCHES = "matches";

    // Match States to be used as params in getMatches
    private static String MATCH_STATE_ALL = "all";
    private static String MATCH_STATE_PENDING = "pending";
    private static String MATCH_STATE_OPEN = "open";
    private static String MATCH_STATE_COMPLETE = "complete";

    private ChallongeAPI(Context context) {
        API_KEY = context.getResources().getString(R.string.challonge_api_key);
    }

    public static ChallongeAPI getInstance(final Context context) {
        if (mChallongeAPI == null) {
            mChallongeAPI = new ChallongeAPI(context);
        }
        return mChallongeAPI;
    }

    public Uri getTournamentUri(final String tournamentID, final Boolean includeParticipants, final Boolean includeMatches) {
        if (tournamentID != null) {
            String ip = (includeParticipants) ? "1" : "0";
            String im = (includeMatches) ? "1" : "0";

            return new Uri.Builder()
                    .scheme("https")
                    .authority(API_URL)
                    .appendPath(API_VERSION)
                    .appendPath(GET_TOURNAMENT)
                    .appendPath(tournamentID + FORMAT)
                    .appendQueryParameter("api_key", API_KEY)
                    .appendQueryParameter("include_participants", ip)
                    .appendQueryParameter("include_matches", im)
                    .build();
        } else {
            return null;
        }
    }

    // TODO : Fix URI Builders for these
    public Uri getParticipantsUri(String tournamentID) {
        if (tournamentID != null) {
            return new Uri.Builder()
                    .appendPath(API_URL)
                    .appendPath(GET_TOURNAMENT)
                    .appendPath(tournamentID)
                    .appendPath(GET_PARTICIPANTS)
                    .appendPath(FORMAT)
                    .appendQueryParameter("api_key", API_KEY)
                    .build();
        } else {
            return null;
        }
    }

    public Uri getMatchesUri(String tournamentID, String state) {
        if (tournamentID != null) {
            return new Uri.Builder()
                    .appendPath(API_URL)
                    .appendPath(GET_TOURNAMENT)
                    .appendPath(tournamentID)
                    .appendPath(GET_MATCHES)
                    .appendPath(FORMAT)
                    .appendQueryParameter("state", state)
                    .build();
        } else {
            return null;
        }
    }

    public Uri getMatchesUriFilteredForParticipant(String tournamentID, String state, String participant) {
        if (tournamentID != null) {
            return new Uri.Builder()
                    .appendPath(API_URL)
                    .appendPath(GET_TOURNAMENT)
                    .appendPath(tournamentID)
                    .appendPath(GET_MATCHES)
                    .appendPath(FORMAT)
                    .appendQueryParameter("state", state)
                    .appendQueryParameter("participant_id", participant)
                    .build();
        } else {
            return null;
        }
    }

}
