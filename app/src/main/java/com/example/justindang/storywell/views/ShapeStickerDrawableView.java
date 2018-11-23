package com.example.justindang.storywell.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RectShape;
import android.view.View;

import com.example.justindang.storywell.presenter.StoriesPresenter;

public class ShapeStickerDrawableView extends View {
    private ShapeDrawable shapeDrawable;

    public ShapeStickerDrawableView(Context context, String shape) {
        super(context);
        // dimensions
        int x = 100;
        int y = 100;
        int width = 300;
        int height = 300;

        if (shape.equals("circle")) {
            shapeDrawable = new ShapeDrawable(new OvalShape());
        } else if (shape.equals("square")) {
            shapeDrawable = new ShapeDrawable(new RectShape());
        } else if (shape.equals("rectangle")) {
            height = 150;
            shapeDrawable = new ShapeDrawable(new RectShape());
        }

        shapeDrawable.setBounds(x, y, x + width, y + height);
    }

    protected void onDraw(Canvas canvas) {
        shapeDrawable.draw(canvas);
    }

}
