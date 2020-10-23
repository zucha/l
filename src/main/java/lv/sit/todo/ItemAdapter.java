package lv.sit.todo;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import lv.sit.todo.db.Database;
import lv.sit.todo.db.ItemDao;

/**
 * @link https://www.geeksforgeeks.org/generics-in-java/
 * @link https://github.com/android/views-widgets-samples/blob/master/RecyclerView/Application/src/main/java/com/example/android/recyclerview/CustomAdapter.java
 *
 * Simple example:
 * @link https://stackoverflow.com/questions/40584424/simple-android-recyclerview-example
 */
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    /**
     *
     */
    private Context context;

    public ItemAdapter (Context context)
    {
        Log.d(MainActivity.LOG_TAG, "Item adapter contructor");
        this.context = context;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // this.context = MainActivity.context;

        Log.d(MainActivity.LOG_TAG, "Create viwv holder");
        Log.d(MainActivity.LOG_TAG, this.context.getClass().toString());

        // Create a new view.
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row, parent, false);

        return new ViewHolder(v);
    }

    /**
     * binds the data to the TextView in each row
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // String animal = mData.get(position);
        holder.rowTexView.setText("Test text");
    }

    @Override
    public int getItemCount() {

        Database db = Room.inMemoryDatabaseBuilder(MainActivity.context, Database.class).build();
        ItemDao itemDao = db.getItemDao();

        return itemDao.getCount();
    }

    /**
     *
     */
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        /**
         * bind row layout text view
         */
        public TextView rowTexView;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);

            rowTexView = (TextView) itemView.findViewById(R.id.rowTextViewId);
        }
    }
}
