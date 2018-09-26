package com.example.justindang.storywell.presenter;

import android.net.Uri;
import android.view.View;

import com.example.justindang.storywell.model.Story;

public class StoryPresenter {
    private Story story;
    private View view;

    // constructor
    public StoryPresenter(View view) {
        this.story = new Story();
        this.view = view;
    }

    public void updateStory(Story story) {
        this.story = story;
        view.updateView(story);
    }

    public void updateStoryPath(Uri imageUri) {
        String imagePath = view.getFilePath(imageUri);
        story.addImage(imagePath);
    }

    public Story getStory() {
        return this.story;
    }


    public interface View {
        void updateView(Story story);
        String getFilePath(Uri imageUri);
    }
}
