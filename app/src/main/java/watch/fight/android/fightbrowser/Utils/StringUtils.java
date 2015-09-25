package watch.fight.android.fightbrowser.Utils;

import android.support.annotation.NonNull;

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

    public static String boldWrap(@NonNull String string) {
        return "<b>" + string + "</b>";
    }

    public static String multipleLineBreaks(int number) {
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<number; i++) {
            sb.append(System.getProperty("line.separator"));
        }
        return sb.toString();
    }
}
