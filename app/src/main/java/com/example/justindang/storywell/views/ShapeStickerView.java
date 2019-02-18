package com.example.justindang.storywell.views;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntDef;
import android.util.Log;
import android.widget.ImageView;

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
    Drawable drawable;

    public ShapeStickerView(Context context, @Shape int shape, boolean isSolid) {
        super(context);
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
    public void setColor(int color) {
        Drawable drawable = shapeImageView.getDrawable();
        drawable.setColorFilter(color, PorterDuff.Mode.SRC);
    }
}
