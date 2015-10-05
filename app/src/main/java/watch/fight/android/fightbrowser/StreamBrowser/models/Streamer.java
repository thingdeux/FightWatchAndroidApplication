package watch.fight.android.fightbrowser.StreamBrowser.models;

/**
 * Created by josh on 10/4/15.
 */
public class Streamer {
    private String mName;
    private Integer mRelatedGame;
    private Boolean mIsFavorite;

    public Boolean getIsFavorite() {
        return mIsFavorite;
    }

    public void setIsFavorite(Boolean isFavorite) {
        mIsFavorite = isFavorite;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Integer getRelatedGame() {
        return mRelatedGame;
    }

    public void setRelatedGame(Integer relatedGame) {
        mRelatedGame = relatedGame;
    }
}
