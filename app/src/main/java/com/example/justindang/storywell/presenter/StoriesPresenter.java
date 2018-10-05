package com.example.justindang.storywell.presenter;

import com.example.justindang.storywell.model.Stories;

import java.util.ArrayList;

public class StoriesPresenter {

    // member data
    private Stories stories;
    private View view;

    // constructor
    public StoriesPresenter(View view) {
        this.view = view;
        this.stories = new Stories();
    }
    public StoriesPresenter(View view, Stories stories) {
        this.view = view;
        this.stories = stories;
    }

    // getters
    public Stories getPages() {
        return this.stories;
    }

    public Stories.Page getPage(int index) {
        return this.stories.getPage(index);
    }

    public int getNumPages() {
        return this.stories.getNumPages();
    }

    // update values
    public void updateName(String name) {
        stories.setName(name);
        view.updateView();
    }

    public void updateDate(String date) {
        stories.setDate(date);
        view.updateView();
    }

    public void generateSharedPrefKey(int numStories) {
        String key = "stories_" + String.valueOf(numStories);
        stories.setSharedPrefKey(key);
        view.updateView();
    }

    public void updateStories(Stories stories) {
        this.stories = stories;
        view.updateView();
    }

    public void addPage(Stories.Page page) {
        this.stories.addPage(page);
        view.updateView();
    }

    public void removePage(Stories.Page page) {
        this.stories.removePage(page);
        view.updateView();
    }

    // update story values
    public void updatePage(int index, Stories.Page page) {
        this.stories.setPage(index, page);
        view.updateView();
    }

    public void updateImageUris(int index, ArrayList<String> imagePaths) {
        stories.setImageUris(index, imagePaths);
        view.updateView();
    }

    public void updateTemplateName(int index, String templateName) {
        stories.setTemplateName(index, templateName);
        view.updateView();
    }

    public void updateColors(int index, ArrayList<Integer> colors) {
        stories.setColors(index, colors);
        view.updateView();
    }

    public void updateTitle(int index, String title) {
        stories.setTitle(index, title);
        view.updateView();
    }

    public void updateText(int index, String text) {
        stories.setText(index, text);
        view.updateView();
    }

    public interface View {
        void updateView();
    }
}
