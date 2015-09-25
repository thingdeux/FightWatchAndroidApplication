package watch.fight.android.fightbrowser.Utils;

/**
 * Created by josh on 9/25/15.
 */
public class StringUtils {
    private static int NUMBER_OF_LIMIT_DOTS = 3;

    public static String limitCharacters(String string, int charLimit, boolean addDots) {
        /* Limit a given string to n characters and potentially add dots to the end*/
        if (string != null) {
            if (string.length() > charLimit) {
                if (addDots) {
                    return string.substring(0,
                            (charLimit - (NUMBER_OF_LIMIT_DOTS + 1)))
                            + " ...";

                } else {
                    return string.substring(0, charLimit);
                }
            } else {
                return string;
            }
        }
        return null;
    }
}
