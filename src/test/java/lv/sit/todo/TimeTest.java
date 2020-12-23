package lv.sit.todo;


import android.graphics.Color;

import org.junit.Test;

import lv.sit.todo.db.Item;

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


}
