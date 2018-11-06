package com.example.justindang.storywell.fragments;


import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.Toast;

import com.example.justindang.storywell.R;
import com.example.justindang.storywell.activities.StoryEditorActivity;
import com.example.justindang.storywell.adapters.PagesListRecyclerAdapter;
import com.example.justindang.storywell.model.Page;
import com.example.justindang.storywell.model.Stories;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectOrderFragment extends Fragment implements StoryEditorActivity.UpdateOrderListener {

    // static data
    private static final String BUNDLE_STORY = "current story";

    // recycler view
    @BindView(R.id.recycler_view_pages) RecyclerView pagesRecyclerView;
    private PagesListRecyclerAdapter pagesListRecyclerAdapter;
    private Stories stories;

    @Override
    public boolean allPagesSelected() {
        if (pagesListRecyclerAdapter.getNewOrderPageList().size() < pagesListRecyclerAdapter.getStories().getNumPages()) {
            Toast.makeText(getContext(), "Must select all pages", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public ArrayList<Page> getNewPageOrder() {
        return pagesListRecyclerAdapter.getNewOrderPageList();
    }

    // interface
    public interface NewPageOrderListener {
        ArrayList<Page> getNewPageList();
    }
    NewPageOrderListener newPageOrderListener;

    public SelectOrderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_select_order, container, false);
        ButterKnife.bind(this, view);

        // get stories data from bundle
        stories = getArguments().getParcelable(BUNDLE_STORY);

        // create recycler view
        pagesRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3, GridLayout.VERTICAL, false));
        pagesListRecyclerAdapter = new PagesListRecyclerAdapter(getContext(), stories);
        pagesRecyclerView.setAdapter(pagesListRecyclerAdapter);

        return view;
    }
}
