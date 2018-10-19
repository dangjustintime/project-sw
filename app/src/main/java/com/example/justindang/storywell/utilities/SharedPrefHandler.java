package com.example.justindang.storywell.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;

import com.example.justindang.storywell.R;
import com.example.justindang.storywell.model.Page;
import com.example.justindang.storywell.model.Stories;
import com.example.justindang.storywell.presenter.StoriesPresenter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class SharedPrefHandler {

    // empty constructor
    private SharedPrefHandler() {}

    public static void putStories(Context context, StoriesPresenter storiesPresenter, boolean isNewStories) {

        // initialize shared preferences
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getResources().getString(R.string.saved_stories), 0);
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();

        if (isNewStories) {
            int numStories = sharedPreferences.getInt(context.getResources().getString(R.string.saved_num_stories_keys), 0);
            int serialID = sharedPreferences.getInt(context.getResources().getString(R.string.serial_id), 0);

            // generate key
            storiesPresenter.generateSharedPrefKey(serialID);

            // increment number of stories
            sharedPreferencesEditor.putInt(context.getResources().getString(R.string.saved_num_stories_keys), ++numStories);
            sharedPreferencesEditor.putInt(context.getResources().getString(R.string.serial_id), ++serialID);
        }

        String key = storiesPresenter.getSharedPrefKey();

        // put values in shared preferences
        sharedPreferencesEditor.putString(key + "_name", storiesPresenter.getPages().getName());
        sharedPreferencesEditor.putString(key + "_date", storiesPresenter.getPages().getDate());
        sharedPreferencesEditor.putInt(key + "_num_pages", storiesPresenter.getNumPages());

        for (int i = 0; i < storiesPresenter.getNumPages(); i++) {
            String pageKey = key + "_" + String.valueOf(i);

            // image uris
            ArrayList<String> imageUris = storiesPresenter.getPage(i).getImageUris();
            for (int j = 0; j < 9; j++) {
                if (j < imageUris.size()) {
                    sharedPreferencesEditor.putString(pageKey + "_image_uri_" + String.valueOf(j), imageUris.get(j));
                }
            }

            // colors
            ArrayList<String> colors = storiesPresenter.getPage(i).getColors();
            for (int j = 0; j < 2; j++) {
                if (j < colors.size()) {
                    sharedPreferencesEditor.putString(pageKey + "_color_" + String.valueOf(j), imageUris.get(j));
                }
            }

            sharedPreferencesEditor.putString(pageKey + "_template", storiesPresenter.getPage(i).getTemplateName());
            sharedPreferencesEditor.putString(pageKey + "_title", storiesPresenter.getPage(i).getTitle());
            sharedPreferencesEditor.putString(pageKey + "_text", storiesPresenter.getPage(i).getText());
        }

        sharedPreferencesEditor.apply();
    }

    public static Stories getStories(Context context, int serialID) {
        // get values from SharedPreferences
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getResources().getString(R.string.saved_stories), 0);

        Stories newStories = new Stories();
        String storiesKey = "stories_" + String.valueOf(serialID);
        newStories.setSharedPrefKey(storiesKey);

        newStories.setName(sharedPreferences.getString(storiesKey + "_name", "NOT FOUND"));
        newStories.setDate(sharedPreferences.getString(storiesKey + "_date", "NOT FOUND"));
        int numPages = sharedPreferences.getInt(storiesKey + "_num_pages", -1);

        if (numPages != -1) {
            for (int i = 0; i < numPages; i++) {
                String pageKey = storiesKey + "_" + String.valueOf(i);
                newStories.addPage(new Page());

                // get image uris
                for (int j = 0; j < 9; j++) {
                    newStories.addImage(i, sharedPreferences.getString(pageKey + "_image_uri_" + String.valueOf(j), "NOT FOUND"));
                }

                // get colors
                for (int j = 0; j < 2; j++) {
                    newStories.addColor(i, sharedPreferences.getString(pageKey + "_color_" + String.valueOf(j), "NOT FOUND"));
                }

                // get template, title, and text
                newStories.setTemplateName(i, sharedPreferences.getString(pageKey + "_template", "NOT FOUND"));
                newStories.setTitle(i, sharedPreferences.getString(pageKey + "_title", "NOT FOUND"));
                newStories.setText(i, sharedPreferences.getString(pageKey + "_text", "NOT FOUND"));
            }
        }

        return newStories;
    }

}
