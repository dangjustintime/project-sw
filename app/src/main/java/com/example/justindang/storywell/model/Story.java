package com.example.justindang.storywell.model;

import android.arch.lifecycle.LiveData;

import java.util.ArrayList;

public class Story {
    // member data
    private String name;
    // absolute directory path for photos
    private ArrayList<String> picturePaths;
    private String templateName;
    private String key;

    // constructor
    public Story(String name, String templateName) {
        this.name = name;
        this.templateName = templateName;
    }


    // getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getPicturePaths() {
        return picturePaths;
    }

    public void setPicturePaths(ArrayList<String> picturePaths) {
        this.picturePaths = picturePaths;
    }

    public String getTemplate() {
        return templateName;
    }

    public void setTemplate(String templateName) {
        this.templateName = templateName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    private void addImage(String imagePath) {
        picturePaths.add(imagePath);
    }

    private void removeImage(String imagePath) {
        picturePaths.remove(imagePath);
    }
}