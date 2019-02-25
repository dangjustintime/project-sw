package com.example.justindang.storywell.views;

import android.content.Context;
import android.view.MotionEvent;
import android.widget.EditText;

import com.example.justindang.storywell.R;

public class TextStickerView extends StickerView {
    EditText editText;

    public TextStickerView(Context context) {
        super(context);
        editText =  new EditText(context);
        editText.setBackground(null);
        editText.setTextColor(getResources().getColor(R.color.colorWhite));
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
