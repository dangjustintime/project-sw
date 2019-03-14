package com.example.justindang.storywell.views;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.justindang.storywell.R;
import com.example.justindang.storywell.activities.StoryEditorActivity;
import com.example.justindang.storywell.listeners.OnScaleListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StickerView extends LinearLayout {
    public static final int INVALID_POINTER_ID = -1000;
    public static final String TOUCH_EVENT_TAG = "TOUCH EVENT";
    private float pointer1X, pointer1Y, pointer2X, pointer2Y, angle1, angle2;
    ViewConfiguration viewConfiguration;
    int touchSlop;

    @BindView(R.id.linear_layout_sticker_view_container) LinearLayout containerLinearLayout;
    @BindView(R.id.image_view_x_icon_sticker_view) ImageView xIconImageView;

    private int activePointerId = INVALID_POINTER_ID;
    private float lastTouchX, lastTouchY;

    public interface OnStickerListener {
        void sendStickerViewId(int id);
    }
    OnStickerListener onStickerListener;

    public StickerView(Context context) {
        super(context);
        inflate(context, R.layout.custom_view_sticker, this);
        ButterKnife.bind(this);
        this.setId(generateViewId());

        viewConfiguration = ViewConfiguration.get(context);
        touchSlop = viewConfiguration.getScaledTouchSlop();
        Toast.makeText(context, String.valueOf(touchSlop), Toast.LENGTH_SHORT).show();

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
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = MotionEventCompat.getActionMasked(event);
        final int pointerIndex;
        final float x, y;

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                pointerIndex = MotionEventCompat.getActionIndex(event);
                x = MotionEventCompat.getX(event, pointerIndex);
                y = MotionEventCompat.getY(event, pointerIndex);
                lastTouchX = x;
                lastTouchY = y;
                activePointerId = MotionEventCompat.getPointerId(event, 0);
                break;
            case MotionEvent.ACTION_MOVE:
                pointerIndex = MotionEventCompat.findPointerIndex(event, activePointerId);
                x = MotionEventCompat.getX(event, pointerIndex);
                y = MotionEventCompat.getY(event, pointerIndex);
                final float dx = x - lastTouchX;
                final float dy = y - lastTouchY;
                setLeft(Math.round(getLeft() + dx));
                setTop(Math.round(getTop() + dy));
                invalidate();
                lastTouchX = x;
                lastTouchY = y;
                break;
            case MotionEvent.ACTION_UP:
                activePointerId = INVALID_POINTER_ID;
                break;
            case MotionEvent.ACTION_CANCEL:
                activePointerId = INVALID_POINTER_ID;
                break;
            case MotionEvent.ACTION_POINTER_UP:
                pointerIndex = MotionEventCompat.getActionIndex(event);
                final int pointerId = MotionEventCompat.getPointerId(event, pointerIndex);
                if (pointerId == activePointerId) {
                    final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
                    lastTouchX = MotionEventCompat.getX(event, newPointerIndex);
                    lastTouchY = MotionEventCompat.getY(event, newPointerIndex);
                    activePointerId = MotionEventCompat.getPointerId(event, newPointerIndex);
                }
                break;
        }

        return true;
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

    public void setSize(float scaleFactor) {
        // empty, must override
    }

    public float getAngle(float x1, float y1, float x2, float y2) {
        return (float) Math.toDegrees(Math.atan2(y2 - y1, x2 - x1));
    }

    public float getDistance(float x1, float y1, float x2, float y2) {
        return (float) Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

}
