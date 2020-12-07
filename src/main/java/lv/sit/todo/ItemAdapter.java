package lv.sit.todo;

import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
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
/*        holder.rowTexView.setOnClickListener((View view) -> {
            Log.d(MainActivity.LOG_TAG, "Row clicked " + position);

            int marginValue = 180;

            if (holder.expanded)
            {
                marginValue = 0;
            }

            holder.expanded = !holder.expanded;

            ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(marginLayoutParams);

            layoutParams.setMargins(marginValue, 0, -1 * marginValue, 0);

            view.setLayoutParams(layoutParams);
        });*/

        // holder.rowTexView.setOn
/*        holder.rowTexView.setOnLongClickListener((View view) -> {
            // Edit this
            FragmentManager fm = MainActivity.getInstance().getSupportFragmentManager();
            ItemDialog itemDialog = new ItemDialog(holder.item);
            itemDialog.show(fm, "test_tag");
            return true;
        });*/

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
         * main row view
         */
        public View rowLayout;

        /**
         * delete swipe menu background bounds
         */
        public Rect deleteBounds;

        /**
         * Edit swipe menu background bounds
         */
        public Rect editBounds;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            rowLayout = itemView;

            rowTexView = (TextView) itemView.findViewById(R.id.rowTextViewId);

            deleteBounds = new Rect();
            editBounds = new Rect();

            View bgView = (View) itemView.findViewById(R.id.rowLayout);
            View buttons = (View) itemView.findViewById(R.id.rowButtons);

            bgView.setOnTouchListener(new RowSwipe(rowTexView, buttons));

            Button delete = (Button) buttons.findViewById(R.id.deleteButton);
            Button edit = (Button) buttons.findViewById(R.id.editButton);

            delete.setOnClickListener ((View v) -> {
                Log.d(MainActivity.LOG_TAG, "On delete callback: " + item.id);
                (new DeleteThread(item)).start();
            });

            edit.setOnClickListener ((View v) -> {
                Log.d(MainActivity.LOG_TAG, "On edit callback: " + item.id);
                FragmentManager fm = MainActivity.getInstance().getSupportFragmentManager();
                ItemDialog itemDialog = new ItemDialog(item);
                itemDialog.show(fm, "test_tag");
            });
        }
    }

    /**
     * Item changed / deleted
     * Set back swiped items
     * @see RecyclerView.Adapter#notifyDataSetChanged()
     * @return void
     */
    public void notifyAllRows ()
    {
        RecyclerView recyclerView = (RecyclerView) MainActivity.getInstance().findViewById(R.id.mainRecycler);
        for (int i=0 ; i < items.size(); i++)
        {
            ItemAdapter.ViewHolder holder = (ItemAdapter.ViewHolder) recyclerView.findViewHolderForAdapterPosition(i);

            if (holder == null)
            {
                continue;
            }
            if (!holder.expanded)
            {
                continue;
            }

            holder.editBounds = new Rect();
            holder.deleteBounds = new Rect();
            holder.expanded = false;
            this.notifyItemChanged(i);
        }

        //this.notifyDataSetChanged();
    }
}
