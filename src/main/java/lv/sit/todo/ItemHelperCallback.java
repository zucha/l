package lv.sit.todo;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import lv.sit.todo.db.OrderThread;

/**
 * Helper class for drag n drop
 * Swipe to delete
 * @link https://medium.com/@zackcosborn/step-by-step-recyclerview-swipe-to-delete-and-undo-7bbae1fce27e
 * @link https://www.tutorialsbuzz.com/2020/09/Android-RecyclerView-Buttons-Under-Swipe%20-HalfSwipe-Custom.html
 * @link https://github.com/daimajia/AndroidSwipeLayout
 */
public class ItemHelperCallback extends ItemTouchHelper.Callback  {
    private final ColorDrawable background;
    private Drawable icon;
    private TextView textView;

    private static final int BUTTON_SIZE = 180;

    /**
     * Constructor
     */
    public ItemHelperCallback ()
    {
        int color = MainActivity.getInstance().getResources().getColor(R.color.warning, null );

        background = new ColorDrawable(color);

        icon = ContextCompat.getDrawable(MainActivity.getInstance(), R.drawable.ic_delete);

        textView = new TextView(MainActivity.getInstance());
        textView.setText(R.string.remove);
        color = MainActivity.getInstance().getResources().getColor(R.color.white, null );
        textView.setTextColor(color);
        color = MainActivity.getInstance().getResources().getColor(R.color.colorAccent, null );
        textView.setBackgroundColor(color);
    }



    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

        // recyclerView.setOnTouchListener();
        ItemAdapter.ViewHolder holder = (ItemAdapter.ViewHolder) viewHolder;

        holder.expanded = false;

        background.setBounds(0,0,0,0);

        holder.deleteBounds = new Rect(0,0,0,0);

        if (dX > 0 )
        {
            if (dX > BUTTON_SIZE )
            {
                holder.expanded = true;
                dX = BUTTON_SIZE;
            }

            int top = holder.itemView.getTop();
            int bottom = holder.itemView.getBottom();

            Rect bounds = new Rect(0, top, (int) dX, bottom);

            background.setBounds(bounds);
            background.draw(c);

            holder.deleteBounds.set(bounds);

            if (dX > 100)
            {
                bounds.left += 50;
                bounds.top += 50;
                bounds.right -= 50;
                bounds.bottom -= 50;
                icon.setBounds(bounds);
                icon.draw(c);
            }
            // textView.draw(c);
        }

        if (dX < 0)
        {
            dX = 0;
        }

        super.onChildDraw(c, recyclerView, holder, dX, dY, actionState, isCurrentlyActive);
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END ,
            ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
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
        // Log.d(MainActivity.LOG_TAG, "On move");

        ItemAdapter.getInstance().notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());

        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }
}
