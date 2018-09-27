package com.example.justindang.storywell.model;

import android.arch.lifecycle.LiveData;
import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.Date;

public class Story {
    // member data
    private String name;
    // absolute directory path for photos
    private ArrayList<String> picturePaths;
    private String templateName;
    private String SHARED_PREF_KEY;
    private ArrayList<Integer> colors;
    private Date date;
    private String title;
    private String text;

    // constructors
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

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getSharedPrefKey() {
        return SHARED_PREF_KEY;
    }

    public void setSharedPrefKey(String key) {
        this.SHARED_PREF_KEY = key;
    }

    public void addImage(String imagePath) {
        picturePaths.add(imagePath);
    }

    public void removeImage(String imagePath) {
        picturePaths.remove(imagePath);
    }

    public ArrayList<Integer> getColors() {
        return colors;
    }

    public void setColors(ArrayList<Integer> colors) {
        this.colors = colors;
    }

    public Date getCreatedDate() {
        return date;
    }

    public void setCreatedDate(Date createdDate) {
        this.date = createdDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}