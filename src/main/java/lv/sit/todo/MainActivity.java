package lv.sit.todo;

import androidx.appcompat.app.AppCompatActivity;
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

    /**
     * public access to application context
     * @see android.app.Application
     */
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;

        setContentView(R.layout.activity_main);

        context = this.getApplicationContext();

        Log.d(LOG_TAG, context.getClass().toString());

        insertRandomItem();


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
        View formView = (View) findViewById(R.id.itemForm);
        formView.setVisibility(View.VISIBLE);

        Button button = (Button) findViewById(R.id.buttonItemAdd);

        button.setOnClickListener((View b) -> {
            EditText input = (EditText) findViewById(R.id.itemName);

            formView.setVisibility(View.GONE);

            Item item = new Item();

            item.name = input.getText().toString();

            insertItem(item);
        });

        ImageButton buttonClose = (ImageButton) findViewById(R.id.buttonCloseForm);

        buttonClose.setOnClickListener((View b) -> {
            formView.setVisibility(View.GONE);
        });

        ImageButton buttonOpen = (ImageButton) findViewById(R.id.buttonOpenForm);

        buttonOpen.setOnClickListener((View b) -> {
            formView.setVisibility(View.VISIBLE);
        });
    }

    private void registerRecycleView ()
    {
        Log.d(LOG_TAG, "try to attach adapter");

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.mainRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));
        ItemAdapter adapter = ItemAdapter.getInstance();
        // adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        new DbThread (context, () -> {
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
                Toast.makeText(context, "Data loaded", Toast.LENGTH_SHORT).show();
            });

            return null;
        }).start();
    }

    /**
     *
     */
    private void insertRandomItem ()
    {
        //InitBase baseRunnable = new InitBase(this.getApplicationContext());

        Thread thread = new Thread( () -> {
            Database db = Room.databaseBuilder(context, Database.class, "test").build();

            ItemDao itemDao = db.getItemDao();

/*            Item item = new Item();

            item.name = "test " + RandomString.generate();

            itemDao.insert(item);
*/
            int rowCount = itemDao.getAll().size();

            Log.d(LOG_TAG, "random items count: " + String.valueOf(rowCount));

            // context.act
            /*this.runOnUiThread(() -> {
                TextView view = this.findViewById(R.id.textView2);
                view.setText(rowCount);
            });*/
        });

        thread.start();
    }

    /**
     *
     */
    private void insertItem (Item item)
    {
        //InitBase baseRunnable = new InitBase(this.getApplicationContext());

        Thread thread = new Thread( () -> {
            Database db = Room.databaseBuilder(context, Database.class, Database.dbName).build();

            ItemDao itemDao = db.getItemDao();

            itemDao.insert(item);

            int rowCount = itemDao.getAll().size();

            Log.d(LOG_TAG, "random items count: " + String.valueOf(rowCount));

            ItemAdapter.getInstance().count = itemDao.getCount();
            ItemAdapter.getInstance().items = itemDao.getAll();

            runOnUiThread(() -> {
                ItemAdapter.getInstance().notifyDataSetChanged();
            });
        });

        thread.start();
    }
}
