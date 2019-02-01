package com.example.justindang.storywell.views;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentActivity;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;

import com.example.justindang.storywell.activities.StoryEditorActivity;
import com.example.justindang.storywell.model.Page;
import com.example.justindang.storywell.view_model.StoriesViewModel;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class TemplateView extends ConstraintLayout {
    int pageIndex;

    // interface
    public interface TemplateHandler {
        void getGalleryPhoto(int viewId, int mediaIndex);
        void sendViewId(int id);
        void sendTitle(String title);
        void sendText(String text);
    }
    TemplateHandler templateHandler;

    // constructor
    public TemplateView(Context context) {
        super(context);
        Activity activity = (Activity) getContext();
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
        // empty, must override
    }


}
