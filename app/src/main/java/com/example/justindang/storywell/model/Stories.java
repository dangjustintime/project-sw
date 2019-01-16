package com.example.justindang.storywell.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Stories implements Parcelable {

    // member data
    private String name;
    private String date;    // format: MM.DD.YY
    private String SHARED_PREF_KEY;
    private int currentIndex;
    private ArrayList<Page> pagesList;

    // constructors
    public Stories() {
        this.name = "";
        this.SHARED_PREF_KEY = "";
        this.pagesList = new ArrayList<>();
        this.currentIndex = 0;
        // format the current date
        SimpleDateFormat formatter = new SimpleDateFormat ("MM.dd.yy");
        Date currentTime = new Date();
        this.date = formatter.format(currentTime);
    }

    public Stories(Stories stories) {
        if (stories.pagesList == null) {
            this.pagesList = new ArrayList<>();
        } else {
            this.pagesList = new ArrayList<>(stories.getPagesList());
        }
        this.name = stories.getName();
        this.date = stories.getDate();
        this.SHARED_PREF_KEY = stories.getSharedPrefKey();
        this.currentIndex = stories.getCurrentIndex();
    }

    public Stories(String name) {
        this.name = name;
        this.SHARED_PREF_KEY = "";
        this.pagesList = new ArrayList<>();
        this.currentIndex = 0;
        // format the current date
        SimpleDateFormat formatter = new SimpleDateFormat ("MM.dd.yy");
        Date currentTime = new Date();
        this.date = formatter.format(currentTime);
    }

    protected Stories(Parcel in) {
        name = in.readString();
        date = in.readString();
        currentIndex = in.readInt();
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
        this.name = name;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Page> getPagesList() {
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
        this.SHARED_PREF_KEY = key;
    }

    public int getCurrentIndex() {
        return this.currentIndex;
    }

    public void setCurrentIndex(int index) {
        this.currentIndex = index;
    }

    // Pages List navigation
    public void nextPage() {
        currentIndex = (currentIndex < this.pagesList.size()) ? currentIndex++ : currentIndex;
    }

    public void previousPage() {
        currentIndex = (currentIndex > 0) ? currentIndex-- : currentIndex;
    }

    // getters and setters for story values
    public void addPage(Page page) {
        this.pagesList.add(page);
    }

    public void removePage(Page page) {
        this.pagesList.remove(page);
        currentIndex = (currentIndex == this.pagesList.size()) ? currentIndex-- : currentIndex;
    }

    public Page getPage() {
        return pagesList.get(currentIndex);
    }

    public void setPage(int index, Page page) {
        this.pagesList.set(index, page);
    }

    public ArrayList<String> getImageUris()  {
        return this.pagesList.get(currentIndex).getImageUris();
    }

    public void setImageUris(ArrayList<String> imageUris) {
        this.pagesList.get(currentIndex).setImageUris(imageUris);
    }

    public String getTemplateName() {
        return pagesList.get(currentIndex).getTemplateName();
    }

    public void setTemplateName(String templateName) {
        this.pagesList.get(currentIndex).setTemplateName(templateName);
    }

    public void addImage(String imagePath) {
        this.pagesList.get(currentIndex).addImage(imagePath);
    }

    public void removeImage(String imagePath) {
        this.pagesList.get(currentIndex).removeImage(imagePath);
    }

    public ArrayList<String> getColors() {
        return this.pagesList.get(currentIndex).getColors();
    }

    public void setColors(ArrayList<String> colors) {
        this.pagesList.get(currentIndex).setColors(colors);
    }

    public void addColor(String color) {
        this.pagesList.get(currentIndex).addColor(color);
    }

    public void removeColor(Integer color) {
        this.pagesList.get(currentIndex).removeColor(color);
    }

    public String getTitle() {
        return this.pagesList.get(currentIndex).getTitle();
    }

    public void setTitle(String title) {
        this.pagesList.get(currentIndex).setTitle(title);
    }

    public String getText() {
        return this.pagesList.get(currentIndex).getText();
    }

    public void setText(String text) {
        this.pagesList.get(currentIndex).setText(text);
    }

    public String toString() {
        String storiesString = "name: " + this.name + "\ndate: " + this.date + "\nSHARED_PREF_KEY:"
                + this.SHARED_PREF_KEY + "\ncurrent index: " + String.valueOf(this.currentIndex)
                + "\nnum pages: " + String.valueOf(this.getNumPages());
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
        dest.writeInt(currentIndex);
        dest.writeString(SHARED_PREF_KEY);
        dest.writeTypedList(pagesList);
    }
}
