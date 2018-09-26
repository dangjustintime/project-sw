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
    private int color;

    // constructor
    public Story() {
        this.picturePaths = new ArrayList<>();
    }
    public Story(String name, String templateName) {
        this.name = name;
        this.templateName = templateName;
        this.picturePaths = new ArrayList<>();
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

    public void addImage(String imagePath) {
        picturePaths.add(imagePath);
    }

    public void removeImage(String imagePath) {
        picturePaths.remove(imagePath);
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}