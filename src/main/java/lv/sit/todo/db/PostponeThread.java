package lv.sit.todo.db;

import lv.sit.todo.ItemAdapter;
import lv.sit.todo.MainActivity;
import lv.sit.todo.Time;

public class PostponeThread extends Thread {
    private Item item;

    public PostponeThread(Item item) {
        this.item = item;
    }

    /**
     * Set items delay to
     */
    public void run() {
        if (item == null) {
            return;
        }

        ItemDao itemDao = Database.getInstance().getItemDao();

        Time time = new Time();

        item.postpone = time.nextTime();

        itemDao.update(item);

        ItemAdapter.getInstance().items = Database.getInstance().getAll();

        MainActivity.getInstance().runOnUiThread(() -> {
            ItemAdapter.getInstance().notifyAllRows();
        });
    }
}
