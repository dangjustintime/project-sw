package com.example.justindang.storywell.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Page implements Parcelable {
    // member data
    // absolute directory path for photos
    private String templateName;
    private String title;
    private String text;
    private ArrayList<String> imageUris;
    private ArrayList<String> colors;

    // constructors
    public Page() {
        this.imageUris = new ArrayList<>();
        this.colors = new ArrayList<>();
    }
    protected Page(Parcel in) {
        templateName = in.readString();
        title = in.readString();
        text = in.readString();
        imageUris = in.createStringArrayList();
        colors = in.createStringArrayList();
    }

    public static final Creator<Page> CREATOR = new Creator<Page>() {
        @Override
        public Page createFromParcel(Parcel in) {
            return new Page(in);
        }

        @Override
        public Page[] newArray(int size) {
            return new Page[size];
        }
    };

    public void addImage(String imagePath) {
        imageUris.add(imagePath);
    }

    public void removeImage(String imagePath) {
        imageUris.remove(imagePath);
    }

    public void addColor(String color) {
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

    public ArrayList<String> getColors() {
        return colors;
    }

    public void setColors(ArrayList<String> colors) {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(templateName);
        dest.writeString(title);
        dest.writeString(text);
        dest.writeStringList(imageUris);
        dest.writeStringList(colors);
    }
}
