package watch.fight.android.fightbrowser.Config;

public interface ItemDragHelperAdapter {
    boolean onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);
}
