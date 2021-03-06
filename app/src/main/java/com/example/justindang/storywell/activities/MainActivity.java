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
import com.example.justindang.storywell.fragments.ChooseATemplateFragment;
import com.example.justindang.storywell.fragments.CreateNewStoryDialogFragment;
import com.example.justindang.storywell.R;
import com.example.justindang.storywell.adapters.SavedStoriesGridRecyclerAdapter;
import com.example.justindang.storywell.model.Page;
import com.example.justindang.storywell.model.Stories;
import com.example.justindang.storywell.utilities.SharedPrefHandler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements CreateNewStoryDialogFragment.OnInputListener,
        SavedStoriesGridRecyclerAdapter.OnItemListener,
        ChangeStoryNameDialogFragment.OnChangeNameListener {

    // static data
    private static final String DIALOG_NEW_STORY = "create a new story";
    private static final String DIALOG_CHANGE_NAME = "change name";
    private static final String EXTRA_IS_NEW_STORIES = "new stories";
    private static final String EXTRA_SAVED_STORIES = "saved stories";
    private static final int REQUEST_READ_PERMISSION = 202;

    // Fragments
    FragmentManager fragmentManager;
    CreateNewStoryDialogFragment createNewStoryDialogFragment = new CreateNewStoryDialogFragment();
    ChangeStoryNameDialogFragment changeStoryNameDialogFragment = new ChangeStoryNameDialogFragment();

    // views
    @BindView(R.id.toolbar_main_activity) Toolbar toolbar;
    @BindView(R.id.constraint_layout_anywhere) ConstraintLayout constraintLayoutAnywhere;
    @BindView(R.id.constraint_layout_bottom_bar) ConstraintLayout bottomBarConstraintLayout;
    @BindView(R.id.image_view_main_activity_pencil_icon) ImageView pencilIconImageView;
    @BindView(R.id.image_view_main_activity_plus_icon) ImageView plusIconImageView;
    @BindView(R.id.image_view_main_activity_trash_icon) ImageView trashIconImageView;
    @BindView(R.id.image_view_main_activity_shopping_cart) ImageView shoppingCartImageView;

    // shared pref
    @BindView(R.id.text_view_shared_preferences) TextView sharedPrefTextView;

    // recycler view
    @BindView(R.id.recycler_view_saved_stories) RecyclerView savedStoriesRecyclerView;
    SavedStoriesGridRecyclerAdapter savedStoriesGridRecyclerAdapter;
    private ArrayList<Stories> savedStoriesList;
    private int changeNamePosition;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // request read permissions
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_PERMISSION);

        loadRecyclerView();

        // get shared pref
        // sharedPrefTextView.setText(SharedPrefHandler.getSharedPrefString(getApplicationContext()));

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
                for (int i = 0; i < savedStoriesList.size(); i++) {
                    SavedStoriesGridRecyclerAdapter.SavedStoryViewHolder savedStoryViewHolder = (SavedStoriesGridRecyclerAdapter.SavedStoryViewHolder) savedStoriesRecyclerView.findViewHolderForAdapterPosition(i);
                    savedStoryViewHolder.toggleDeleteImageView();
                }
            }
        });
        shoppingCartImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, StarterKitsActivity.class);
                startActivity(intent);
            }
        });
        pencilIconImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pencilIconImageView.setColorFilter(0);
                for (int i = 0; i < savedStoriesList.size(); i++) {
                    SavedStoriesGridRecyclerAdapter.SavedStoryViewHolder savedStoryViewHolder = (SavedStoriesGridRecyclerAdapter.SavedStoryViewHolder) savedStoriesRecyclerView.findViewHolderForAdapterPosition(i);
                    savedStoryViewHolder.toggleEditNameImageView();
                }
            }
        });
        trashIconImageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                SharedPreferences sharedPreferences = getBaseContext().getSharedPreferences(getResources().getString(R.string.saved_stories), 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                Toast.makeText(getApplicationContext(), "Shared Preferences cleared", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        setSupportActionBar(toolbar);
    }
    @Override
    protected void onResume() {
        super.onResume();
        loadRecyclerView();
    }

    // OnItemListener interface
    @Override
    public void getNewName(int position) {
        fragmentManager = getSupportFragmentManager();
        changeStoryNameDialogFragment.show(fragmentManager, DIALOG_CHANGE_NAME);
        changeNamePosition = position;
    }

    // OnChangeNameListener interface
    @Override
    public void sendNewName(String newName) {
        savedStoriesGridRecyclerAdapter.changeName(changeNamePosition, newName);
    }

    // OnInputListener interface
    @Override
    public void sendInput(String input) {
        Intent intent = new Intent(MainActivity.this, StoryEditorActivity.class);
        Stories newStories = new Stories(input);
        intent.putExtra(EXTRA_SAVED_STORIES, newStories);
        intent.putExtra(EXTRA_IS_NEW_STORIES, true);
        startActivity(intent);
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

    public void loadRecyclerView() {
        // get values from SharedPreferences
        // if there are not stories, hide recycler view
        SharedPreferences sharedPreferences = this.getSharedPreferences(getResources().getString(R.string.saved_stories), 0);
        int serialID = sharedPreferences.getInt(getResources().getString(R.string.serial_id), 0);
        int numSavedStories = sharedPreferences.getInt(getResources().getString(R.string.saved_num_stories_keys), 0);

        savedStoriesList = new ArrayList<>();

        if (numSavedStories == 0) {
            hideSavedStoriesRecyclerView();
        } else {
            showSavedStoriesRecyclerView();
            for (int i = 0; i < serialID; i++) {
                Stories newStories = SharedPrefHandler.getStories(MainActivity.this, i);
                if (!newStories.getName().equals("NOT FOUND")) {
                    savedStoriesList.add(newStories);
                }
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
    }
}
