package lv.sit.todo.db;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;
import lv.sit.todo.MainActivity;

/**
 * TODO /home/uldis/AndroidStudioProjects/TodoList/app/src/main/java/lv/sit/todo/db/Database.java:11: warning: Schema export directory is not provided to the annotation processor so we cannot export the schema. You can either provide `room.schemaLocation` annotation processor argument OR set exportSchema to false.
 * TODO public abstract class Database extends RoomDatabase {
 *                 ^
 */
@androidx.room.Database(entities = {Item.class}, version = 3)
public abstract class Database extends RoomDatabase {
    /**
     * Database name
     */
    public static final String dbName = "items";

    /**
     *
     * @return dao object
     */
    public abstract ItemDao getItemDao();

    /**
     * Create db instance
     */
    public static Database getInstance ()
    {
/*        final Migration migration1_2 = new Migration(1, 2) {
            @Override
            public void migrate (SupportSQLiteDatabase database)
            {
                database.execSQL("ALTER TABLE item "
                        + " ADD COLUMN position INTEGER NOT NULL DEFAULT 0;");

                database.execSQL("ALTER TABLE item "
                        + " ADD COLUMN color TEXT;");
            }
        };

        final Migration migration2_3 = new Migration(2, 3) {
            @Override
            public void migrate (SupportSQLiteDatabase database)
            {
                database.execSQL("ALTER TABLE item RENAME COLUMN \"color\" TO tmp_color;");

                database.execSQL("ALTER TABLE item "
                        + " ADD COLUMN color INTEGER DEFAULT 0 NOT NULL;");

                database.execSQL("ALTER TABLE item "
                        + " ADD COLUMN delete_mark INTEGER DEFAULT 0 NOT NULL;");

                database.execSQL("ALTER TABLE item "
                        + " ADD COLUMN postpone INTEGER DEFAULT 0 NOT NULL;");
            }
        };*/

        return Room.databaseBuilder(
                MainActivity.getInstance().getApplicationContext(),
                Database.class,
                Database.dbName)
                // .addMigrations(migration1_2, migration2_3)
                .build();
    }
}
