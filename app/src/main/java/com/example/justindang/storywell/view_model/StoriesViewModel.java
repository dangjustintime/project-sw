package com.example.justindang.storywell.view_model;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.justindang.storywell.model.Stories;

public class StoriesViewModel extends ViewModel {
    // live data
    private MutableLiveData<Stories> storiesMutableLiveData;

    // getter
    public MutableLiveData<Stories> getStoriesMutableLiveData() {
        if (storiesMutableLiveData == null) {
            storiesMutableLiveData = new MutableLiveData<>();
        }
        return storiesMutableLiveData;
    }
}
