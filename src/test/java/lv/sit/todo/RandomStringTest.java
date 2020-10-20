package lv.sit.todo;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

import org.junit.Test;

import java.util.concurrent.Executor;

import static org.junit.Assert.*;

/**
 * Random string for random
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class RandomStringTest {
    @Test
    public void addition_isCorrect()
    {
        String x = RandomString.generate();
        System.out.println(x);
        assertEquals (10, x.length());
    }

    /**
     * @link https://stackoverflow.com/questions/58767733/android-asynctask-api-deprecating-in-android-11-what-are-the-alternatives
     * @ret
     */
    @Test
    public void asyncGenerate ()
    {
        // java.util.concurrent.
        // Thread
        // Executor
        // Looper
        // Handler


    }
}