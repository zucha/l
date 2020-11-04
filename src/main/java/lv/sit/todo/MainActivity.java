package lv.sit.todo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import lv.sit.todo.db.Database;
import lv.sit.todo.db.DbThread;
import lv.sit.todo.db.Item;
import lv.sit.todo.db.ItemDao;

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

        /*TextView view = findViewById(R.id.textView2);
        view.setText("000");*/
        //

        registerRecycleView ();

        registerFormActions ();


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
        // adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

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

            return null;
        }).start();
    }
}
