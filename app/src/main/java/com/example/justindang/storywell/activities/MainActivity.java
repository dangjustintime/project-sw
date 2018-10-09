package com.example.justindang.storywell.activities;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.justindang.storywell.fragments.ChangeStoryNameDialogFragment;
import com.example.justindang.storywell.fragments.CreateNewStoryDialogFragment;
import com.example.justindang.storywell.R;
import com.example.justindang.storywell.adapters.SavedStoriesGridRecyclerAdapter;
import com.example.justindang.storywell.model.Stories;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements CreateNewStoryDialogFragment.OnInputListener,
        SavedStoriesGridRecyclerAdapter.OnItemListener,
        ChangeStoryNameDialogFragment.OnChangeNameListener {

    // tags
    private static final String EXTRA_NAME = "name";
    private static final String DIALOG_NEW_STORY = "create a new story";
    private static final String DIALOG_CHANGE_NAME = "change name";

    // request code
    private static final int REQUEST_READ_PERMISSION = 202;

    // Fragments
    FragmentManager fragmentManager;
    CreateNewStoryDialogFragment createNewStoryDialogFragment = new CreateNewStoryDialogFragment();
    ChangeStoryNameDialogFragment changeStoryNameDialogFragment = new ChangeStoryNameDialogFragment();

    // views
    @BindView(R.id.toolbar_main_activity) Toolbar toolbar;
    @BindView(R.id.constraint_layout_anywhere) ConstraintLayout constraintLayoutAnywhere;
    @BindView(R.id.frame_layout_fragment_placeholder) FrameLayout frameLayoutFragmentPlaceholder;
    @BindView(R.id.text_view_shared_preferences) TextView sharedPreferencesTextView;
    @BindView(R.id.constraint_layout_bottom_bar) ConstraintLayout bottomBarConstraintLayout;
    @BindView(R.id.image_view_main_activity_pencil_icon) ImageView pencilIconImageView;
    @BindView(R.id.image_view_main_activity_plus_icon) ImageView plusIconImageView;
    @BindView(R.id.image_view_main_activity_trash_icon) ImageView trashIconImageView;
    @BindView(R.id.image_view_main_activity_shopping_cart) ImageView shoppingCartImageView;

    // recycler view
    @BindView(R.id.recycler_view_saved_stories) RecyclerView savedStoriesRecyclerView;
    SavedStoriesGridRecyclerAdapter savedStoriesGridRecyclerAdapter;
    private ArrayList<Stories> savedStoriesList;
    private int changeNamePosition;

    @Override
    public void getNewName(int position) {
        fragmentManager = getSupportFragmentManager();
        changeStoryNameDialogFragment.show(fragmentManager, DIALOG_CHANGE_NAME);
        changeNamePosition = position;

    }

    @Override
    public void sendNewName(String newName) {
        savedStoriesGridRecyclerAdapter.changeName(changeNamePosition, newName);
    }

    // interface
    public interface OnRecyclerListener {
        void openChangeNameFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // request read permissions
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_PERMISSION);

        // get values from SharedPreferences
        // if there are not stories, hide recycler view
        SharedPreferences sharedPreferences = this.getSharedPreferences(getResources().getString(R.string.saved_stories), 0);
        int numSavedStories = sharedPreferences.getInt(getResources().getString(R.string.saved_num_stories_keys), 0);
        // place shared pref values in map
        Map<String, ?> sharedPrefMap = sharedPreferences.getAll();
        savedStoriesList = new ArrayList<>();

        if (numSavedStories == 0) {
            hideSavedStoriesRecyclerView();
        } else {
            showSavedStoriesRecyclerView();

            for (int i = 0; i < numSavedStories; i++) {
                Stories newStories = new Stories();
                String storiesKey = "stories_" + String.valueOf(i);
                newStories.setSharedPrefKey(storiesKey);

                newStories.setName(sharedPreferences.getString(storiesKey + "_name", "NOT FOUND"));
                newStories.setDate(sharedPreferences.getString(storiesKey + "_date", "NOT FOUND"));
                int numPages = sharedPreferences.getInt(storiesKey + "_num_pages", 0);

                for (int j = 0; j < numPages; j++) {
                    String pageKey = storiesKey + "_" + String.valueOf(j);
                    newStories.addPage(new Stories.Page());

                    // get image uris
                    Set<String> imageUrisSet = sharedPreferences.getStringSet(pageKey + "_image_uris", new HashSet<String>());
                    ArrayList<String> imageUrisStrings = new ArrayList<>();
                    imageUrisStrings.addAll(imageUrisSet);
                    newStories.setImageUris(j, imageUrisStrings);

                    // get colors
                    Set<String> colorsSet = sharedPreferences.getStringSet(pageKey + "_colors", new HashSet<String>());
                    ArrayList<Integer> colorsInts = new ArrayList<>();
                    for (String color: colorsSet) {
                        colorsInts.add(Integer.getInteger(color));
                    }
                    newStories.setColors(j, colorsInts);

                    // get template, title, and text
                    newStories.setTemplateName(j, sharedPreferences.getString(pageKey + "_template", "NOT FOUND"));
                    newStories.setTitle(j, sharedPreferences.getString(pageKey + "_title", "NOT FOUND"));
                    newStories.setText(j, sharedPreferences.getString(pageKey + "_text", "NOT FOUND"));
                }
                savedStoriesList.add(newStories);
            }

            // check if read permissions are granted
            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                // create recycler view
                savedStoriesRecyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2, GridLayoutManager.VERTICAL, false));
                savedStoriesGridRecyclerAdapter = new SavedStoriesGridRecyclerAdapter(MainActivity.this, savedStoriesList);
                savedStoriesRecyclerView.setAdapter(savedStoriesGridRecyclerAdapter);
            }
        }

        // clickListeners
        constraintLayoutAnywhere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager = getSupportFragmentManager();
                createNewStoryDialogFragment.show(fragmentManager, DIALOG_NEW_STORY);
            }
        });
        plusIconImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager = getSupportFragmentManager();
                createNewStoryDialogFragment.show(fragmentManager, DIALOG_NEW_STORY);
                hideSavedStoriesRecyclerView();
            }
        });
        trashIconImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getBaseContext().getSharedPreferences(getResources().getString(R.string.saved_stories), 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                Toast.makeText(getApplicationContext(), "Shared Preferences cleared", Toast.LENGTH_SHORT).show();
            }
        });
        shoppingCartImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, StarterKitsActivity.class);
                startActivity(intent);
            }
        });

        setSupportActionBar(toolbar);
    }

    public void showSavedStoriesRecyclerView() {
        constraintLayoutAnywhere.setVisibility(View.INVISIBLE);
        savedStoriesRecyclerView.setVisibility(View.VISIBLE);
        bottomBarConstraintLayout.setVisibility(View.VISIBLE);
        shoppingCartImageView.setVisibility(View.VISIBLE);
    }

    public void hideSavedStoriesRecyclerView() {
        constraintLayoutAnywhere.setVisibility(View.VISIBLE);
        savedStoriesRecyclerView.setVisibility(View.INVISIBLE);
        bottomBarConstraintLayout.setVisibility(View.INVISIBLE);
        shoppingCartImageView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void sendInput(String input) {
        Intent intent = new Intent(MainActivity.this, StoryEditorActivity.class);
        intent.putExtra(EXTRA_NAME, input);
        startActivity(intent);
    }
}
