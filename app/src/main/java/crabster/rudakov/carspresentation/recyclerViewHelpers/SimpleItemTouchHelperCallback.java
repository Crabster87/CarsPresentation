package crabster.rudakov.carspresentation.recyclerViewHelpers;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class SimpleItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private final ItemTouchHelperAdapter adapter;

    public SimpleItemTouchHelperCallback(ItemTouchHelperAdapter adapter) {
        this.adapter = adapter;
    }

    /**
     * Определяем возможные направления перемещения элемента списка по затяжному нажатию(вверх-вниз)
     * и по свайпу(только вправо)
     */
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.END;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    /**
     * Уведомляем адаптер о перемещении элемента списка
     */
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder source, RecyclerView.ViewHolder target) {
        adapter.onItemMove(source.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    /**
     * Уведомляем адаптер о свайпе элемента списка
     */
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        adapter.onItemDismiss(viewHolder.getAdapterPosition());
    }

    /**
     * Указваем, что действия будут производиться по затяжному нажатию на элемент списка
     */
    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    /**
     * Указваем, что свайп можно инициировать в любой точке отображения элемента списка
     */
    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

}
