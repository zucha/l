package lv.sit.todo;


import android.graphics.Color;

import org.junit.Test;

import lv.sit.todo.db.Item;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertTrue;

public class TimeTest {
    @Test
    public void testDateTime ()
    {
        Time t = new Time();
        long x = t.currentTime ();

        long next = t.nextTime();

        assertTrue(x <= next);

        // 1607930253435
        // 1607930350870
        // 1607930506063
        // 1607930709
        // 1607930725
        // 2114125312
        System.out.println("Current time: " + x);
        System.out.println("Next  time: " + next);
        System.out.println("Color: " + Color.WHITE);
        System.out.println("Color: " + Color.RED);
        System.out.println("Color: " + Color.DKGRAY);


    }

    /**
     *
     */
    @Test
    public void testThreadJoin ()
    {
        Thread t = new Thread(() -> {
            System.out.println("Start thread");

            try
            {
                Thread x = new Thread (() -> {
                    System.out.println("Start thread 2");
                    // sleep(10);
                });
                x.join();
                x.start();
                sleep(10);
            } catch (InterruptedException e)
            {

            }

            System.out.println("End thread");
        });

        t.start();
    }
}
