package lv.sit.todo.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ItemDao {
    @Query("SELECT count(*) FROM item;")
    int getCount();

    @Query("SELECT * FROM item")
    List<Item> getAll();

    @Query("SELECT * FROM item where id=:id;")
    Item findOne (int id);

    @Insert
    void insert (Item... items);

    @Delete
    void delete (Item item);
}
