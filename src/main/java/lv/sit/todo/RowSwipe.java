package lv.sit.todo;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Swipe ItemAdapter's row
 * @deprecated
 */
public class RowSwipe implements View.OnTouchListener {

    /**
     * Animation thread
     */
    private RowSwipeAnimation animation;

    /**
     * Item adapter view holder
     */
    private final ItemAdapter.ViewHolder holder;

    /**
     * Constructor
     */
    public RowSwipe(ItemAdapter.ViewHolder holder) {
        this.holder = holder;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {

        /*Log.d(MainActivity.LOG_TAG, "action: " + event.getAction());
        Log.d(MainActivity.LOG_TAG, "actionm: " + event.getActionMasked());*/

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                animation = new RowSwipeAnimation(holder, event.getX());
                animation.start();
                break;
            case MotionEvent.ACTION_MOVE:
                animation.setX(event.getX());
                break;
            default:
                RowSwipeAnimation.setPassive();
                return false;
        }

        return true;
    }
}
