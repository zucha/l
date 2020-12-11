package lv.sit.todo;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Swipe ItemAdapter row
 */
public class RowSwipe implements View.OnTouchListener {

    /**
     * Animation thread
     */
    private RowSwipeAnimation animation;

    /**
     * View which will be translated
     */
    private View swipeView;

    /**
     * View with action buttons
     * default hidden
     */
    private View buttons;

    /**
     * Item adapter view holder
     */
    private ItemAdapter.ViewHolder holder;

    /**
     * Constructor
     */
    public RowSwipe(ItemAdapter.ViewHolder holder) {
        this.holder = holder;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        // Log.d(MainActivity.LOG_TAG, "action: " + event.getAction());

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
