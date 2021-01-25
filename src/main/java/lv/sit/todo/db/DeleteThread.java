package lv.sit.todo.db;

import android.util.Log;
import android.widget.Toast;
import lv.sit.todo.ItemAdapter;
import lv.sit.todo.MainActivity;
import lv.sit.todo.Undo;

/**
 * Delete one Item
 */
public class DeleteThread extends Thread {
    /**
     * @param item database item
     */
    public DeleteThread(Item item)
    {
        super(() -> {
            Database db = Database.getInstance();

            ItemDao itemDao = db.getItemDao();

            Log.d(Undo.TAG, "delete thread " + item.id);

            itemDao.delete(item);

            ItemAdapter.getInstance().items = itemDao.getAll();
            ItemAdapter.getInstance().count = itemDao.getCount();

            MainActivity.getInstance().runOnUiThread(() -> {
                ItemAdapter.getInstance().notifyAllRows();
                Toast.makeText(MainActivity.getInstance().getApplicationContext(), "Item deleted", Toast.LENGTH_SHORT).show();
            });
        });
    }
}
