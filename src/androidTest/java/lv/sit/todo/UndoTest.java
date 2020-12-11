package lv.sit.todo;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class UndoTest {
    @Test
    public void testValues() {
        Undo.test();
        Undo undo = new Undo();
        Undo.test();


    }
}
