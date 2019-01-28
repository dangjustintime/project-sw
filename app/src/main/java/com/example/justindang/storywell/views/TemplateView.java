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
    List<String> mediaUriList;
    StoriesViewModel storiesViewModel;
    String uriString;

    // interface
    public interface MediaHandler {
        // Pair
        // key = viewId
        // value = media index
        void getGalleryPhoto(Pair<Integer, Integer> pair);
        String getUriString();
    }
    MediaHandler mediaHandler;

    // constructor
    public TemplateView(Context context) {
        super(context);
        Activity activity = (Activity) getContext();
        this.mediaHandler = (MediaHandler) activity;
        storiesViewModel = ViewModelProviders.of((FragmentActivity) getContext()).get(StoriesViewModel.class);

        // set id
        int newId = generateViewId();
        this.setId(newId);
    }

    // getter and setter
    public List<String> getMediaUriList() {
        return mediaUriList;
    }

    public void setMediaUriList(List<String> mediaUriList) {
        this.mediaUriList = mediaUriList;
    }

    public void setMediaImageView(Pair<Integer, Uri> pair) {
        // empty, must override
    };
}
