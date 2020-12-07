package lv.sit.todo;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class RowSwipe implements View.OnTouchListener  {

    /**
     * animation thread
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
     * constructor
     */
    public RowSwipe (View swipeView, View buttons)
    {
        this.swipeView = swipeView;
        this.buttons = buttons;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        Log.d(MainActivity.LOG_TAG, "action: " + event.getAction());

        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                animation = new RowSwipeAnimation(swipeView, buttons, event.getX());
                animation.start();
                break;
            case MotionEvent.ACTION_MOVE:
                animation.setX(event.getX());
                break;
            default:
                Log.d(MainActivity.LOG_TAG, "stop ");
                RowSwipeAnimation.setPassive();
                return false;
        }


        return true;
    }
}
