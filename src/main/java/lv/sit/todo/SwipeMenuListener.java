package lv.sit.todo;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import lv.sit.todo.db.Item;

/**
 * Helper four swiped menu buttons
 * @author uldis
 */
public class SwipeMenuListener {

    private float _x, _y;

    /**
     * delete callback
     */
    private SwipeMenuListener.IAction _deleteAction;


    SwipeMenuListener (RecyclerView recyclerView) {

        recyclerView.setOnTouchListener((View v, MotionEvent event) -> {

            if (event.getAction() == MotionEvent.ACTION_UP)
            {
                _x = event.getX();
                _y = event.getY();

                this._extendedItems();
            }

            return false;
        });
    }

    /**
     * Extended rows
     *
     */
    private void _extendedItems ()
    {
        RecyclerView recyclerView = (RecyclerView) MainActivity.getInstance().findViewById(R.id.mainRecycler);

        for (int i = 0; i < ItemAdapter.getInstance().items.size(); i++)
        {
            ItemAdapter.ViewHolder holder = (ItemAdapter.ViewHolder) recyclerView.findViewHolderForAdapterPosition(i);

            if (holder.deleteBounds == null)
            {
                continue;
            }

            if (holder.deleteBounds.contains((int) _x, (int) _y))
            {
                if (this._deleteAction != null)
                {
                    _deleteAction.onClick(holder.item);
                }

                Log.d(MainActivity.LOG_TAG, "Delete button touched");
            }
        }
    }

    /**
     * on delete
     * Set delete callback
     */
    public void onDelete (IAction action)
    {
        this._deleteAction = action;
    }

    /**
     * click action
     */
    @FunctionalInterface
    public interface IAction
    {
        /**
         * Click listener
         */
        public void onClick (Item item);
    }
}
