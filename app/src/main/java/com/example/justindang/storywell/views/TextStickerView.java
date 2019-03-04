package com.example.justindang.storywell.views;

import android.content.Context;
import android.support.annotation.IntDef;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.justindang.storywell.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.security.Key;

public class TextStickerView extends StickerView {
    EditText editText;

    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int CENTER = 2;

    @IntDef ({LEFT, RIGHT, CENTER})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Alignment { }

    public TextStickerView(Context context) {
        super(context);
        editText = new EditText(context);
        editText.setBackground(null);
        editText.setText("tap to edit");
        editText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        editText.setTextColor(getResources().getColor(R.color.colorWhite));
        containerLinearLayout.addView(editText);
        editText.requestFocus();


        /*
        if (editText.requestFocus()) {
            InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT);
        }
        */
    }

    @Override
    public void setColor(int color) {
        editText.setTextColor(color);
    }

    @Override
    public void hideUi() {
        super.hideUi();
        editText.setCursorVisible(false);
    }

    public EditText getEditText() {
        return editText;
    }
}
