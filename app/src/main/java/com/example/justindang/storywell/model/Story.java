package com.example.justindang.storywell.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;

import java.io.File;
import java.util.ArrayList;
import java.util.function.Predicate;

public class Story {
    // member data
    private String name;
    // absolute directory path for photos
    private ArrayList<String> picturePaths;
    private Fragment template;

    // getters and setters
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String[] getPicturePaths() {
        return picturePaths;
    }
    public void setPicturePaths(String[] picturePaths) {
        this.picturePaths = picturePaths;
    }
    public Fragment getTemplate() {
        return template;
    }
    public void setTemplate(Fragment template) {
        this.template = template;
    }

    private void addImage(String imagePath) {
      picturePaths.add(imagePath);
    }
    private void removeImage(String imagePath) {
      picturePaths.remove(imagePath);
    }
}