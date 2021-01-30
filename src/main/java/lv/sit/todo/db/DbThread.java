package lv.sit.todo.db;

import android.util.Log;
import lv.sit.todo.ItemAdapter;
import lv.sit.todo.MainActivity;

/**
 * Gets data from db
 */
public class DbThread extends Thread {
    /**
     * Initialize database data for app
     * @param v after db initialize, run code
     */
    public DbThread (Runnable v)
    {
        super(() -> {
            Database db = Database.getInstance();

            ItemDao itemDao = db.getItemDao();

            itemDao.deleteMarked();

            ItemAdapter.getInstance().items = Database.getInstance().getAll();

            onComplete(v);
        });
    }

    /**
     * @param v Call after execute
     */
    private static void onComplete (Runnable v)
    {
        try {
            v.run();
        } catch (Exception e)
        {
            Log.e(MainActivity.LOG_TAG, "Cannot execute callable");
            Log.e(MainActivity.LOG_TAG, e.getMessage());
            e.printStackTrace();
        }
    }
}
