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


    public interface View {
        void updateView(Stories stories);
    }
}
