package lv.sit.todo.db;

import android.content.Context;
import android.util.Log;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import org.junit.Assert;
import org.junit.Test;

public class DbThreadTest {
    /**
     *
     */
    @Test
    public void testThread ()
    {
        DbThread thread = new DbThread(ApplicationProvider.getApplicationContext(), () -> {
            Log.d("TEST", "test complete thread");
            System.out.println("test completed");
            Assert.assertTrue(true);
            return null;
        });

        thread.start();
    }

}
