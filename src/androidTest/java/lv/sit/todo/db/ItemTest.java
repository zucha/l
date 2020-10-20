package lv.sit.todo.db;

import android.content.Context;
import android.util.Log;

import androidx.core.util.Pools;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import lv.sit.todo.db.Database;
import lv.sit.todo.db.ItemDao;

@RunWith(AndroidJUnit4.class)
public class ItemTest {
    /**
     * locat db
     */
    private Database db;

    /**
     *
     */
    private ItemDao itemDao;

    @Before
    public void setUp ()
    {
        Context context = ApplicationProvider.getApplicationContext();

        db = Room.inMemoryDatabaseBuilder(context, Database.class).build();
        itemDao = db.getItemDao();
    }

    @Test
    public void testCreate ()
    {
        Assert.assertEquals(0, itemDao.getCount());

        List<Item> items = itemDao.getAll();

        Assert.assertEquals(0, items.size());

        Item item = new Item();
        item.name = "test name";

        itemDao.insert(item);

        items = itemDao.getAll();

        Assert.assertEquals(1, items.size());
    }

    @Test
    public void testThreads ()
    {
        Thread thread = new Thread(() -> {
            System.out.println("test");
            Log.d("Tests", "yes, thread ok");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // e.printStackTrace();
                Log.d("Tests", "no, interrupted");
            }

            Log.d("Tests", "yes, thread thats all");
        });

        thread.start();

        /*Pools.SimplePool x = new Pools.SimplePool<Thread>();
        x.release(thread);*/

    }
}
