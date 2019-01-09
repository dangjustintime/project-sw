package com.example.justindang.storywell.view_model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.justindang.storywell.model.Stories;

public class StoriesViewModel extends ViewModel {
    private MutableLiveData<Stories> mutableLiveData = new MutableLiveData<>();

    public void setStories(Stories stories) {
        mutableLiveData.postValue(stories);
    }

    public LiveData<Stories> getStories() {
        return mutableLiveData;
    }

    private void loadStories() {
        // fetch data asynchronously
    }
}