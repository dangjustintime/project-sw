package com.example.justindang.storywell;


import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

    // interface listener
    public interface OnCancelListener {
        public void cancelStory();
    }
    OnCancelListener onCancelListener;

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

        return view;
    }
}
