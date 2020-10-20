package lv.sit.todo.db;

import androidx.room.RoomDatabase;

@androidx.room.Database(entities = {Item.class}, version = 1)
public abstract class Database extends RoomDatabase {
    /**
     *
     * @return dao object
     */
    public abstract ItemDao getItemDao();
}
