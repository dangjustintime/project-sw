package com.example.justindang.storywell.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Page {
    // member data
    // absolute directory path for photos
    private ArrayList<String> imageUris;
    private String templateName;
    private ArrayList<Integer> colors;
    private String title;
    private String text;

    public void addImage(String imagePath) {
        imageUris.add(imagePath);
    }

    public void removeImage(String imagePath) {
        imageUris.remove(imagePath);
    }

    public void addColor(Integer color) {
        colors.add(color);
    }

    public void removeColor(Integer color) {
        colors.remove(color);
    }

    // getters and setters
    public ArrayList<String> getImageUris() {
        return imageUris;
    }

    public void setImageUris(ArrayList<String> imageUris) {
        this.imageUris = imageUris;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public ArrayList<Integer> getColors() {
        return colors;
    }

    public void setColors(ArrayList<Integer> colors) {
        this.colors = colors;
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
