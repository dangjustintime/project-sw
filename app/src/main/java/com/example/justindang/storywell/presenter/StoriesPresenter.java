package com.example.justindang.storywell.presenter;

import android.view.View;

import com.example.justindang.storywell.model.Stories;
import com.example.justindang.storywell.model.Story;

import java.util.ArrayList;

public class StoriesPresenter {
    private Stories stories;
    private View view;

    // constructor
    public StoriesPresenter(View view) {
        this.view = view;
        this.stories = new Stories();
    }

    // update values
    public void updateName(String name) {
        stories.setName(name);
        view.updateView(stories);
    }

    public void updateDate(String date) {
        stories.setDate(date);
        view.updateView(stories);
    }

    public void generateSharedPrefKey(int numStories) {
        String key = "story_" + String.valueOf(numStories);
        stories.setSharedPrefKey(key);
        view.updateView(stories);
    }

    public void updateStoires(Stories stories) {
        this.stories = stories;
        view.updateView(stories);
    }

    public void addStory(Story story) {
        this.stories.addStory(story);
        view.updateView(stories);
    }

    public void removeStory(Story story) {
        this.stories.removeStory(story);
        view.updateView(stories);
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
        view.updateView(stories);
    }

    public void updateImagePaths(int index, ArrayList<String> imagePaths) {
        stories.setPicturePaths(index, imagePaths);
        view.updateView(stories);
    }

    public void updateTemplateName(int index, String templateName) {
        stories.setTemplateName(index, templateName);
        view.updateView(stories);
    }

    public void updateColors(int index, ArrayList<Integer> colors) {
        stories.setColors(index, colors);
        view.updateView(stories);
    }

    public void updateTitle(int index, String title) {
        stories.setTitle(index, title);
        view.updateView(stories);
    }

    public void updateText(int index, String text) {
        stories.setText(index, text);
        view.updateView(stories);
    }

    public interface View {
        void updateView(Stories stories);
    }
}
