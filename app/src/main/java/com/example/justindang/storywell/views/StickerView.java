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
    private int pointerId;
    private float scaleFactor = 1.5f;
    ViewConfiguration viewConfiguration;
    int touchSlop;


    @BindView(R.id.linear_layout_sticker_view_container) LinearLayout containerLinearLayout;
    @BindView(R.id.image_view_x_icon_sticker_view) ImageView xIconImageView;

    int activePointerId = INVALID_POINTER_ID;

    ScaleGestureDetector scaleGestureDetector;

    public interface OnStickerListener {
        void sendStickerViewId(int id);
    }
    OnStickerListener onStickerListener;

    public StickerView(Context context) {
        super(context);
        inflate(context, R.layout.custom_view_sticker, this);
        ButterKnife.bind(this);
        this.setId(generateViewId());

        this.setGravity(Gravity.CENTER_HORIZONTAL);

        viewConfiguration = ViewConfiguration.get(context);
        touchSlop = viewConfiguration.getScaledTouchSlop();

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
        float newPointer1X, newPointer1Y, newPointer2X, newPointer2Y, dX1, dY1,
                dX2, dY2, distance1, distance2, centerX, centerY;

        centerX = getLeft() + (getWidth() / 2);
        centerY = getTop() + (getTop() / 2);
        setPivotX(centerX);
        setPivotY(centerY);

        angle1 = getAngle(pointer1X, pointer1Y, pointer2X, pointer2Y);


        switch (action) {
            case MotionEvent.ACTION_DOWN:
                pointer1X = event.getX();
                pointer1Y = event.getY();
                return true;
            case MotionEvent.ACTION_POINTER_DOWN:
                pointer2X = event.getX(1);
                pointer2Y = event.getY(1);
                return true;
            case MotionEvent.ACTION_MOVE:
                // drag view
                if (event.getPointerCount() == 1) {
                    newPointer1X = event.getX();
                    newPointer1Y = event.getY();
                    dX1 = newPointer1X - pointer1X;
                    dY1 = newPointer1Y - pointer1Y;

                    setLeft(Math.round(getLeft() + dX1));
                    setTop(Math.round(getTop() + dY1));

                    centerX = getLeft() + (getWidth() / 2);
                    centerY = getTop() + (getTop() / 2);

                // rotate view
                } else if (event.getPointerCount() == 2) {
                    newPointer1X = event.getX();
                    newPointer1Y = event.getY();
                    newPointer2X = event.getX(1);
                    newPointer2Y = event.getY(1);
                    angle2 = getAngle(newPointer1X, newPointer1Y, newPointer2X, newPointer2Y);
                }
                return true;
            case MotionEvent.ACTION_UP:
                pointer1X = event.getX();
                pointer1Y = event.getY();
                return true;
            case MotionEvent.ACTION_POINTER_UP:
                pointer2X = event.getX(1);
                pointer2Y = event.getY(1);
                float rotation = (angle2 - angle1) * -1;
                this.setRotation(rotation);
                return true;
            default:
                Log.i(TOUCH_EVENT_TAG, "action not found");
                break;
        }
        return true;
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

    public float getAngle(float x1, float y1, float x2, float y2) {
        double theta = Math.atan2(y2 - y1, x2 - x1);
        return (float) theta;
    }

}
