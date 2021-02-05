package lv.sit.todo;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.view.View;

/**
 * Selected color
 */
public class Preferences {
    public static final String COLOR = "color";

    private int _defaultButtonId;

    public Preferences() {
        _defaultButtonId = R.id.submitColor1;
    }

    /**
     * Last selected
     * @return
     */
    public int selectedButtonId () {
        return _preferences().getInt(COLOR, _defaultButtonId);
    }

    public void setButtonId (int id) {
        _preferences().edit().putInt(COLOR, id).apply();
    }

    /**
     * @return proferences storage
     */
    private SharedPreferences _preferences () {
        return MainActivity.getInstance().getPreferences(Context.MODE_PRIVATE);
    }

    /**
     * @return selected button color
     */
    public int selectedButtonColor() {
        View view = MainActivity.getInstance().findViewById(selectedButtonId());
        ColorDrawable colorDrawable = (ColorDrawable) view.getBackground();
        return colorDrawable.getColor();
    }
}
