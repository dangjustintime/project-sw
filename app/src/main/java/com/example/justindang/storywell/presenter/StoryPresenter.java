package com.example.justindang.storywell.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;

import com.example.justindang.storywell.StoryEditorActivity;
import com.example.justindang.storywell.model.Story;

import java.util.ArrayList;
import java.util.Date;

public class StoryPresenter {

    // member data
    private Story story;
    private Context context;
    OnUpdateListener onUpdateListener;

    // constructor
    public StoryPresenter(Context context) {
        this.story = new Story();
        this.context = context;
    }

    // update values
    public void updateStory(Story story) {
        this.story = story;
        onUpdateListener.updateView(context);
    }

    public void updateName(String name) {
        story.setName(name);
        onUpdateListener.updateView(context);
    }

    public void updateImagePaths(ArrayList<String> imagePaths) {
        story.setPicturePaths(imagePaths);
        onUpdateListener.updateView(context);
    }

    public void updateTemplateName(String templateName) {
        story.setTemplateName(templateName);
        onUpdateListener.updateView(context);
    }

    public void generateSharedPrefKey(int numStories) {
        String key = "story_" + String.valueOf(numStories);
        story.setSharedPrefKey(key);
        onUpdateListener.updateView(context);
    }

    public void updateColors(ArrayList<Integer> colors) {
        story.setColors(colors);
        onUpdateListener.updateView(context);
    }

    public void updateTitle(String title) {
        story.setTitle(title);
        onUpdateListener.updateView(context);
    }

    public void updateText(String text) {
        story.setText(text);
        onUpdateListener.updateView(context);
    }

    public void updateDate(String date) {
        story.setDate(date);
        onUpdateListener.updateView(context);
    }

    public Story getStory() {
        return this.story;
    }

    public interface OnUpdateListener {
        void updateView(Context context);
    }
}
