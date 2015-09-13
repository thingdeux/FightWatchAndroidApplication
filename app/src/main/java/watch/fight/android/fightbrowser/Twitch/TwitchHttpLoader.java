package watch.fight.android.fightbrowser.Twitch;

import android.util.Log;

/**
 * Created by josh on 9/12/15.
 */
public class TwitchHttpLoader {

    private IHttpResponse listener;

    public interface IHttpResponse {
        public void onReceivedResponse(String result);
    }

    public TwitchHttpLoader() {
        listener = null;
    }

    public void setCustomObjectListener(IHttpResponse listener) {
        this.listener = listener;
    }

    public void getTwitchData(String Url) {
        TwitchConsumer tc = new TwitchConsumer(Url, listener);
        tc.execute();
    }



}
