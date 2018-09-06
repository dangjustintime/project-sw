package com.example.justindang.storywell;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;


public class StoryEditorActivity extends AppCompatActivity {
    // views
    @BindView(R.id.image_view_aa_icon) ImageView aaIconImageView;
    @BindView(R.id.image_view_square_circle_icon) ImageView squareCircleIconImageView;
    @BindView(R.id.image_view_plus_icon) ImageView plusIconImageView;
    @BindView(R.id.image_view_three_circle_icon) ImageView threeCircleIconImageView;
    @BindView(R.id.image_view_angle_brackets_icon) ImageView angleBracketsIconImageView;
    @BindView(R.id.frame_layout_fragment_placeholder_story_editor) FrameLayout fragmentPlaceholderFrameLayout;

    // fragment
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Template1Fragment template1Fragment = new Template1Fragment();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_editor);
        ButterKnife.bind(this);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frame_layout_fragment_placeholder_story_editor, template1Fragment);
        fragmentTransaction.commit();
    }
}
