package lv.sit.todo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import lv.sit.todo.db.DbThread;
import lv.sit.todo.db.DeleteThread;
import lv.sit.todo.db.Item;
import lv.sit.todo.db.OrderThread;

public class MainActivity extends AppCompatActivity {

    private static MainActivity instance;

    /**
     * @return Singleton
     */
    public static MainActivity getInstance()
    {
        return instance;
    }

    /**
     * Tag for loging
     */
    public final static String LOG_TAG = "todoapp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;

        setContentView(R.layout.activity_main);

        registerRecycleView ();

        registerFormActions ();

        Button b = findViewById(R.id.testButton);
        b.setOnClickListener((View v) -> {
            ItemAdapter.getInstance().notifyAllRows();
        });

        Toast.makeText(this, "App loaded", Toast.LENGTH_SHORT).show();
    }

    /**
     *
     */
    private void registerFormActions ()
    {
        ImageButton buttonOpen = (ImageButton) findViewById(R.id.buttonOpenForm);

        buttonOpen.setOnClickListener((View b) -> {
            FragmentManager fm = getSupportFragmentManager();
            ItemDialog itemDialog = new ItemDialog();
            itemDialog.show(fm, "test_tag");
        });
    }

    private void registerRecycleView () {
        Log.d(LOG_TAG, "try to attach adapter");

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.mainRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));
        ItemAdapter adapter = ItemAdapter.getInstance();
        recyclerView.setAdapter(adapter);

        ItemHelperCallback dragDropCallback = new ItemHelperCallback();
        ItemTouchHelper touchHelper = new ItemTouchHelper(dragDropCallback);
        touchHelper.attachToRecyclerView(recyclerView);

        SwipeMenuListener swipeMenuLisener = new SwipeMenuListener (recyclerView);
        swipeMenuLisener.onDelete ((Item item) -> {
            Log.d(LOG_TAG, "On delete callback: " + item.id);
            (new DeleteThread(item)).start();
        });
        swipeMenuLisener.onEdit ((Item item) -> {
            Log.d(LOG_TAG, "On edit callback: " + item.id);
            FragmentManager fm = MainActivity.getInstance().getSupportFragmentManager();
            ItemDialog itemDialog = new ItemDialog(item);
            itemDialog.show(fm, "test_tag");
        });

        new DbThread(() -> {
            Log.d(LOG_TAG, "Do refresh: " + ItemAdapter.getInstance().count);

            /*
            RecyclerView recyclerView = findViewById(R.id.mainRecycler);
            recyclerView.invalidate();
            recyclerView.refreshDrawableState();
            */

            runOnUiThread(() -> {
                ItemAdapter.getInstance().notifyDataSetChanged();
            });

            runOnUiThread(() -> {
                Toast.makeText(getApplicationContext(), "Data loaded", Toast.LENGTH_SHORT).show();
            });

            new OrderThread().start();

            return null;
        }).start();
    }
}
