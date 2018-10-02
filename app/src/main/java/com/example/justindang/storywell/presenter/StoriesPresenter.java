package com.example.justindang.storywell.presenter;

import android.content.Context;
import android.view.View;

import com.example.justindang.storywell.StoryEditorActivity;
import com.example.justindang.storywell.model.Stories;
import com.example.justindang.storywell.model.Story;

import java.util.ArrayList;

public class StoriesPresenter {

    // member data
    private Stories stories;
    private Context context;
    private OnUpdateListener onUpdateListener;

    // constructor
    public StoriesPresenter(Context context) {
        this.context = context;
        this.stories = new Stories();
    }

    // update values
    public void updateName(String name) {
        stories.setName(name);
        onUpdateListener.updateView(context);
    }

    public void updateDate(String date) {
        stories.setDate(date);
        onUpdateListener.updateView(context);
    }

    public void generateSharedPrefKey(int numStories) {
        String key = "story_" + String.valueOf(numStories);
        stories.setSharedPrefKey(key);
        onUpdateListener.updateView(context);
    }

    public void updateStories(Stories stories) {
        this.stories = stories;
        onUpdateListener.updateView(context);
    }

    public void addStory(Story story) {
        this.stories.addStory(story);
        onUpdateListener.updateView(context);
    }

    public void removeStory(Story story) {
        this.stories.removeStory(story);
        onUpdateListener.updateView(context);
    }

    public Stories getStories() {
        return this.stories;
    }

    public Story getStory(int index) {
        return this.stories.getStory(index);
    }

    // update story values
    public void updateStory(int index, Story story) {
        this.stories.setStory(index, story);
        onUpdateListener.updateView(context);
    }

    public void updateImagePaths(int index, ArrayList<String> imagePaths) {
        stories.setPicturePaths(index, imagePaths);
        onUpdateListener.updateView(context);
    }

    public void updateTemplateName(int index, String templateName) {
        stories.setTemplateName(index, templateName);
        onUpdateListener.updateView(context);
    }

    public void updateColors(int index, ArrayList<Integer> colors) {
        stories.setColors(index, colors);
        onUpdateListener.updateView(context);
    }

    public void updateTitle(int index, String title) {
        stories.setTitle(index, title);
        onUpdateListener.updateView(context);
    }

    public void updateText(int index, String text) {
        stories.setText(index, text);
        onUpdateListener.updateView(context);
    }

    public interface OnUpdateListener {
        void updateView(Context context);
    }
}
