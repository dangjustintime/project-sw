package com.example.justindang.storywell.fragments;


import android.arch.lifecycle.ViewModelProviders;
import android.graphics.drawable.BitmapDrawable;
import android.icu.text.UnicodeSetSpanner;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.Toast;

import com.example.justindang.storywell.R;
import com.example.justindang.storywell.activities.StoryEditorActivity;
import com.example.justindang.storywell.adapters.PageOrderRecyclerAdapter;
import com.example.justindang.storywell.model.Page;
import com.example.justindang.storywell.model.Stories;
import com.example.justindang.storywell.view_model.StoriesViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectOrderFragment extends Fragment {

    // recycler view
    @BindView(R.id.recycler_view_pages) RecyclerView pagesRecyclerView;
    private PageOrderRecyclerAdapter pagesListRecyclerAdapter;
    private StoriesViewModel storiesViewModel;

    public SelectOrderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_select_order, container, false);
        ButterKnife.bind(this, view);

        storiesViewModel = ViewModelProviders.of(getActivity()).get(StoriesViewModel.class);

        // create recycler view
        pagesRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3, GridLayout.VERTICAL, false));
        pagesListRecyclerAdapter = new PageOrderRecyclerAdapter(getContext(), storiesViewModel.getStories().getValue());
        pagesRecyclerView.setAdapter(pagesListRecyclerAdapter);
        return view;
    }

    public boolean getNewOrderPageList() {
        if (pagesListRecyclerAdapter.allPagesSelected()) {
            Stories updatedStores = pagesListRecyclerAdapter.getStories();
            storiesViewModel.setStories(updatedStores);
            return true;
        }
        return false;
    }
}
