package com.example.justindang.storywell.views;

import android.content.Context;
import android.widget.EditText;

import com.example.justindang.storywell.R;

public class TextStickerView extends StickerView {
    EditText editText;

    public TextStickerView(Context context) {
        super(context);
        editText =  new EditText(context);
        editText.setTextColor(getResources().getColor(R.color.colorWhite));
        containerLinearLayout.addView(editText);
    }

    @Override
    public void setColor(int color) {
        editText.setTextColor(color);
    }

}
