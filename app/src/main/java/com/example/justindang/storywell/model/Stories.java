package com.example.justindang.storywell.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Stories {

    // member data
    private String name;
    private String date;    // format: MM.DD.YY
    private ArrayList<Page> pagesList;
    private String SHARED_PREF_KEY;

    public static class Page {
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

    // constructor
    public Stories() {
        this.pagesList = new ArrayList<>();
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

    public ArrayList<Page> getPagesList() {
        return pagesList;
    }

    public void setPagesList(ArrayList<Page> pagesList) {
        this.pagesList = pagesList;
    }

    public int getNumPages() {
        return this.pagesList.size();
    }

    public String getSharedPrefKey() {
        return SHARED_PREF_KEY;
    }

    public void setSharedPrefKey(String key) {
        this.SHARED_PREF_KEY = key;
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

    public ArrayList<String> getImageUris(int index) {
        return pagesList.get(index).getImageUris();
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

    public ArrayList<Integer> getColors(int index) {
        return this.pagesList.get(index).getColors();
    }

    public void setColors(int index, ArrayList<Integer> colors) {
        this.pagesList.get(index).setColors(colors);
    }

    public void addColor(int index, Integer color) {
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
}
