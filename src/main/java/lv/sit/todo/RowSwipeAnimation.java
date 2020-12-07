package lv.sit.todo;

import android.util.Log;
import android.view.View;

/**
 *
 */
public class RowSwipeAnimation extends Thread {

    /**
     * threshold to view menu
     */
    private final static int MENU_THRESHOLD = 150;

    /**
     * Swipeapble view
     */
    private View view;

    /**
     * Group of buttons
     */
    private View buttons;

    /**
     * last finger pointer
     */
    private float lastPointerX;

    /**
     * Previous finger/position pointer
     */
    private float pointerX;

    /**
     * is running
     */
    private static boolean active = true;

    /**
     * velocity
     */
    private final int velocity = 15;

    private int _tmpVelocity = velocity;

    /**
     * Constructor
     */
    public RowSwipeAnimation (View view, View buttons, float x) {
        this.view = view;
        this.buttons = buttons;
        pointerX = lastPointerX = x;
        // Log.d(MainActivity.LOG_TAG, "left: " + view.getLeft() + " right: " + view.getRight());
    }

    @Override
    public synchronized void start() {
        super.start();
        active = true;
    }

    @Override
    public void run() {
        Log.d(MainActivity.LOG_TAG, "start run");

        do {
            try {

                sleep(20);
                Log.d(MainActivity.LOG_TAG, "run t " + deltaX());

                drawSwipe ();
            } catch (InterruptedException e)
            {
                active = false;
            }
        } while (active);

        if (view.getTranslationX() < MENU_THRESHOLD)
        {
            backToZero ();
        } else if (view.getTranslationX() > (view.getWidth() / 2))
        {
            swipeToDelete ();
        } else
        {
            drawToButtons ();
        }

        Log.d(MainActivity.LOG_TAG, "end run");
    }

    /**
     * stop animation
     */
    public static void setPassive ()
    {
        active = false;
    }

    /**
     * Set pointer to move
     * @param x
     */
    public void setX (float x)
    {
        // Log.d(MainActivity.LOG_TAG, "set x" + x);
        lastPointerX = x;
    }

    /**
     *
     * @return
     */
    private float deltaX ()
    {
        return (pointerX - lastPointerX) * -1;
    }

    /**
     *
     */
    private void drawSwipe ()
    {
        float x = view.getTranslationX();
        float positiveDelta = deltaX();
        int direction = deltaX() < 0 ? -1 : 1;

        positiveDelta = positiveDelta * direction;

        toggleButtonsView ();

        int velocity = getVelocity();

        if (positiveDelta > velocity)
        {
            view.setTranslationX(x + (velocity * direction));
            pointerX += (velocity * direction);
        } else
        {
            view.setTranslationX(x + deltaX());
            pointerX += deltaX();
            _tmpVelocity = this.velocity;
        }
    }

    /**
     *
     */
    private void backToZero ()
    {
        lastPointerX = 0;
        pointerX = view.getTranslationX();

        while (pointerX != 0)
        {
            try
            {
                sleep(20);
                drawSwipe();
            } catch (InterruptedException e)
            {
                view.setTranslationX(lastPointerX);
            }
        }
    }

    /**
     * @return
     */
    private int getVelocity ()
    {
        return _tmpVelocity++;
    }

    /**
     * show swipe menu buttons
     */
    private void drawToButtons ()
    {
        lastPointerX = 250;
        pointerX = view.getTranslationX();

        while (pointerX != lastPointerX)
        {
            try
            {
                sleep(20);
                drawSwipe();
            } catch (InterruptedException e)
            {
                view.setTranslationX(lastPointerX);
            }
        }
    }

    /**
     * Swipe out of the view
     */
    private void swipeToDelete ()
    {
        lastPointerX = view.getWidth();
        pointerX = view.getTranslationX();

        while (pointerX != lastPointerX)
        {
            try
            {
                sleep(20);
                drawSwipe();
            } catch (InterruptedException e)
            {
                view.setTranslationX(lastPointerX);
            }
        }
    }

    /**
     * Determine to show or hide buttons group
     */
    private void toggleButtonsView ()
    {
        float x = view.getTranslationX();
        /*float positiveDelta = deltaX();
        int direction = deltaX() < 0 ? -1 : 1;

        positiveDelta = positiveDelta * direction;*/

        if (x > MENU_THRESHOLD && buttons.getVisibility() == View.GONE)
        {
            new ShowButtons().start();
        }

        if (x < MENU_THRESHOLD && buttons.getVisibility() == View.VISIBLE)
        {
            new HideButtons().start();
        }
    }

    /**
     * To show buttons group
     */
    private class ShowButtons extends Thread
    {
        @Override
        public void run ()
        {
            MainActivity.getInstance().runOnUiThread(() -> {
                buttons.setAlpha(0);
                buttons.setVisibility(View.VISIBLE);
            });

            for (float i = 0.1f; i <= 1.1; i += 0.1f)
            {
                float alpha = i;
                Log.d(MainActivity.LOG_TAG, "set alpha: " + alpha);
                MainActivity.getInstance().runOnUiThread(() -> {
                    buttons.setAlpha( (alpha) );
                });

                try
                {
                    this.sleep(10);
                } catch (InterruptedException e)
                {
                    MainActivity.getInstance().runOnUiThread(() -> {
                        buttons.setAlpha( (1) );
                    });
                }

            }
        }
    }

    /**
     * Hide buttons group
     */
    private class HideButtons extends Thread
    {
        @Override
        public void run ()
        {
            for (float i = 0.9f; i >= 0; i -= 0.1f)
            {
                float alpha = i;

                MainActivity.getInstance().runOnUiThread(() -> {
                    buttons.setAlpha( (alpha) );
                });

                try
                {
                    sleep(10);
                } catch (InterruptedException e)
                {

                }
            }

            MainActivity.getInstance().runOnUiThread(() -> {
                buttons.setAlpha(0);
                buttons.setVisibility(View.GONE);
            });
        }

    }
}
