package com.example.justindang.storywell.view_model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.justindang.storywell.model.Stories;

public class StoriesModelView extends ViewModel {
    private final MutableLiveData<Stories> mutableLiveData = new MutableLiveData<Stories>();

    public void setStories(Stories stories) {
        mutableLiveData.setValue(stories);
    }

    public LiveData<Stories> getStories() {
        return mutableLiveData;
    }
}
