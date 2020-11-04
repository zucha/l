package lv.sit.todo.db;

import android.content.Context;
import android.util.Log;

import androidx.room.Room;

import java.util.concurrent.Callable;

import lv.sit.todo.ItemAdapter;
import lv.sit.todo.MainActivity;

/**
 * Gets data from db
 */
public class DbThread extends Thread {
    public DbThread (Callable v)
    {
        super(() -> {
            Database db = Room.databaseBuilder(
                    MainActivity.getInstance().getApplicationContext(),
                    Database.class,
                    Database.dbName).build();

            ItemDao itemDao = db.getItemDao();

            ItemAdapter.getInstance().count = itemDao.getCount();
            ItemAdapter.getInstance().items = itemDao.getAll();

            onComplete(v);
        });
    }

    /**
     *
     * @param v
     */
    private static void onComplete (Callable v)
    {
        try {
            v.call();
        } catch (Exception e)
        {
            Log.e(MainActivity.LOG_TAG, "Cannot execute callable");
            Log.e(MainActivity.LOG_TAG, e.getMessage());
            e.printStackTrace();
        }
    }
}
