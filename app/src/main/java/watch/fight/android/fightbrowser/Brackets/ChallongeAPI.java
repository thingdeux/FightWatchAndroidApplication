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

    private static String API_URL = "https://api.challonge.com/v1";
    private static String FORMAT = ".json";
    private static String GET_TOURNAMENT = "tournaments";
    private static String GET_PARTICIPANTS = "participants";
    private static String GET_MATCHES = "matches";

    // Match States to be used as params in getMatches
    public static String MATCH_STATE_ALL = "all";
    public static String MATCH_STATE_PENDING = "pending";
    public static String MATCH_STATE_OPEN = "open";
    public static String MATCH_STATE_COMPLETE = "complete";


    ChallongeAPI() {}

    public ChallongeAPI getInstance(final Context context) {
        if (mChallongeAPI == null) {
            mChallongeAPI = new ChallongeAPI();
            API_KEY = context.getResources().getString(R.string.challonge_api_key);
        }
        return mChallongeAPI;
    }

    public Uri getTournamentUri(String tournamentID) {
        if (tournamentID != null) {
            return new Uri.Builder()
                    .appendPath(API_URL)
                    .appendPath(GET_TOURNAMENT)
                    .appendPath(tournamentID)
                    .appendPath(FORMAT)
                    .appendQueryParameter("api_key", API_KEY)
                    .build();
        } else {
            return null;
        }
    }

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

//    TODO : Implement subdomain support
//    Tournament ID (e.g. 10230) or URL (e.g. 'single_elim' for challonge.com/single_elim). If assigned to a subdomain,
//    URL format must be :subdomain-:tournament_url (e.g. 'test-mytourney' for test.challonge.com/mytourney)
//    :subdomain-:tournament_url
}
