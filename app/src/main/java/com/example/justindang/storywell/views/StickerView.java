package com.example.justindang.storywell.views;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.justindang.storywell.R;
import com.example.justindang.storywell.activities.StoryEditorActivity;
import com.example.justindang.storywell.listeners.OnScaleListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StickerView extends LinearLayout {
    protected class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        private ImageView imageView;

        public ScaleListener(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            scaleFactor *= detector.getScaleFactor();
            scaleFactor = Math.max(0.95f, Math.min(scaleFactor, 1.05f));
            imageView.setLayoutParams(new LayoutParams(
                    Math.round(imageView.getWidth() * scaleFactor),
                    Math.round(imageView.getHeight() * scaleFactor)));
            invalidate();
            return true;
        }
    }

    public static final int INVALID_POINTER_ID = -1000;
    public static final String TOUCH_EVENT_TAG = "TOUCH EVENT";
    private float pointer1X, pointer1Y, pointer2X, pointer2Y;
    private int pointerId;
    private float scaleFactor = 1.5f;


    @BindView(R.id.linear_layout_sticker_view_container) LinearLayout containerLinearLayout;
    @BindView(R.id.image_view_x_icon_sticker_view) ImageView xIconImageView;

    float lastTouchX, lastTouchY, posX, posY;
    int activePointerId = INVALID_POINTER_ID;

    ScaleGestureDetector scaleGestureDetector;

    public interface OnStickerListener {
        void sendStickerViewId(int id);
    }
    OnStickerListener onStickerListener;

    public StickerView(Context context) {
        super(context);
        Activity activity = (Activity) context;
        onStickerListener = (OnStickerListener) activity;
        inflate(context, R.layout.custom_view_sticker, this);
        ButterKnife.bind(this);
        this.setId(generateViewId());

        // clicklistener
        xIconImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                hideView();
            }
        });
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onStickerListener.sendStickerViewId(getId());
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        activePointerId = event.getPointerId(0);
        int pointerIndex = event.findPointerIndex(activePointerId);
        int action = event.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                Log.i(TOUCH_EVENT_TAG, "first finger down");
                pointer1X = event.getX();
                pointer1Y = event.getY();
                Log.i(TOUCH_EVENT_TAG, "pointer 1: " + String.valueOf(pointer1X) + ", " + String.valueOf(pointer1Y));
                return true;
            case MotionEvent.ACTION_POINTER_DOWN:
                Log.i(TOUCH_EVENT_TAG, "second finger down");
                pointer2X = event.getX(1);
                pointer2Y = event.getY(1);
                Log.i(TOUCH_EVENT_TAG, "pointer 2: " + String.valueOf(pointer2X) + ", " + String.valueOf(pointer2Y));
                return true;
            case MotionEvent.ACTION_MOVE:
                Log.i(TOUCH_EVENT_TAG, "movement");
                float newPointer1X = event.getX();
                float newPointer1Y = event.getY();
                float dX = newPointer1X - pointer1X;
                float dY = newPointer1Y - pointer1Y;
                setLeft(Math.round(getLeft() + dX));
                setTop(Math.round(getTop() + dY));
                return true;
            case MotionEvent.ACTION_UP:
                pointer1X = event.getX();
                pointer1Y = event.getY();
                Log.i(TOUCH_EVENT_TAG, "first finger up");
                Log.i(TOUCH_EVENT_TAG, "pointer 1: " + String.valueOf(pointer1X) + ", " + String.valueOf(pointer1Y));
                return true;
            case MotionEvent.ACTION_POINTER_UP:
                pointer2X = event.getX(1);
                pointer2Y = event.getY(1);
                Log.i(TOUCH_EVENT_TAG, "second finger up");
                Log.i(TOUCH_EVENT_TAG, "pointer 2: " + String.valueOf(pointer2X) + ", " + String.valueOf(pointer2Y));
                return true;
            default:
                Log.i(TOUCH_EVENT_TAG, "action not found");
                break;
        }
        return false;
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.scale(scaleFactor, scaleFactor);
        canvas.restore();
    }

    public void hideUi() {
        this.xIconImageView.setVisibility(INVISIBLE);
        this.containerLinearLayout.setBackground(null);
    }

    public void hideView() {
        this.setVisibility(INVISIBLE);
    }

    public void setColor(int color) {
        // empty, must override
    }
}
