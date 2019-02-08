package com.example.justindang.storywell.views;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentActivity;
import android.text.Layout;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.justindang.storywell.activities.StoryEditorActivity;
import com.example.justindang.storywell.model.Page;
import com.example.justindang.storywell.view_model.StoriesViewModel;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class TemplateView extends FrameLayout {
    int pageIndex;
    FrameLayout stickerLayerFrameLayout;
    ConstraintLayout templateLayerConstraintLayout;

    // interface
    public interface TemplateHandler {
        void getGalleryPhoto(int viewId, int mediaIndex);
        void sendViewId(int id);
        void sendTitle(String title);
        void sendText(String text);
    }
    TemplateHandler templateHandler;

    // constructor
    @SuppressLint("ClickableViewAccessibility")
    public TemplateView(Context context) {
        super(context);
        Activity activity = (Activity) context;
        this.pageIndex = pageIndex;
        this.templateHandler = (TemplateHandler) activity;
        // set id
        this.setId(generateViewId());
        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                templateHandler.sendViewId(getId());
                return true;
            }
        });
        templateLayerConstraintLayout = new ConstraintLayout(context);
        templateLayerConstraintLayout.setId(generateViewId());
        stickerLayerFrameLayout = new FrameLayout(context);
        stickerLayerFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(1080, 1920));
        stickerLayerFrameLayout.setId(generateViewId());
        this.addView(templateLayerConstraintLayout);
        this.addView(stickerLayerFrameLayout);
    }

    public int getStickerLayerViewId() {
        return this.stickerLayerFrameLayout.getId();
    }

    public int getTemplateLayerViewId() {
        return this.templateLayerConstraintLayout.getId();
    }

    public void setTitle(String title) {
        // empty, must override
    }

    public void setText(String text) {
        // empty, must override
    }

    public void setMediaImageView(int mediaIndex, Uri uri) {
        // empty, must override
    }

    public void setColor(int colorIndex, int color) {
        // empty, must override
    }

    public void hideUi() {

    }
}
