package com.example.justindang.storywell;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.justindang.storywell.model.Story;

import java.util.List;

public class SavedStoriesGridRecyclerAdapter {
    // member data
    Context context;
    List<Story> savedStoriesList;

    // constructor
    public SavedStoriesGridRecyclerAdapter(Context context, List<Story> savedStoriesList) {
        this.context = context;
        this.savedStoriesList = savedStoriesList;
    }

    public interface OnStoryListener {
        public void sendStory();
    }
    OnStoryListener onStoryListener;


}
