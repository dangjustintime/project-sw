package com.example.justindang.storywell.views;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
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
    Drawable drawable;

    public ShapeStickerView(Context context, @Shape int shape, boolean isSolid) {
        super(context);
        shapeImageView = new ImageView(context);
        scaleGestureDetector = new ScaleGestureDetector(context, new StickerView.ScaleListener(shapeImageView));

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

        // click listener
        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // onScale Listener
                scaleGestureDetector.onTouchEvent(event);

                // onDrag Listener
                final int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN: {
                        final int pointerIndex = event.getActionIndex();
                        final float x = event.getX(pointerIndex);
                        final float y = event.getY(pointerIndex);
                        // remember where drag started
                        lastTouchX = x;
                        lastTouchY = y;
                        // save pointer id
                        activePointerId = event.getPointerId(0);
                        break;
                    }
                    case MotionEvent.ACTION_MOVE: {
                        // find index of active point and its location
                        final int pointerIndex = event.findPointerIndex(activePointerId);
                        final float x = event.getX(pointerIndex);
                        final float y = event.getY(pointerIndex);

                        // calculate distance
                        final float dx = x - lastTouchX;
                        final float dy = y - lastTouchY;
                        posX += dx;
                        posY += dy;

                        invalidate();

                        // remember position for next touch event
                        lastTouchX = x;
                        lastTouchY = y;

                        // move view
                        v.setLeft((int) posX);
                        v.setTop((int) posY);

                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        activePointerId = INVALID_POINTER_ID;
                        break;
                    }
                    case MotionEvent.ACTION_CANCEL: {
                        activePointerId = INVALID_POINTER_ID;
                        break;
                    }
                    case MotionEvent.ACTION_POINTER_UP: {
                        final int pointerIndex = event.getActionIndex();
                        final int pointerId = event.getPointerId(pointerIndex);

                        if (pointerId == activePointerId) {
                            // switch active pointer
                            final int newPointerIndex = pointerIndex == 0 ? 1: 0;
                            lastTouchX = event.getX(newPointerIndex);
                            lastTouchY = event.getY(newPointerIndex);
                            activePointerId = event.getPointerId(newPointerIndex);
                        }
                        break;
                    }
                }
                return true;
            }
        });
    }

    @Override
    public void setColor(int color) {
        Drawable drawable = shapeImageView.getDrawable();
        drawable.setColorFilter(color, PorterDuff.Mode.SRC);
    }
}
