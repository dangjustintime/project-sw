package com.example.justindang.storywell.views;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.justindang.storywell.R;

public class ShapeStickerView extends View {
    private LinearLayout linearLayout;
    private ImageView shapeImageView;
    private ImageView xImageView;

    public ShapeStickerView(Context context, String shape) {
        super(context);

        if (shape.equals("circle")) {

        } else if (shape.equals("square_solid")) {
            shapeImageView.setImageResource(R.drawable.square_solid);
        } else if (shape.equals("rectangle")) {

        }
        linearLayout.addView(shapeImageView);

    }

    @Override
    protected void onDraw(Canvas canvas) { }

}
