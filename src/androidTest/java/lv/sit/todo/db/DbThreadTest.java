package lv.sit.todo.db;

import android.util.Log;
import org.junit.Assert;
import org.junit.Test;

public class DbThreadTest {
    /**
     *
     */
    @Test
    public void testThread ()
    {
        DbThread thread = new DbThread(() -> {
            Log.d("TEST", "test complete thread");
            System.out.println("test completed");
            Assert.assertTrue(true);
        });

        thread.start();
    }

}
