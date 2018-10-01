package com.example.justindang.storywell.model;

import android.arch.lifecycle.LiveData;
import android.graphics.Bitmap;
import android.icu.util.Calendar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Story {
    // member data
    private String name;
    // absolute directory path for photos
    private ArrayList<String> imageUris;
    private String templateName;
    private String SHARED_PREF_KEY;
    private ArrayList<Integer> colors;
    private String date;    // format: MM.DD.YY
    private String title;
    private String text;

    // constructors
    public Story() {
        this.imageUris = new ArrayList<>();
        this.colors = new ArrayList<>();
        // format the current date
        SimpleDateFormat formatter = new SimpleDateFormat ("MM.dd.yy");
        Date currentTime = new Date();
        this.date = formatter.format(currentTime);
    }
    public Story(String name, String templateName) {
        this.name = name;
        this.templateName = templateName;
        this.imageUris = new ArrayList<>();
        this.colors = new ArrayList<>();
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

    public ArrayList<String> getPicturePaths() {
        return imageUris;
    }

    public void setPicturePaths(ArrayList<String> picturePaths) {
        this.imageUris = picturePaths;
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
        imageUris.add(imagePath);
    }

    public void removeImage(String imagePath) {
        imageUris.remove(imagePath);
    }

    public ArrayList<Integer> getColors() {
        return colors;
    }

    public void setColors(ArrayList<Integer> colors) {
        this.colors = colors;
    }

    public void addColor(Integer color) {
        colors.add(color);
    }

    public void removeColor(Integer color) {
        colors.remove(color);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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