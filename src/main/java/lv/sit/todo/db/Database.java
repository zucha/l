package lv.sit.todo.db;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import lv.sit.todo.MainActivity;

@androidx.room.Database(entities = {Item.class}, version = 2)
public abstract class Database extends RoomDatabase {
    /**
     * Database name
     */
    public static final String dbName = "test";

    /**
     *
     * @return dao object
     */
    public abstract ItemDao getItemDao();

    /**
     * Create db instance
     */
    public static final Database getInstance ()
    {
        return Room.databaseBuilder(
                MainActivity.getInstance().getApplicationContext(),
                Database.class,
                Database.dbName).build();
    }
}
