package com.example.justindang.storywell.views;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.justindang.storywell.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StickerView extends LinearLayout {
    int color;
    @BindView(R.id.linear_layout_sticker_view_container) LinearLayout containerLinearLayout;
    @BindView(R.id.image_view_x_icon_sticker_view) ImageView xIconImageView;

    public StickerView(Context context) {
        super(context);
        inflate(context, R.layout.custom_view_sticker, this);
        ButterKnife.bind(this);
        xIconImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                hideView();
            }
        });
    }

    public void hideUi() {
        xIconImageView.setVisibility(INVISIBLE);
        this.setBackground(null);
    }

    public void hideView() {
        this.setVisibility(INVISIBLE);
    }

    public void setColor(int color) {
        this.color = color;
    }
}
