package com.example.justindang.storywell.presenter;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;

import com.example.justindang.storywell.model.Story;

import java.util.ArrayList;
import java.util.Date;

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
    public void updateImagePaths(ArrayList<String> imagePaths) {
        story.setPicturePaths(imagePaths);
        view.updateView(story);
    }
    public void updateTemplateName(String templateName) {
        story.setTemplateName(templateName);
        view.updateView(story);
    }
    public void generateSharedPrefKey(int numStories) {
        String key = "story_" + String.valueOf(numStories);
        story.setSharedPrefKey(key);
        view.updateView(story);
    }
    public void updateColors(ArrayList<Integer> colors) {
        story.setColors(colors);
        view.updateView(story);
    }

    public void updateTitle(String title) {
        story.setTitle(title);
        view.updateView(story);
    }

    public void updateText(String text) {
        story.setText(text);
        view.updateView(story);
    }

    public void updateDate(String date) {
        story.setDate(date);
        view.updateView(story);
    }

    public Story getStory() {
        return this.story;
    }

    public interface View {
        void updateView(Story story);
    }
}
