package com.example.StoryWell.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Story implements Parcelable {
    // member data
    private String name;
    private String template;
    private String photos[];

    // constructor
    public Story(String name, String template, String photos[]) {
        this.name = name;
        this.template = template;
        this.photos = photos.clone();
    }

    // getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String[] getPhotos() {
        return photos;
    }

    public void setPhotos(String[] photos) {
        this.photos = photos;
    }

}