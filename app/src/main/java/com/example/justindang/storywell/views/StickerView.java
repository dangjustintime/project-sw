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

    private int activePointerId1 = INVALID_POINTER_ID;
    private int activePointerId2 = INVALID_POINTER_ID;
    private float lastTouchX1, lastTouchY1, lastTouchX2, lastTouchY2, theta1, theta2;

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
        final int pointerIndex1, pointerIndex2;
        final float x1, y1, x2, y2;

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                pointerIndex1 = MotionEventCompat.getActionIndex(event);
                lastTouchX1 = MotionEventCompat.getX(event, pointerIndex1);
                lastTouchY1 = MotionEventCompat.getY(event, pointerIndex1);
                activePointerId1 = MotionEventCompat.getPointerId(event, 0);
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                pointerIndex2 = MotionEventCompat.getActionIndex(event);
                lastTouchX2 = MotionEventCompat.getX(event, pointerIndex2);
                lastTouchY2 = MotionEventCompat.getY(event, pointerIndex2);
                activePointerId2 = MotionEventCompat.getPointerId(event, pointerIndex2);
                theta1 = getAngle(lastTouchX1, lastTouchY1, lastTouchX2, lastTouchY2);
                break;
            case MotionEvent.ACTION_MOVE:
                pointerIndex1 = MotionEventCompat.findPointerIndex(event, activePointerId1);
                x1 = MotionEventCompat.getX(event, pointerIndex1);
                y1 = MotionEventCompat.getY(event, pointerIndex1);

                if (event.getPointerCount() == 1) {
                    final float dx = x1 - lastTouchX1;
                    final float dy = y1 - lastTouchY1;
                    setLeft(Math.round(getLeft() + dx));
                    setTop(Math.round(getTop() + dy));
                } else if (event.getPointerCount() >= 2) {
                    pointerIndex2 = MotionEventCompat.findPointerIndex(event, activePointerId2);
                    x2 = MotionEventCompat.getX(event, pointerIndex2);
                    y2 = MotionEventCompat.getY(event, pointerIndex2);
                    lastTouchX2 = x2;
                    lastTouchY2 = y2;
                    theta2 = getAngle(x1, y1, x2, y2);
                    setRotation(getRotation() + (theta2 - theta1));
                }

                invalidate();
                lastTouchX1 = x1;
                lastTouchY1 = y1;

                break;
            case MotionEvent.ACTION_UP:
                activePointerId1 = INVALID_POINTER_ID;
                break;
            case MotionEvent.ACTION_POINTER_UP:
                activePointerId2 = INVALID_POINTER_ID;
                break;
            case MotionEvent.ACTION_CANCEL:
                activePointerId1 = INVALID_POINTER_ID;
                activePointerId2 = INVALID_POINTER_ID;
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
