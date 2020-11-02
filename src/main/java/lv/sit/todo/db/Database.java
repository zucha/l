package lv.sit.todo.db;

import androidx.room.RoomDatabase;

@androidx.room.Database(entities = {Item.class}, version = 1)
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
}
