package com.example.justindang.storywell.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;

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
        onUpdateListener.updateView(story);
    }

    public void updateName(String name) {
        story.setName(name);
        onUpdateListener.updateView(story);
    }

    public void updateImagePaths(ArrayList<String> imagePaths) {
        story.setPicturePaths(imagePaths);
        onUpdateListener.updateView(story);
    }

    public void updateTemplateName(String templateName) {
        story.setTemplateName(templateName);
        onUpdateListener.updateView(story);
    }

    public void generateSharedPrefKey(int numStories) {
        String key = "story_" + String.valueOf(numStories);
        story.setSharedPrefKey(key);
        onUpdateListener.updateView(story);
    }

    public void updateColors(ArrayList<Integer> colors) {
        story.setColors(colors);
        onUpdateListener.updateView(story);
    }

    public void updateTitle(String title) {
        story.setTitle(title);
        onUpdateListener.updateView(story);
    }

    public void updateText(String text) {
        story.setText(text);
        onUpdateListener.updateView(story);
    }

    public void updateDate(String date) {
        story.setDate(date);
        onUpdateListener.updateView(story);
    }

    public Story getStory() {
        return this.story;
    }

    public interface OnUpdateListener {
        void updateView(Story story);
    }
}
