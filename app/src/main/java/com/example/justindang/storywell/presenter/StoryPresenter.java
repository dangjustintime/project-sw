package com.example.justindang.storywell.presenter;

import android.net.Uri;
import android.view.View;

import com.example.justindang.storywell.model.Story;

public class StoryPresenter {
    Story story;
    View view;

    // constructor
    public StoryPresenter(View view) {
        this.story = new Story();
        this.view = view;
    }

    private void updateStory(Story story) {
        this.story = story;
        view.updateView(story);
    }

    public interface View {
        void updateView(Story story);
        String getFilePath(Uri imageUri);
    }
}
