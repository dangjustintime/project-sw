package com.example.justindang.storywell.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;

import com.example.justindang.storywell.R;
import com.example.justindang.storywell.model.Stories;
import com.example.justindang.storywell.presenter.StoriesPresenter;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
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
            // convert Arraylists to Sets
            LinkedHashSet<String> filePathsSet = new LinkedHashSet<String>(storiesPresenter.getPage(i).getImageUris());
            LinkedHashSet<String> colorsSet = new LinkedHashSet<String>(storiesPresenter.getPage(i).getColors());

            sharedPreferencesEditor.putString(pageKey + "_template", storiesPresenter.getPage(i).getTemplateName());
            sharedPreferencesEditor.putString(pageKey + "_title", storiesPresenter.getPage(i).getTitle());
            sharedPreferencesEditor.putString(pageKey + "_text", storiesPresenter.getPage(i).getText());
            sharedPreferencesEditor.putStringSet(pageKey +"_colors", colorsSet);
            sharedPreferencesEditor.putStringSet(pageKey + "_image_uris", filePathsSet);
        }

        sharedPreferencesEditor.apply();


    }

}
