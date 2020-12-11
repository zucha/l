package lv.sit.todo;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.concurrent.Executor;
import org.junit.Test;

import static java.lang.Thread.sleep;
import static org.junit.Assert.*;
import androidx.collection.ArrayMap;

import lv.sit.todo.db.Item;

/**
 * Random string for random
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class RandomStringTest {

    @Test
    public void testNull ()
    {
        Item item = null; //= new Item();

        if (item == null)
        {
            assertTrue(true);
        } else
        {
            assertTrue(false);
        }


    }

    @Test
    public void testMap () {

        ArrayMap<Integer, String> x = new ArrayMap<Integer, String>();
        x.put(1, "viens");
        assertEquals(1, x.size());
        assertEquals("viens", x.get(1));
    }

    /**
     * @link https://stackoverflow.com/questions/58767733/android-asynctask-api-deprecating-in-android-11-what-are-the-alternatives
     * @ret
     */
    @Test
    public void asyncGenerate ()
    {
        // java.util.concurrent.*
        // Thread
        // Executor
        // Looper
        // Handler

        Thread t = new Thread(() -> {
            System.out.println("start");
            try
            {

                //Log.d(MainActivity.LOG_TAG, "start");
                for (int i = 0; i < 10; i++) {
                    sleep(100);
                }


            } catch (InterruptedException e)
            {
                System.out.println("interupted 1");
            }

            System.out.println("end");

        });

        t.start();

        // t.interrupt();

        try
        {
            t.join(10000);
        } catch (InterruptedException e)
        {
            System.out.println("interupted 2");
        }


        System.out.println("Ended main");
    }


}