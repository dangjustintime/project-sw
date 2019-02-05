package com.example.justindang.storywell.views;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.justindang.storywell.R;

public class StickerView extends View {

    public StickerView(Context context) {
        super(context);
        this.setBackgroundResource(R.drawable.square_solid);
    }

    public void hideUi() {
        // empty, must override
    }
}
