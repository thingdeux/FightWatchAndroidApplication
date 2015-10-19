package watch.fight.android.fightbrowser.Brackets.Network;

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
    public static String MATCH_STATE_ALL = "all";
    public static String MATCH_STATE_PENDING = "pending";
    public static String MATCH_STATE_OPEN = "open";
    public static String MATCH_STATE_COMPLETE = "complete";

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
            String parsedTournamentID = ParseTournamentID(tournamentID);
            String ip = (includeParticipants) ? "1" : "0";
            String im = (includeMatches) ? "1" : "0";

            return new Uri.Builder()
                    .scheme("https")
                    .authority(API_URL)
                    .appendPath(API_VERSION)
                    .appendPath(GET_TOURNAMENT)
                    .appendPath(parsedTournamentID + FORMAT)
                    .appendQueryParameter("api_key", API_KEY)
                    .appendQueryParameter("include_participants", ip)
                    .appendQueryParameter("include_matches", im)
                    .build();
        } else {
            return null;
        }
    }

    public Uri getParticipantsUri(String tournamentID) {
        if (tournamentID != null) {
            String parsedTournamentID = ParseTournamentID(tournamentID);

            return new Uri.Builder()
                    .scheme("https")
                    .authority(API_URL)
                    .appendPath(GET_TOURNAMENT)
                    .appendPath(parsedTournamentID)
                    .appendPath(GET_PARTICIPANTS + FORMAT)
                    .appendQueryParameter("api_key", API_KEY)
                    .build();
        } else {
            return null;
        }
    }

    public Uri getMatchesUri(String tournamentID, String state) {
        if (tournamentID != null) {
            String parsedTournamentID = ParseTournamentID(tournamentID);
            return new Uri.Builder()
                    .scheme("https")
                    .authority(API_URL)
                    .appendPath(GET_TOURNAMENT)
                    .appendPath(parsedTournamentID)
                    .appendPath(GET_MATCHES + FORMAT)
                    .appendQueryParameter("state", state)
                    .build();
        } else {
            return null;
        }
    }

    public Uri getMatchesUriFilteredForParticipant(String tournamentID, String state, String participant) {
        if (tournamentID != null) {
            String parsedTournamentID = ParseTournamentID(tournamentID);
            return new Uri.Builder()
                    .scheme("https")
                    .authority(API_URL)
                    .appendPath(GET_TOURNAMENT)
                    .appendPath(parsedTournamentID)
                    .appendPath(GET_MATCHES + FORMAT)
                    .appendQueryParameter("state", state)
                    .appendQueryParameter("participant_id", participant)
                    .build();
        } else {
            return null;
        }
    }

    public String ParseTournamentID(String id) {
        /* Challonge expects tournaments in the following format.
           <tournament_name> - <subdomain-tournament_name>
           Find out if the passed id has a subdomain and build the proper id. */

        // Strip http/https

        // EX: challonge.com/EVOSSBMPicks | nextlevel.challonge.com/nlbc140usf4
        if (id != null) {
            String stripped = id.replaceFirst("^(https?://|https?://www\\.|www\\.)", "");
            String[] splitID = stripped.split("/");
            String bracketName = splitID[1];
            String[] possibleSubdomain = splitID[0].split("\\.");
            if (possibleSubdomain.length > 2) {
                return possibleSubdomain[0] + "-" + bracketName;
            } else {
                return bracketName;
            }
        }
        return null;


    }
}
