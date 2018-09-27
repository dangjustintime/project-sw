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
    private int color;
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

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
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

    @Override
    public String toString() {
        String string = "name: " + name
                + " template: " + templateName
                + " color: " + color
                + " file names: ";
        for (String picturePath : picturePaths) {
            string += picturePath + ", ";
        }

        // remove trailing comma and whitespace and return value
        return string.substring(0, string.length() - 3);
    }
}