package watch.fight.android.fightbrowser.Utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import watch.fight.android.fightbrowser.R;

/**
 * Created by josh on 10/10/15.
 */
public class SharedPrefManager {
    public static final int WEB_VIEW_PREF = 1;
    public static final int DARK_MODE = 2;
    private int mPreference;
    private Context mContext;

    public SharedPrefManager(@NonNull Context c, final int preference) {
        mPreference = preference;
        mContext = c;
    }

    public String toggle() {
        switch (mPreference) {
            case WEB_VIEW_PREF:
                SharedPreferences.setToWebviewOrNotToWebview(mContext,
                        !SharedPreferences.getToWebViewOrNotToWebView(mContext));
                return this.toString();
            case DARK_MODE:
                SharedPreferences.setDarkView(mContext,
                        !SharedPreferences.getDarkView(mContext));
                return this.toString();
        }
        return "";
    }

    public void setValue(boolean value) {
        switch (mPreference) {
            case WEB_VIEW_PREF:
                SharedPreferences.setToWebviewOrNotToWebview(mContext, value);
                break;
            case DARK_MODE:
                SharedPreferences.setDarkView(mContext, value);
                break;
        }
    }

    public Boolean getValue() {
        switch (mPreference) {
            case WEB_VIEW_PREF:
                return SharedPreferences.getToWebViewOrNotToWebView(mContext);
            case DARK_MODE:
                return SharedPreferences.getDarkView(mContext);
            default:
                return null;
        }
    }

    @Override
    public String toString() {
        switch (mPreference) {
            case WEB_VIEW_PREF:
                return mContext.getResources().getString(R.string.web_view_label);
            case DARK_MODE:
                return mContext.getResources().getString(R.string.dark_view_label);
        }
        return "";
    }


}
