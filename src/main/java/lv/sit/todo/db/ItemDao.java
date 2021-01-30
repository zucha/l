package lv.sit.todo.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface ItemDao {
    @Query("SELECT * FROM item WHERE delete_mark=0 AND postpone<=:time ORDER BY position;")
    List<Item> getAll(long time);

    @Query("SELECT * FROM item where id=:id;")
    Item findOne (int id);

    @Insert
    void insert (Item... items);

    @Delete
    void delete (Item item);

    @Update
    public void update(Item... items);

    @Query("DELETE FROM item WHERE delete_mark=1;")
    public void deleteMarked ();
}
