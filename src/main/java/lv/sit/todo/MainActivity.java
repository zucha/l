package lv.sit.todo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import lv.sit.todo.db.Database;
import lv.sit.todo.db.Item;
import lv.sit.todo.db.ItemDao;

public class MainActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_main);

        context = this.getApplicationContext();

        Log.d(LOG_TAG, context.getClass().toString());

        insertRandomItem();


        /*TextView view = findViewById(R.id.textView2);
        view.setText("000");*/
        //

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.mainRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));
        ItemAdapter adapter = new ItemAdapter(context);
        // adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    /**
     *
     */
    private void insertRandomItem ()
    {
        //InitBase baseRunnable = new InitBase(this.getApplicationContext());

        Thread thread = new Thread( () -> {
            Database db = Room.databaseBuilder(this.getApplicationContext(), Database.class, "test").build();

            ItemDao itemDao = db.getItemDao();

            /*Item item = new Item();

            item.name = "test 1";

            itemDao.insert(item);*/

            int rowCount = itemDao.getAll().size();

            // context.act
            /*this.runOnUiThread(() -> {
                TextView view = this.findViewById(R.id.textView2);
                view.setText(rowCount);
            });*/
        });

        thread.start();
    }
}
