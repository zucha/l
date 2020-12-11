package lv.sit.todo;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.room.Room;

import lv.sit.todo.db.Database;
import lv.sit.todo.db.Item;
import lv.sit.todo.db.ItemDao;
import lv.sit.todo.db.OrderThread;

public class ItemDialog extends DialogFragment {

    /**
     * if null - create new
     * if not null - updates
     */
    private Item item;

    /**
     * Dialog to create new item
     */
    public ItemDialog() {
        item = null;
    }

    /**
     * Dialog to update existing item
     * @param item
     */
    public ItemDialog(Item item) {
        this.item = item;
    }
/*    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setPositiveButton("Save", (dialog, which) -> {
            // DialogInterface.onClick
            Log.d(MainActivity.LOG_TAG, "Dialog clicked");
        });

        return alertDialogBuilder.create();
        // return super.onCreateDialog(savedInstanceState);
    }*/

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.form, container);
        // return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onResume() {
        // Get existing layout params for the window
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        // Assign window properties to fill the parent
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
        // Call super onResume after sizing
        super.onResume();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText editText = view.findViewById(R.id.itemName);

        editText.requestFocus();

        InputMethodManager imm = (InputMethodManager) MainActivity.getInstance().getSystemService(MainActivity.getInstance().INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

        editText.setOnEditorActionListener((textView, actionId, keyEvent) -> {

            String value = editText.getText().toString();

            if (value.equals(""))
            {
                return false;
            }

            if (EditorInfo.IME_ACTION_DONE == actionId || EditorInfo.IME_ACTION_UNSPECIFIED == actionId)
            {
                // imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                saveItem(value);
                return true;
            }

            Log.d(MainActivity.LOG_TAG, "action id - " + actionId);
            return false;
        });

        if (item != null)
        {
            editText.setText(item.name);
        }
    }

    /**
     *
     */
    private void saveItem (String value) {
        Log.d(MainActivity.LOG_TAG, "save item");

        if (this.item == null)
        {
            Item item = new Item();
            item.name = value;

            _insertItem (item);
        } else
        {
            this.item.name = value;
            _updateItem (this.item);
        }

        getDialog().dismiss();
    }

    private void _insertItem (Item item)
    {
        new Thread(() -> {
            Database db = Database.getInstance();

            ItemDao itemDao = db.getItemDao();

            itemDao.insert(item);

            ItemAdapter.getInstance().count = itemDao.getCount();
            ItemAdapter.getInstance().items = itemDao.getAll();

            MainActivity.getInstance().runOnUiThread(() -> {
                ItemAdapter.getInstance().notifyAllRows();
            });

            new OrderThread().start();
            
        }).start();
    }

    private void _updateItem (Item item)
    {
        new Thread(() -> {
            Database db = Room.databaseBuilder(
                    MainActivity.getInstance().getApplicationContext(),
                    Database.class,
                    Database.dbName
            ).build();

            ItemDao itemDao = db.getItemDao();

            itemDao.update(this.item);

            // ItemAdapter.getInstance().count = itemDao.getCount();
            ItemAdapter.getInstance().items = itemDao.getAll();

            MainActivity.getInstance().runOnUiThread(() -> {
                ItemAdapter.getInstance().notifyAllRows();
            });
        }).start();
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);

        InputMethodManager imm = (InputMethodManager) MainActivity.getInstance().getSystemService(MainActivity.getInstance().INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
    }
}
