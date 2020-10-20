package lv.sit.todo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import lv.sit.todo.db.Database;
import lv.sit.todo.db.Item;
import lv.sit.todo.db.ItemDao;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        insertRandomItem();

        /*TextView view = findViewById(R.id.textView2);
        view.setText("000");*/
        //
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
