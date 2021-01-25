package lv.sit.todo.db;

import android.util.Log;
import androidx.recyclerview.widget.RecyclerView;
import lv.sit.todo.ItemAdapter;
import lv.sit.todo.MainActivity;
import lv.sit.todo.R;

public class OrderThread extends Thread {
    /**
     * Set order by adapter position
     */
    public OrderThread ()
    {
        super(() -> {
            Database db = Database.getInstance();

            int count = ItemAdapter.getInstance().getItemCount();

            // Log.d(MainActivity.LOG_TAG, "Item count" + count);

            RecyclerView recyclerView = MainActivity.getInstance().findViewById(R.id.mainRecycler);

            // recyclerView.findViewHolderForLayoutPosition()

            ItemDao itemDao = db.getItemDao();

            Log.d(MainActivity.LOG_TAG, "Start order, " + count);

            for (int i = 0; i < count; i++) {
                Log.d(MainActivity.LOG_TAG, "Item position " + i);

                // ItemAdapter.ViewHolder viewHolder = (ItemAdapter.ViewHolder) recyclerView.findViewHolderForAdapterPosition(i);
                ItemAdapter.ViewHolder viewHolder = (ItemAdapter.ViewHolder) recyclerView.findViewHolderForLayoutPosition(i);

                if (viewHolder == null)
                {
                    Log.w(MainActivity.LOG_TAG, "Item id is null");
                } else
                {
                    // Log.d(MainActivity.LOG_TAG, "Item id " + viewHolder.item.name);
                    viewHolder.item.position = i+1;

                    itemDao.update(viewHolder.item);
                }
            }
        });
    }
}
