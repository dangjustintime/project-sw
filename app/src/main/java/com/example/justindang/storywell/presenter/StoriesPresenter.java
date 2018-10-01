package com.example.justindang.storywell.presenter;

import android.content.Context;

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
        onUpdateListener.updateView(stories);
    }

    public void updateDate(String date) {
        stories.setDate(date);
        onUpdateListener.updateView(stories);
    }

    public void generateSharedPrefKey(int numStories) {
        String key = "story_" + String.valueOf(numStories);
        stories.setSharedPrefKey(key);
        onUpdateListener.updateView(stories);
    }

    public void updateStories(Stories stories) {
        this.stories = stories;
        onUpdateListener.updateView(stories);
    }

    public void updateStory(int index, Story story) {
        this.stories.setStory(index, story);
        onUpdateListener.updateView(stories);
    }

    public interface OnUpdateListener {
        void updateView(Stories stories);
    }
}
