package com.example.justindang.storywell.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Stories implements Parcelable {

    // member data
    private String name;
    private String date;    // format: MM.DD.YY
    private String SHARED_PREF_KEY;
    private ArrayList<Page> pagesList;

    // constructors
    public Stories() {
        this.pagesList = new ArrayList<>();

        // format the current date
        SimpleDateFormat formatter = new SimpleDateFormat ("MM.dd.yy");
        Date currentTime = new Date();
        this.date = formatter.format(currentTime);
    }
    public Stories(Stories stories) {
        this.pagesList = new ArrayList<>(stories.getPagesList());
        this.name = stories.getName();
        this.date = stories.getDate();
        this.SHARED_PREF_KEY = stories.getSharedPrefKey();
    }

    public Stories(String name) {
        this.name = name;
        this.pagesList = new ArrayList<>();
        // format the current date
        SimpleDateFormat formatter = new SimpleDateFormat ("MM.dd.yy");
        Date currentTime = new Date();
        this.date = formatter.format(currentTime);
    }

    protected Stories(Parcel in) {
        name = in.readString();
        date = in.readString();
        SHARED_PREF_KEY = in.readString();
        pagesList = in.createTypedArrayList(Page.CREATOR);
    }

    public static final Creator<Stories> CREATOR = new Creator<Stories>() {
        @Override
        public Stories createFromParcel(Parcel in) {
            return new Stories(in);
        }

        @Override
        public Stories[] newArray(int size) {
            return new Stories[size];
        }
    };

    // key generator
    public void generateSharedPrefKey(int serialId) {
        String key = "stories_" + String.valueOf(serialId);
        this.setSharedPrefKey(key);
    }

    // getters and setters
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = new String(name);
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = new String(date);
    }

    public ArrayList<Page> getPagesList() {
        return pagesList;
    }

    public void setPagesList(ArrayList<Page> pagesList) {
        this.pagesList = new ArrayList<>(pagesList);
    }

    public int getNumPages() {
        return this.pagesList.size();
    }

    public String getSharedPrefKey() {
        return this.SHARED_PREF_KEY;
    }

    public void setSharedPrefKey(String key) {
        this.SHARED_PREF_KEY = new String(key);
    }

    // getters and setters for story values
    public void addPage(Page page) {
        this.pagesList.add(page);
    }

    public void removePage(Page page) {
        this.pagesList.remove(page);
    }

    public Page getPage(int index) {
        return pagesList.get(index);
    }

    public void setPage(int index, Page page) {
        this.pagesList.set(index, page);
    }

    public ArrayList<String> getImageUris(int index)  {
        return this.pagesList.get(index).getImageUris();
    }

    public void setImageUris(int index, ArrayList<String> imageUris) {
        this.pagesList.get(index).setImageUris(imageUris);
    }

    public String getTemplateName(int index) {
        return pagesList.get(index).getTemplateName();
    }

    public void setTemplateName(int index, String templateName) {
        this.pagesList.get(index).setTemplateName(templateName);
    }

    public void addImage(int index, String imagePath) {
        this.pagesList.get(index).addImage(imagePath);
    }

    public void removeImage(int index, String imagePath) {
        this.pagesList.get(index).removeImage(imagePath);
    }

    public ArrayList<String> getColors(int index) {
        return this.pagesList.get(index).getColors();
    }

    public void setColors(int index, ArrayList<String> colors) {
        this.pagesList.get(index).setColors(colors);
    }

    public void addColor(int index, String color) {
        this.pagesList.get(index).addColor(color);
    }

    public void removeColor(int index, Integer color) {
        this.pagesList.get(index).removeColor(color);
    }

    public String getTitle(int index) {
        return this.pagesList.get(index).getTitle();
    }

    public void setTitle(int index, String title) {
        this.pagesList.get(index).setTitle(title);
    }

    public String getText(int index) {
        return this.pagesList.get(index).getText();
    }

    public void setText(int index, String text) {
        this.pagesList.get(index).setText(text);
    }

    public String toString() {
        String storiesString = "name: " + this.name + "\ndate: " + this.date + "\nSHARED_PREF_KEY:"
                + this.SHARED_PREF_KEY + "\nnum pages: " + String.valueOf(this.getNumPages());

        for (Page page : this.pagesList) {
            storiesString.concat("\nPage\n");
            storiesString.concat(page.toString());
        }
        return storiesString;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(date);
        dest.writeString(SHARED_PREF_KEY);
        dest.writeTypedList(pagesList);
    }
}
