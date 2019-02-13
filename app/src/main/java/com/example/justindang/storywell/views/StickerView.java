package com.example.justindang.storywell.views;

import android.app.Activity;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.justindang.storywell.R;
import com.example.justindang.storywell.activities.StoryEditorActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StickerView extends LinearLayout {
    @BindView(R.id.linear_layout_sticker_view_container) LinearLayout containerLinearLayout;
    @BindView(R.id.image_view_x_icon_sticker_view) ImageView xIconImageView;

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
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onStickerListener.sendStickerViewId(getId());
            }
        });
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
