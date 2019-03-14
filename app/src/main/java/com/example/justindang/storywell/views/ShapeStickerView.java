package com.example.justindang.storywell.views;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.support.annotation.IntDef;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.justindang.storywell.R;
import com.example.justindang.storywell.activities.StoryEditorActivity;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import butterknife.BindView;

public class ShapeStickerView extends StickerView {
    public static final int SQUARE = 0;
    public static final int CIRCLE = 1;
    public static final int RECTANGLE = 2;

    @IntDef ({SQUARE, CIRCLE, RECTANGLE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Shape { }

    ImageView shapeImageView;

    public ShapeStickerView(Context context, @Shape int shape, boolean isSolid) {
        super(context);
        Activity activity = (Activity) context;
        onStickerListener = (OnStickerListener) activity;
        shapeImageView = new ImageView(context);

        if (isSolid) {
            switch (shape) {
                case SQUARE:
                    shapeImageView.setImageResource(R.drawable.square_solid);
                    break;
                case CIRCLE:
                    shapeImageView.setImageResource(R.drawable.circle_solid);
                    break;
                case RECTANGLE:
                    shapeImageView.setImageResource(R.drawable.rectangle_solid);
                    break;
            }
        } else {
            switch (shape) {
                case SQUARE:
                    shapeImageView.setImageResource(R.drawable.square);
                    break;
                case CIRCLE:
                    shapeImageView.setImageResource(R.drawable.circle);
                    break;
                case RECTANGLE:
                    shapeImageView.setImageResource(R.drawable.rectangle);
                    break;
            }
        }

        containerLinearLayout.addView(shapeImageView);
    }

    @Override
    public void setSize(float scaleFactor) {
        scaleFactor *= 0.5;
        int newWidth = Math.round(shapeImageView.getWidth() + scaleFactor);
        int newHeight = Math.round(shapeImageView.getHeight() + scaleFactor);
        shapeImageView.setLayoutParams(new LayoutParams(newWidth, newHeight));
    }

    @Override
    public void setColor(int color) {
        Drawable drawable = shapeImageView.getDrawable();
        drawable.setColorFilter(color, PorterDuff.Mode.SRC);
    }
}
