package com.example.justindang.storywell.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.provider.MediaStore;

public class Story {
    // member data
    private String name;
    private String template;
    private MediaStore.Images medias;

    // constructor
    public Story(String name, String template) {
        this.name = name;
        this.template = template;
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
    public MediaStore.Images getMedias() {
        return medias;
    }
    public void setMedias(MediaStore.Images medias) {
        this.medias = medias;
    }
}