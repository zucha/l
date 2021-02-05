package lv.sit.todo;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import lv.sit.todo.db.Database;
import lv.sit.todo.db.Item;
import lv.sit.todo.db.ItemDao;
import lv.sit.todo.db.OrderThread;

public class ItemDialog extends DialogFragment {

    /**
     * if null - create new
     * if not null - updates
     */
    private final Item item;

    /**
     * Dialog to create new item
     */
    public ItemDialog() {
        item = null;
    }

    /**
     * Dialog to update existing item
     * @param item dao item to work with
     */
    public ItemDialog(Item item) {
        this.item = item;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(MainActivity.LOG_TAG, "onCreateView");
        View view = inflater.inflate(R.layout.form, container);

        Preferences preferences = new Preferences();

        selectedColor = view.findViewById(preferences.selectedButtonId());

        view.addOnLayoutChangeListener( (View v, int left, int top, int right, int bottom,
                                         int oldLeft, int oldTop, int oldRight, int oldBottom) -> {
            Log.d(MainActivity.LOG_TAG, "onCreateView layout change");
            Log.d(MainActivity.LOG_TAG, "left: " + left);
            Log.d(MainActivity.LOG_TAG, "right: " + right);

            _moveSelectedButton(selectedColor, selectedColorBackground, false);
        });

        return view;
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

        Log.d(MainActivity.LOG_TAG, "onViewCreated");

        EditText editText = view.findViewById(R.id.itemName);

        editText.requestFocus();

        InputMethodManager imm = (InputMethodManager) MainActivity.getInstance().getSystemService(Context.INPUT_METHOD_SERVICE);
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

        View selectedButton = selectedColorBackground = view.findViewById(R.id.selectedColor);

        // selectedColor =

        Button color1 = (Button) view.findViewById(R.id.submitColor1);
        color1.setOnClickListener((View v) -> _setColor (v, selectedButton));

        Button color2 = (Button) view.findViewById(R.id.submitColor2);
        color2.setOnClickListener((View v) -> _setColor (v, selectedButton));

        Button color3 = (Button) view.findViewById(R.id.submitColor3);
        color3.setOnClickListener((View v) -> _setColor (v, selectedButton));

        Button color4 = (Button) view.findViewById(R.id.submitColor4);
        color4.setOnClickListener((View v) -> _setColor (v, selectedButton));
    }

    /**
     * Save item
     */
    private void saveItem (String value) {
        Log.d(MainActivity.LOG_TAG, "save item");

        Preferences preferences = new Preferences();

        if (this.item == null)
        {
            Item item = new Item();
            item.name = value;

            item.color = preferences.selectedButtonColor();

            _insertItem (item);
        } else
        {
            this.item.name = value;
            this.item.color = preferences.selectedButtonColor();

            _updateItem (this.item);
        }

        getDialog().dismiss();
    }

    private void _insertItem (Item item)
    {
        new Thread(() -> {

            Log.d(MainActivity.LOG_TAG, "thread: " + Thread.currentThread().getName());
            Database db = Database.getInstance();

            ItemDao itemDao = db.getItemDao();

            itemDao.insert(item);

            ItemAdapter.getInstance().items = db.getAll();

            MainActivity.getInstance().runOnUiThread(() -> ItemAdapter.getInstance().notifyAllRows());

            OrderThread orderThread = new OrderThread();
            try {
                orderThread.join();
                orderThread.start();
            } catch (InterruptedException e)
            {
                e.getStackTrace();
            }
        }).start();
    }

    private void _updateItem (Item item)
    {
        new Thread(() -> {
            Database db = Database.getInstance();

            ItemDao itemDao = db.getItemDao();

            itemDao.update(item);

            ItemAdapter.getInstance().items = db.getAll();

            MainActivity.getInstance().runOnUiThread(() -> ItemAdapter.getInstance().notifyAllRows());
        }).start();
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);

        InputMethodManager imm = (InputMethodManager) MainActivity.getInstance().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
    }

    // onActivityCreated(@Nullable Bundle savedInstanceState) {
    private View selectedColor;
    private View selectedColorBackground;

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        Log.d(MainActivity.LOG_TAG, "onViewStateRestored");
        Log.d(MainActivity.LOG_TAG, "onViewStateRestored left:" + selectedColor.getLeft());
    }

    /**
     * Store color, move
     * get view x position
     * @param view
     */
    private void _setColor(View view, View selectedColor) {
        Preferences preferences = new Preferences();

        preferences.setButtonId(view.getId());

        _moveSelectedButton(view, selectedColor, true);
    }

    /**
     * Background color highlight move to active color
     * @param moveTo
     * @param selectedColor
     */
    private void _moveSelectedButton(View moveTo, View selectedColor, boolean animate) {

        Log.d(MainActivity.LOG_TAG, "Move to id: " + moveTo.getId());
        Log.d(MainActivity.LOG_TAG, "Left: " + moveTo.getLeft());

        float density = MainActivity.getInstance().getResources().getDisplayMetrics().density;

        int left = moveTo.getLeft();

        left -= (int) density * 5;

        if (animate)
        {
            selectedColor.animate().translationX(left);
        } else
        {
            selectedColor.setTranslationX( left );
        }
    }
}
