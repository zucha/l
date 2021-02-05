package lv.sit.todo;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class PreferencesTest {

    /**
     *
     */
    @Test
    public void testReadValue() {
        Context context = ApplicationProvider.getApplicationContext();

        // context.getPreferences(Context.MODE_PRIVATE);

        SharedPreferences sp = context.getSharedPreferences(PreferencesTest.class.toString(), Context.MODE_PRIVATE);

        Assert.assertFalse(sp.contains("gop"));

        Assert.assertTrue(sp.edit().putString("gop", "hop").commit());

        Assert.assertFalse(sp.contains("gop"));

        Assert.assertTrue(sp.contains("gop"));


    }
}
