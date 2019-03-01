package com.example.justindang.storywell.views;

import android.content.Context;
import android.support.annotation.IntDef;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.justindang.storywell.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

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
        editText.setTextColor(getResources().getColor(R.color.colorWhite));
        editText.requestFocus();
        containerLinearLayout.addView(editText);
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
}
