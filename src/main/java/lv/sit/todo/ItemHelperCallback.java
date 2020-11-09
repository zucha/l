package lv.sit.todo;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import lv.sit.todo.db.OrderThread;

/**
 * Helper class for drag n drop
 *
 */
public class ItemHelperCallback extends ItemTouchHelper.Callback  {

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END , 0);
    }

    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);

        Log.d(MainActivity.LOG_TAG, "On movechanged " + actionState);

        if (ItemTouchHelper.ACTION_STATE_IDLE == actionState)
        {
            // store position in db
            new OrderThread().start();
        }
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        Log.d(MainActivity.LOG_TAG, "On move");

        ItemAdapter.getInstance().notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());


        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return false;
    }
}
