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
    private View view;

    public interface View {
        void updateView();
    }

    // constructor
    public StoryPresenter(View view) {
        this.story = new Story();
        this.view = view;
    }
    public StoryPresenter(View view, Story story) {
        this.story = story;
        this.view = view;
    }

    // update values
    public void updateStory(Story story) {
        this.story = story;
        view.updateView();
    }

    public void updateName(String name) {
        story.setName(name);
        view.updateView();
    }

    public void updateImagePaths(ArrayList<String> imagePaths) {
        story.setPicturePaths(imagePaths);
        view.updateView();
    }

    public void updateTemplateName(String templateName) {
        story.setTemplateName(templateName);
        view.updateView();
    }

    public void generateSharedPrefKey(int numStories) {
        String key = "story_" + String.valueOf(numStories);
        story.setSharedPrefKey(key);
        view.updateView();
    }

    public void updateColors(ArrayList<Integer> colors) {
        story.setColors(colors);
        view.updateView();
    }

    public void updateTitle(String title) {
        story.setTitle(title);
        view.updateView();
    }

    public void updateText(String text) {
        story.setText(text);
        view.updateView();
    }

    public void updateDate(String date) {
        story.setDate(date);
        view.updateView();
    }

    public Story getStory() {
        return this.story;
    }

}
