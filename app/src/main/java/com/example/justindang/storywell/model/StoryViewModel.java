package com.example.justindang.storywell.model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;

import java.io.File;
import java.util.ArrayList;
import java.util.function.Predicate;

public class StoryViewModel extends ViewModel {
    private MutableLiveData<Story> data;

    public MutableLiveData<Story> getStory() {
        if (data == null) {
            data = new MutableLiveData<Story>();
        }
        return data;
    }
    public void setData(Story story) {
        data.setValue(story);
    }
}