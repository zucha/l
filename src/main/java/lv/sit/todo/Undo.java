package lv.sit.todo;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import lv.sit.todo.db.DeleteThread;
import lv.sit.todo.db.Item;

/**
 * Undo block, to prevent deletion of accidentaly swiped items
 * @author uldis@sit.lv
 */
public class Undo extends Thread {
    /**
     * Undo layout
     */
    private View view;

    /**
     * Singleton
     */
    private static Undo instance;

    /**
     * Close undo view after x milisecond
     */
    private static final int TIMEOUT = 5000;

    /**
     * Item adapter's holder
     */
    private ItemAdapter.ViewHolder holder;

    /**
     *
     *
     */
    Undo (ItemAdapter.ViewHolder holder) {
        view = MainActivity.getInstance().findViewById(R.id.undoLayout);
        instance = this;

        // rowLayout = holder.rowLayout;

        this.holder = holder;

        if (isHidden()) {
            new ShowUndo().start();
        }
        new HideRow ().start();
    }

    /**
     * Main thread
     */
    public void run ()
    {
        boolean delete = true;
        try
        {
            sleep(TIMEOUT);
        } catch (InterruptedException e)
        {
            new HideUndo().start();
            new ShowRow().start();
            delete = false;
        }

        new HideUndo ().start();

        if (delete)
        {
            Log.d(MainActivity.LOG_TAG, "Deleted item: " + holder.item.id);
            new DeleteThread(holder.item).start();
            new ShowRow().start();
        }
    }

    /**
     *
     * @param holder set current deleteable viewv
     */
    public static void set(ItemAdapter.ViewHolder holder) {
        Log.d(MainActivity.LOG_TAG, "delete item: " + holder.item.id);

        Undo undo = new Undo(holder);
        undo.start();
    }

    /**
     * Hide active item row
     */
    private class HideRow extends Thread
    {
        /**
         *
         */
        public void run ()
        {
            View rowLayout = holder.rowLayout;

            ViewGroup.LayoutParams params = rowLayout.getLayoutParams();

            float factor = ItemAdapter.getDefaultRowHeight() / 10;

            for (float i = ItemAdapter.getDefaultRowHeight(); i >= 0; i -= factor)
            {
                try
                {
                    sleep(20);
                } catch (InterruptedException e)
                {
                    params.height = 0;
                    MainActivity.getInstance().runOnUiThread(() -> rowLayout.setLayoutParams(params));
                    break;
                }

                params.height = (int) i;
                MainActivity.getInstance().runOnUiThread(() -> rowLayout.setLayoutParams(params));
            }

            View buttons = rowLayout.findViewById(R.id.rowButtons);

            MainActivity.getInstance().runOnUiThread(() -> buttons.setVisibility(View.GONE));

            params.height = 0;

            MainActivity.getInstance().runOnUiThread(() -> rowLayout.setLayoutParams(params));
        }
    }

    /**
     * Is view visible
     * @return
     */
    public boolean isHidden ()
    {
        return view == null || view.getVisibility() == View.GONE;
    }

    /**
     * undo is clicked
     */
    public static void onUndo () {
        Log.d(MainActivity.LOG_TAG, "Clicked undo");
        instance.interrupt();
    }

    /**
     * Vievholder
     */
    public class ShowRow extends Thread
    {
        public void run ()
        {
            View rowLayout = holder.rowLayout;
            ViewGroup.LayoutParams params = rowLayout.getLayoutParams();

            View buttons = rowLayout.findViewById(R.id.rowButtons);

            MainActivity.getInstance().runOnUiThread(() -> buttons.setVisibility(View.GONE) );

            MainActivity.getInstance().runOnUiThread(() -> holder.rowTexView.setTranslationX(0) );

            float factor = ItemAdapter.getDefaultRowHeight() / 10;

            for (float i = 0; i <= ItemAdapter.getDefaultRowHeight(); i += factor)
            {
                try
                {
                    sleep(20);
                } catch (InterruptedException e)
                {
                    params.height = ItemAdapter.getDefaultRowHeight();
                    MainActivity.getInstance().runOnUiThread(() -> rowLayout.setLayoutParams(params));
                    break;
                }

                params.height = (int) i;
                MainActivity.getInstance().runOnUiThread(() -> rowLayout.setLayoutParams(params));
            }

            params.height = ItemAdapter.getDefaultRowHeight();

            MainActivity.getInstance().runOnUiThread(() -> rowLayout.setLayoutParams(params));
        }
    }

    /**
     * Show undo Wiev
     */
    private class ShowUndo extends Thread
    {
        /**
         * Show undo window
         *
         */
        public void run() {
            MainActivity.getInstance().runOnUiThread(() -> {
                view.setAlpha(0);
                view.setVisibility(View.VISIBLE);
            });

            for (float i = 0; i <= 1; i += 0.1)
            {

                try
                {
                    sleep(20);
                } catch (InterruptedException e)
                {
                    MainActivity.getInstance().runOnUiThread(() -> view.setAlpha(1));
                    break;
                }

                float alpha = i;
                MainActivity.getInstance().runOnUiThread(() -> view.setAlpha(alpha));
            }
        }
    }

    /**
     * Hide undo View
     */
    private class HideUndo extends Thread
    {
        /**
         * Show undo window
         *
         */
        public void run() {
            for (float i = 1; i >= 0; i -= 0.1)
            {
                try
                {
                    sleep(20);
                } catch (InterruptedException e)
                {
                    MainActivity.getInstance().runOnUiThread(() -> view.setAlpha(0));
                    break;
                }

                float alpha = i;
                MainActivity.getInstance().runOnUiThread(() -> view.setAlpha(alpha));
            }

            MainActivity.getInstance().runOnUiThread(() -> {
                view.setAlpha(0);
                view.setVisibility(View.GONE);
            });

        }
    }
}
