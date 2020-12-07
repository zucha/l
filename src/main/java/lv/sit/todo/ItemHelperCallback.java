package lv.sit.todo;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
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
    private final ColorDrawable background;
    private Drawable _iconDelete;
    private Drawable _iconEdit;

    /**
     * Constructor
     */
    public ItemHelperCallback ()
    {
        int color = MainActivity.getInstance().getResources().getColor(R.color.warning, null );

        background = new ColorDrawable(color);

        _iconDelete = ContextCompat.getDrawable(MainActivity.getInstance(), R.drawable.ic_delete);
        _iconEdit = ContextCompat.getDrawable(MainActivity.getInstance(), R.drawable.ic_config);
    }

    @Override
    public void onChildDraw(@NonNull Canvas c,
                            @NonNull RecyclerView recyclerView,
                            @NonNull RecyclerView.ViewHolder viewHolder,
                            float dX, float dY,
                            int actionState, boolean isCurrentlyActive) {

        ItemAdapter.ViewHolder holder = (ItemAdapter.ViewHolder) viewHolder;

        background.setBounds(0,0,0,0);

        int top = holder.itemView.getTop();
        int bottom = holder.itemView.getBottom();

        Rect backgroundBounds = new Rect(0, top, (int) dX, bottom);

        background.setBounds(backgroundBounds);
        background.draw(c);

        if (dX > 100)
        {
            dX = 100;
        }

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    /*@Override
    public void onChildDraw(@NonNull Canvas c,
                            @NonNull RecyclerView recyclerView,
                            @NonNull RecyclerView.ViewHolder viewHolder,
                            float dX, float dY,
                            int actionState, boolean isCurrentlyActive) {

        ItemAdapter.ViewHolder holder = (ItemAdapter.ViewHolder) viewHolder;

        Log.d(MainActivity.LOG_TAG, "Child draw sate: " + actionState);
        // Log.d(MainActivity.LOG_TAG, "child draw active: " + isCurrentlyActive);
        // Log.d(MainActivity.LOG_TAG, "child draw expanded: " + holder.expanded);

        if (isCurrentlyActive) {
            holder.expanded = false;
        }

        if (!isCurrentlyActive && holder.expanded && dX <= _buttonSize(holder) ) {
            dX = (_buttonSize(holder) * 2);
        }

        background.setBounds(0,0,0,0);

        holder.deleteBounds = new Rect(0,0,0,0);
        holder.editBounds = new Rect(0,0,0,0);

        if (holder.expanded && dX == _buttonSize(holder) * 2)
        {
            onSwiped(viewHolder, 8);
        }

        // Log.d(MainActivity.LOG_TAG, "Dx: " + dX);

        // if (dX > 0 )
        {
            if (dX > (_buttonSize(holder) * 2) )
            {
                holder.expanded = true;
                // dX = _buttonSize(holder) * 2;
            }

            int top = holder.itemView.getTop();
            int bottom = holder.itemView.getBottom();

            Rect backgroundBounds = new Rect(0, top, (int) dX, bottom);

            background.setBounds(backgroundBounds);
            background.draw(c);

            Rect buttonBounds = new Rect(backgroundBounds);
            if (buttonBounds.right > (_buttonSize(holder) * 2))
            {
                buttonBounds.right = _buttonSize(holder) * 2;
            }

            // Log.d(MainActivity.LOG_TAG, "button bounds: " + buttonBounds.toString());

            Rect deleteBackgroundBounds = new Rect(buttonBounds);
            deleteBackgroundBounds.right /= 2;
            holder.deleteBounds.set(deleteBackgroundBounds);

            Rect editBackgroundBounds = new Rect(buttonBounds);
            editBackgroundBounds.left = buttonBounds.right/2;
            holder.editBounds.set(editBackgroundBounds);

            if (dX > 100)
            {
                Rect deleteIconBounds = new Rect(buttonBounds);
                deleteIconBounds.left += 50;
                deleteIconBounds.top += 50;
                deleteIconBounds.right -= 50 + _buttonSize(holder);
                deleteIconBounds.bottom -= 50;
                _iconDelete.setBounds(deleteIconBounds);
                _iconDelete.draw(c);

                Rect editIconBounds = new Rect(buttonBounds);
                editIconBounds.left += 50 + _buttonSize(holder);
                editIconBounds.top += 50;
                editIconBounds.right -= 50;
                editIconBounds.bottom -= 50;
                _iconEdit.setBounds(editIconBounds);
                _iconEdit.draw(c);
            }
        }

        *//*if (dX < 0)
        {
            dX = 0;
        }*//*

        super.onChildDraw(c, recyclerView, holder, dX, dY, actionState, isCurrentlyActive);
    }*/

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

    /**
     * Cache row height
     */
    private int _rowHeight;

    /**
     * Size of row height
     *
     * Determine size to create proportional buttons
     */
    private int _buttonSize (ItemAdapter.ViewHolder holder)
    {
        if (_rowHeight != 0)
        {
            return _rowHeight;
        }

        return _rowHeight = holder.itemView.getBottom() - holder.itemView.getTop();
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

    }
}
