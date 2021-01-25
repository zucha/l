package lv.sit.todo.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "item")
public class Item
{
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "position")
    public int position = 0;

    @ColumnInfo(name = "color")
    public int color;

    @ColumnInfo(name = "delete_mark")
    public int delete;

    @ColumnInfo(name = "postpone")
    public int postpone;
}
