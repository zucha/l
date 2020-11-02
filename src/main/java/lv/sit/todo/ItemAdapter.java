package lv.sit.todo;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import lv.sit.todo.db.DeleteThread;
import lv.sit.todo.db.Item;

/**
 * @link https://www.geeksforgeeks.org/generics-in-java/
 * @link https://github.com/android/views-widgets-samples/blob/master/RecyclerView/Application/src/main/java/com/example/android/recyclerview/CustomAdapter.java
 *
 * Simple example:
 * @link https://stackoverflow.com/questions/40584424/simple-android-recyclerview-example
 */
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    /**
     * Signleton instance
     */
    private static ItemAdapter instance;

    public int count = 0;

    public List<Item> items;

    /**
     * singleton
     */
    public static ItemAdapter getInstance()
    {
        if (instance == null)
        {
            instance = new ItemAdapter();
        }

        return instance;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create new row view.
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row, parent, false);

        return new ViewHolder(v);
    }

    /**
     * Binds the data to the TextView in each row
     * @param holder row nested views holder
     * @param position position in recycler list
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.rowRemoveView.setOnClickListener((View view) -> {
            Log.d(MainActivity.LOG_TAG, "Delete clicked " + position);

            /*
            ViewGroup.LayoutParams params = holder.rowLayout.getLayoutParams();
            params.width = 0;
            params.height = 0;
            holder.rowLayout.setLayoutParams(params);
            */
            // holder.rowLayout.setVisibility(View.GONE);

            new DeleteThread(holder.item).start();
        });

        holder.rowTexView.setOnClickListener((View view) -> {
            Log.d(MainActivity.LOG_TAG, "Row clicked " + position);

            int marginValue = 300;

            if (holder.expanded)
            {
                marginValue = 0;
            }

            holder.expanded = !holder.expanded;

            ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(marginLayoutParams);

            layoutParams.setMargins(marginValue, 0, -1 * marginValue, 0);

            view.setLayoutParams(layoutParams);
        });

        holder.item = items.get(position);
        holder.rowTexView.setText(holder.item.name);
    }

    @Override
    public int getItemCount() {
        return this.count;
    }

    /**
     * Hold information about row items
     */
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        /**
         * bind row layout text view
         */
        public TextView rowTexView;

        /**
         * is row slided
         */
        public boolean expanded = false;

        /**
         * DAO item
         */
        public Item item;

        /**
         * remove button
         */
        public TextView rowRemoveView;

        /**
         * main row view
         */
        public View rowLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            rowLayout = itemView;

            rowTexView = (TextView) itemView.findViewById(R.id.rowTextViewId);
            rowRemoveView = (TextView) itemView.findViewById(R.id.rowRemove);
        }
    }
}
