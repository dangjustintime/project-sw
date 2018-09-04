package com.example.justindang.storywell.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Story {
    // member data
    private String name;
    private String template;

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

}