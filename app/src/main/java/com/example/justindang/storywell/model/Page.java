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
        this.templateName = "";
        this.title = "";
        this.text = "";
        this.imageUris = new ArrayList<>();
        this.colors = new ArrayList<>();
    }

    public Page(Page page) {
        this.templateName = page.getTemplateName();
        this.title = page.getTitle();
        this.text = page.getText();
        this.imageUris = new ArrayList<>(page.getImageUris());
        this.colors = new ArrayList<>(page.getColors());
    }

    public Page(String templateName) {
        this.templateName = templateName;
        this.title = "";
        this.text = "";
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
        this.imageUris.add(imagePath);
    }

    public void removeImage(String imagePath) {
        this.imageUris.remove(imagePath);
    }

    public void addColor(String color) {
        this.colors.add(color);
    }

    public void removeColor(Integer color) {
        this.colors.remove(color);
    }

    // getters and setters
    public ArrayList<String> getImageUris() {
        return this.imageUris;
    }

    public void setImageUris(ArrayList<String> imageUris) {
        this.imageUris = new ArrayList<>(imageUris);
    }

    public String getTemplateName() {
        return this.templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public ArrayList<String> getColors() {
        return this.colors;
    }

    public void setColors(ArrayList<String> colors) {
        this.colors = new ArrayList<>(colors);
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String toString() {
        return "template: " + this.templateName + "\ntitle: " + this.title + "\ntext: " + this.text
                + "\nURIs: " + this.imageUris.toString() + "\ncolors: " + this.colors.toString();
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
