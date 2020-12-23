package lv.sit.todo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import lv.sit.todo.db.OrderThread;

/**
 * Helper class for drag n drop
 * Swipe to delete
 * Special swipe menu buttons
 * @link https://medium.com/@zackcosborn/step-by-step-recyclerview-swipe-to-delete-and-undo-7bbae1fce27e
 * @link https://www.tutorialsbuzz.com/2020/09/Android-RecyclerView-Buttons-Under-Swipe%20-HalfSwipe-Custom.html
 * @link https://github.com/daimajia/AndroidSwipeLayout
 */
public class ItemHelperCallback extends ItemTouchHelper.Callback  {
    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END, 0);
    }

    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);

        if (ItemTouchHelper.ACTION_STATE_IDLE == actionState)
        {
            // store position in db
            new OrderThread().start();
        }
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        ItemAdapter.getInstance().notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }


    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

    }
}
