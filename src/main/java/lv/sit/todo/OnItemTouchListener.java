package lv.sit.todo;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 *
 */
public class OnItemTouchListener implements RecyclerView.OnItemTouchListener {

    /**
     * Animation thread
     */
    @Nullable
    private RowSwipeAnimation animation;

    /**
     * inittial coordinates
     */
    private final MotionEvent.PointerCoords initialCoords = new MotionEvent.PointerCoords();

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
        // Log.d(MainActivity.LOG_TAG, "onInterceptTouchEvent Action:" + e.getAction());
        // Log.d(MainActivity.LOG_TAG, "Item id: " + holder.item.id);

        if (e.getAction() == MotionEvent.ACTION_DOWN)
        {
            animation = null;
            // initialX = e.getX();
            e.getPointerCoords(0, initialCoords);
            return false;
        }

        if (_initialMove(e))
        {
            View view = rv.findChildViewUnder(e.getX(), e.getY());

            if (view == null)
            {
                return false;
            }

            ItemAdapter.ViewHolder holder = (ItemAdapter.ViewHolder) rv.findContainingViewHolder(view);

            if (holder == null)
            {
                return false;
            }

            animation = new RowSwipeAnimation(holder, initialCoords.x);
            animation.start();
        }

        return animation != null;
    }

    /**
     * Is swipe started
     */
    private boolean _initialMove(MotionEvent e) {
        if (animation != null) {
            return false;
        }

        if (e.getAction() != MotionEvent.ACTION_MOVE) {
            return false;
        }

        if (e.getPointerCount() !=1)
        {
            return false;
        }

        float dX = Math.abs(initialCoords.x - e.getX());
        float dY = Math.abs(initialCoords.y - e.getY());

        return dX > 5 && dY < 5;
    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
        Log.d(MainActivity.LOG_TAG, "Action:" + e.getAction());

        if (e.getAction() == MotionEvent.ACTION_MOVE)
        {
            animation.setX(e.getX());
        } else
        {
            RowSwipeAnimation.setPassive();
            animation = null;
        }
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        Log.d(MainActivity.LOG_TAG, "onRequestDisallowInterceptTouchEvent Action");

        if (disallowIntercept)
        {
            animation = null;
            RowSwipeAnimation.setPassive();
        }
    }
}
