package com.example.justindang.storywell.presenter;

import android.net.Uri;
import android.view.View;

import com.example.justindang.storywell.model.Story;

import java.util.ArrayList;

public class StoryPresenter {
    private Story story;
    private View view;

    // constructor
    public StoryPresenter(View view) {
        this.story = new Story();
        this.view = view;
    }

    // update values
    public void updateStory(Story story) {
        this.story = story;
        view.updateView(story);
    }
    public void updateName(String name) {
        story.setName(name);
        view.updateView(story);
    }
    public void updateStoryPath(Uri imageUri) {
        ArrayList<String> imagePaths = view.getFilePaths(imageUri);
        story.setPicturePaths(imagePaths);
        view.updateView(story);
    }
    public void updateTemplateName(String templateName) {
        story.setTemplate(templateName);
        view.updateView(story);
    }
    public void updateSharedPrefKey(String key) {
        story.setSharedPrefKey(key);
        view.updateView(story);
    }
    public void updateStoryColor(int color) {
        story.setColor(view.getColor(color));
    }

    public Story getStory() {
        return this.story;
    }

    public interface View {
        void updateView(Story story);
        ArrayList<String> getFilePaths(Uri imageUri);
        int getColor(int color);
    }
}
