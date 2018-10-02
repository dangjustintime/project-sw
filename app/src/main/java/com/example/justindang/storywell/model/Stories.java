package com.example.justindang.storywell.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Stories {

    // member data
    private String name;
    private String date;    // format: MM.DD.YY
    private ArrayList<Story> storiesList;
    private String SHARED_PREF_KEY;

    // constructor
    public Stories() {
        this.storiesList = new ArrayList<>();
        // format the current date
        SimpleDateFormat formatter = new SimpleDateFormat ("MM.dd.yy");
        Date currentTime = new Date();
        this.date = formatter.format(currentTime);
    }

    // getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<Story> getStoriesList() {
        return storiesList;
    }

    public void setStoriesList(ArrayList<Story> storiesList) {
        this.storiesList = storiesList;
    }

    public String getSharedPrefKey() {
        return SHARED_PREF_KEY;
    }

    public void setSharedPrefKey(String key) {
        this.SHARED_PREF_KEY = key;
    }

    public void addStory(Story story) {
        this.storiesList.add(story);
    }

    public void removeStory(Story story) {
        this.storiesList.remove(story);
    }


    // getters and setters for story values
    public Story getStory(int index) {
        return storiesList.get(index);
    }

    public void setStory(int index, Story story) {
        this.storiesList.set(index, story);
    }

    public ArrayList<String> getPicturePaths(int index) {
        return storiesList.get(index).getPicturePaths();
    }

    public void setPicturePaths(int index, ArrayList<String> picturePaths) {
        this.storiesList.get(index).setPicturePaths(picturePaths);
    }

    public String getTemplateName(int index) {
        return storiesList.get(index).getTemplateName();
    }

    public void setTemplateName(int index, String templateName) {
        this.storiesList.get(index).setTemplateName(templateName);
    }

    public void addImage(int index, String imagePath) {
        this.storiesList.get(index).addImage(imagePath);
    }

    public void removeImage(int index, String imagePath) {
        this.storiesList.get(index).removeImage(imagePath);
    }

    public ArrayList<Integer> getColors(int index) {
        return this.storiesList.get(index).getColors();
    }

    public void setColors(int index, ArrayList<Integer> colors) {
        this.storiesList.get(index).setColors(colors);
    }

    public void addColor(int index, Integer color) {
        this.storiesList.get(index).addColor(color);
    }

    public void removeColor(int index, Integer color) {
        this.storiesList.get(index).removeColor(color);
    }

    public String getTitle(int index) {
        return this.storiesList.get(index).getTitle();
    }

    public void setTitle(int index, String title) {
        this.storiesList.get(index).setTitle(title);
    }

    public String getText(int index) {
        return this.storiesList.get(index).getText();
    }

    public void setText(int index, String text) {
        this.storiesList.get(index).setText(text);
    }
}
