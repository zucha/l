package lv.sit.todo;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
 * <p>
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
    public static ItemAdapter getInstance() {
        if (instance == null) {
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
     *
     * @param holder   row nested views holder
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

        Log.d(MainActivity.LOG_TAG, "Items total: " + items.size());
        Log.d(MainActivity.LOG_TAG, "Bind position: " + position);

        holder.item = items.get(position);
        holder.rowTexView.setText(holder.item.name);

        View rowLayout = holder.rowLayout;
        ViewGroup.LayoutParams params = rowLayout.getLayoutParams();

        params.height = getDefaultRowHeight();
        rowLayout.setLayoutParams(params);
        holder.rowTexView.setTranslationX(0);

        View buttons = rowLayout.findViewById(R.id.rowButtons);
        buttons.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return this.count;
    }

    /**
     * Hold information about row items
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
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
         * Define viewholder actions
         *
         * @param itemView
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            rowLayout = itemView;

            rowTexView = (TextView) itemView.findViewById(R.id.rowTextViewId);

            // itemView.setOnTouchListener(new RowSwipe(this));

            Button delete = (Button) itemView.findViewById(R.id.deleteButton);
            Button edit = (Button) itemView.findViewById(R.id.editButton);

            delete.setOnClickListener((View v) -> {
                Log.d(MainActivity.LOG_TAG, "On delete callback: " + item.id);
                (new DeleteThread(item)).start();
            });

            edit.setOnClickListener((View v) -> {
                Log.d(MainActivity.LOG_TAG, "On edit callback: " + item.id);
                FragmentManager fm = MainActivity.getInstance().getSupportFragmentManager();
                ItemDialog itemDialog = new ItemDialog(item);
                itemDialog.show(fm, "test_tag");

                new RowSwipeAnimation(this).startDrawBack();
            });

            // tmp delete button
            Button deleteTmp = (Button) itemView.findViewById(R.id.deleteButtonTmp);

            deleteTmp.setOnClickListener((View v) -> {
                // new DeleteThread(item).start();
                Undo.set(this, item);
            });
        }

        /**
         * When row is swiped out of view to delete item
         */
        public void onSwipeDelete() {
            Undo.set(this, this.item);
        }
    }

    /**
     * Item changed / deleted
     * Set back swiped items
     *
     * @return void
     * @see RecyclerView.Adapter#notifyDataSetChanged()
     */
    public void notifyAllRows() {
/*        RecyclerView recyclerView = (RecyclerView) MainActivity.getInstance().findViewById(R.id.mainRecycler);
        for (int i = 0; i < items.size(); i++) {
            ItemAdapter.ViewHolder holder = (ItemAdapter.ViewHolder) recyclerView.findViewHolderForAdapterPosition(i);

            if (holder == null) {
                continue;
            }
            if (!holder.expanded) {
                continue;
            }

            holder.expanded = false;
            this.notifyItemChanged(i);
        }*/

        this.notifyDataSetChanged();
    }

    /**
     * Default row height
     * @return row height in pixels
     */
    public static int getDefaultRowHeight () {
        // float factor = holder.itemView.getContext().getResources().getDisplayMetrics().density;

        return (int) Math.ceil(70 * MainActivity.getInstance().getResources().getDisplayMetrics().density);
    }
}
