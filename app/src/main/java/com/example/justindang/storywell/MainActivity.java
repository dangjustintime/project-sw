package com.example.justindang.storywell;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.justindang.storywell.model.Story;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements CreateNewStoryDialogFragment.OnInputListener,
        TemplateGridRecyclerAdapter.OnTemplateListener {

    // variables
    private String newStoryName;
    private int numSavedStories;
    private Map<String, ?> sharedPrefMap;

    // tags
    private static final String EXTRA_NAME = "name";
    private static final String EXTRA_TEMPLATE = "template";
    private static final String DIALOG_NEW_STORY = "create a new story";

    // Fragment Transaction
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

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
    private ArrayList<Story> savedStoriesList;

    // fragments
    CreateNewStoryDialogFragment createNewStoryDialogFragment = new CreateNewStoryDialogFragment();
    ChooseATemplateFragment chooseATemplateFragment = new ChooseATemplateFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // get values from SharedPreferences
        // if there are not stories, hide recycler view
        SharedPreferences sharedPreferences = this.getSharedPreferences(getResources().getString(R.string.saved_stories), 0);
        numSavedStories = sharedPreferences.getInt(getResources().getString(R.string.saved_num_stories_keys), 0);
        savedStoriesList = new ArrayList<>();

        if (numSavedStories == 0) {
            hideSavedStoriesRecyclerView();
        } else {
            showSavedStoriesRecyclerView();

            // place stories into an array
            for (int i = 0; i < numSavedStories; i++) {
                // get values from shared preferences
                Story newStory = new Story();
                String newStoryKey = "story_" + String.valueOf(i);
                newStory.setName(sharedPreferences.getString(newStoryKey + "_name", "NOT FOUND"));
                newStory.setTemplateName(sharedPreferences.getString(newStoryKey + "_template", "NOT FOUND"));
                newStory.setTitle(sharedPreferences.getString(newStoryKey + "_title", "NOT FOUND"));
                newStory.setText(sharedPreferences.getString(newStoryKey + "_text", "NOT FOUND"));
                newStory.setDate(sharedPreferences.getString(newStoryKey + "_date", "NOT FOUND"));
                Set<String> newStoryFilePathsSet = sharedPreferences.getStringSet(newStoryKey + "_file_paths", new HashSet<String>());
                for (String filePath : newStoryFilePathsSet) {
                    newStory.addImage(filePath);
                }
                Set<String> newStoryColors = sharedPreferences.getStringSet(newStoryKey + "_colors", new HashSet<String>());
                if (newStoryColors.size() > 0) {
                    for (String color : newStoryColors) {
                        newStory.addColor(Integer.valueOf(color));
                    }
                }
                // push to savedStories list
                savedStoriesList.add(newStory);
            }

            // create recycler view
            savedStoriesRecyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2, GridLayoutManager.VERTICAL, false));
            savedStoriesGridRecyclerAdapter = new SavedStoriesGridRecyclerAdapter(MainActivity.this, savedStoriesList);
            savedStoriesRecyclerView.setAdapter(savedStoriesGridRecyclerAdapter);
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
        newStoryName = input;
        getSupportFragmentManager().beginTransaction().add(R.id.frame_layout_fragment_placeholder, chooseATemplateFragment).commit();
    }

    @Override
    public void sendTemplate(String template) {
        Intent intent = new Intent(MainActivity.this, StoryEditorActivity.class);
        intent.putExtra(EXTRA_NAME, newStoryName);
        intent.putExtra(EXTRA_TEMPLATE, template);
        startActivity(intent);
    }
}
