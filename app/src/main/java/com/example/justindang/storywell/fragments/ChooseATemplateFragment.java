package com.example.justindang.storywell.fragments;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.justindang.storywell.R;
import com.example.justindang.storywell.activities.StoryEditorActivity;
import com.example.justindang.storywell.adapters.TemplateGridRecyclerAdapter;
import com.example.justindang.storywell.activities.StarterKitsActivity;
import com.example.justindang.storywell.model.Stories;
import com.example.justindang.storywell.view_model.StoriesViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ChooseATemplateFragment extends Fragment {

    // views
    @BindView(R.id.text_view_choose_a_template) TextView chooseATemplateTextView;
    @BindView(R.id.image_view_x_icon) ImageView xIconImageView;
    @BindView(R.id.image_view_shopping_cart_icon) ImageView shoppingCartIconImageView;

    // recycler view
    @BindView(R.id.recycler_view_templates) RecyclerView templatesRecyclerView;
    private TemplateGridRecyclerAdapter templateGridRecyclerAdapter;
    private List<String> templateNames;

    // view model
    StoriesViewModel storiesViewModel;

    // fragment manager
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    // constructor
    public ChooseATemplateFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_choose_atemplate, container, false);
        ButterKnife.bind(this, view);

        // initialize model view
        storiesViewModel = ViewModelProviders.of(this.getActivity()).get(StoriesViewModel.class);

        // create recycler view
        templatesRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3, GridLayoutManager.VERTICAL, false));
        templateNames = new ArrayList<>();
        for (int i = 1; i < 7; i++) {
            templateNames.add("free template " + String.valueOf(i));
        }
        templateGridRecyclerAdapter = new TemplateGridRecyclerAdapter(getContext(), templateNames);
        templatesRecyclerView.setAdapter(templateGridRecyclerAdapter);

        // click listeners
        xIconImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.remove(ChooseATemplateFragment.this);
                fragmentTransaction.commit();
            }
        });
        shoppingCartIconImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), StarterKitsActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
