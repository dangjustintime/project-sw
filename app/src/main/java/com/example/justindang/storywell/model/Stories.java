package com.example.justindang.storywell.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Stories {

    // member data
    private String name;
    private String date;    // format: MM.DD.YY
    private ArrayList<Story> storiesList;
    private String SHARED_PREF_KEY;

    // constructor
    public Stories() {
        this.storiesList = new ArrayList<>();
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<Story> getStoriesList() {
        return storiesList;
    }

    public void setStoriesList(ArrayList<Story> storiesList) {
        storiesList = storiesList;
    }

    public String getSHARED_PREF_KEY() {
        return SHARED_PREF_KEY;
    }

    public void setSHARED_PREF_KEY(String SHARED_PREF_KEY) {
        this.SHARED_PREF_KEY = SHARED_PREF_KEY;
    }


}
