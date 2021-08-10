package crabster.rudakov.carspresentation.recyclerViewHelpers;

public interface ItemTouchHelperAdapter {

    void onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);

}
