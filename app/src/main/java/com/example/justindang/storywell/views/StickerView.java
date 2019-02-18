package com.example.justindang.storywell.views;

import android.app.Activity;
import android.content.Context;
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

import butterknife.BindView;
import butterknife.ButterKnife;

public class StickerView extends LinearLayout {
    private static final int INVALID_POINTER_ID = -1000;

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
        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
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
