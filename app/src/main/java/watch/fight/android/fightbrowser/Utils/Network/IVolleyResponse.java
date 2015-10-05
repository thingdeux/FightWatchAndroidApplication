package watch.fight.android.fightbrowser.Utils.Network;

import com.android.volley.VolleyError;

/**
 * Created by josh on 10/5/15.
 */
public interface IVolleyResponse<T> {
    void onSuccess(T response);
    void onFailure(VolleyError error);
}
