package com.example.justindang.storywell.views;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentActivity;
import android.util.Pair;

import com.example.justindang.storywell.activities.StoryEditorActivity;
import com.example.justindang.storywell.model.Page;
import com.example.justindang.storywell.view_model.StoriesViewModel;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class TemplateView extends ConstraintLayout {

    // interface
    public interface MediaHandler {
        // Pair
        // key = viewId
        // value = media index
        void getGalleryPhoto(int viewId, int mediaIndex);
    }
    MediaHandler mediaHandler;

    // constructor
    public TemplateView(Context context) {
        super(context);
        Activity activity = (Activity) getContext();
        this.mediaHandler = (MediaHandler) activity;

        // set id
        this.setId(generateViewId());
    }

    public void setMediaImageView(int mediaIndex, Uri uri) {
        // empty, must override
    }
}
