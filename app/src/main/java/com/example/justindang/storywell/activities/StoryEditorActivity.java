package com.example.justindang.storywell.activities;


import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.justindang.storywell.fragments.ChooseATemplateFragment;
import com.example.justindang.storywell.R;
import com.example.justindang.storywell.fragments.SaveStoryDialogFragment;
import com.example.justindang.storywell.adapters.TemplateGridRecyclerAdapter;
import com.example.justindang.storywell.model.Stories;
import com.example.justindang.storywell.presenter.StoriesPresenter;
import com.example.justindang.storywell.utilities.ImageHandler;
import com.example.justindang.storywell.utilities.TemplateManager;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

import butterknife.BindView;
import butterknife.ButterKnife;


public class StoryEditorActivity extends AppCompatActivity
        implements SaveStoryDialogFragment.OnSaveListener,
        StoriesPresenter.View,
        TemplateGridRecyclerAdapter.OnTemplateListener {

    // intent keys
    private static final String EXTRA_NAME = "name";
    private static final String EXTRA_TEMPLATE = "template";

    // model of story
    private ArrayList<String> filePaths;
    private StoriesPresenter storiesPresenter;

    // TAG
    private static final String DIALOG_SAVE_STORY = "save story";

    public interface OnSaveImageListener {
        void hideUI();
        ArrayList<String> sendFilePaths();
        ArrayList<Integer> sendColors();
        String sendTitle();
        String sendText();
    }
    OnSaveImageListener onSaveImageListener;

    // template manager
    TemplateManager templateManager = new TemplateManager();

    // views
    @BindView(R.id.image_view_story_editor_aa_icon) ImageView aaIconImageView;
    @BindView(R.id.image_view_story_editor_square_circle_icon) ImageView squareCircleIconImageView;
    @BindView(R.id.image_view_story_editor_plus_icon) ImageView plusIconImageView;
    @BindView(R.id.image_view_story_editor_three_circle_icon) ImageView threeCircleIconImageView;
    @BindView(R.id.image_view_story_editor_angle_brackets_icon) ImageView angleBracketsIconImageView;
    @BindView(R.id.frame_layout_fragment_placeholder_story_editor) FrameLayout fragmentPlaceholderFrameLayout;
    @BindView(R.id.image_view_story_editor_back_button) ImageView backButtonImageView;
    @BindView(R.id.image_view_story_editor_download_icon) ImageView downloadButtonImageView;
    @BindView(R.id.frame_layout_fragment_placeholder_save_story) FrameLayout fragmentPlaceholderSaveStoryFrameLayout;
    @BindView(R.id.constraint_layout_icon_container) ConstraintLayout iconContainerConstraintLayout;

    // fragments
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Fragment templatePlaceholderFragment;
    SaveStoryDialogFragment saveStoryDialogFragment = new SaveStoryDialogFragment();

    // save photo to storage
    public void saveImage() {
        Toast.makeText(getBaseContext(), "saving image to device....", Toast.LENGTH_SHORT).show();

        ImageHandler.writeFile(StoryEditorActivity.this, fragmentPlaceholderFrameLayout, storiesPresenter.getPages().getName());

        // get values from template fragments
        storiesPresenter.updateImageUris(0, onSaveImageListener.sendFilePaths());
        storiesPresenter.updateColors(0, onSaveImageListener.sendColors());
        storiesPresenter.updateTitle(0, onSaveImageListener.sendTitle());
        storiesPresenter.updateText(0, onSaveImageListener.sendText());

        // put values in shared preferences
        SharedPreferences sharedPreferences = this.getSharedPreferences(getResources().getString(R.string.saved_stories), 0);
        int numStories = sharedPreferences.getInt(getResources().getString(R.string.saved_num_stories_keys), 0);

        // generate key
        storiesPresenter.generateSharedPrefKey(numStories);
        String key = storiesPresenter.getPages().getSharedPrefKey();

        // convert Arraylists to HashSets
        HashSet<String> filePathsHashSet = new HashSet<String>(storiesPresenter.getPage(0).getImageUris());
        ArrayList<String> colorsArrayList = new ArrayList<String>();
        ArrayList<Integer> integerColors = storiesPresenter.getPage(0).getColors();
        for (Integer integer : integerColors) {
            colorsArrayList.add(integer.toString());
        }
        HashSet<String> colorsHashSet = new HashSet<String>(colorsArrayList);

        // put values in shared preferences
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.putString(key + "_name", storiesPresenter.getPages().getName());
        sharedPreferencesEditor.putString(key + "_date", storiesPresenter.getPages().getDate());
        sharedPreferencesEditor.putString(key + "_0_template", storiesPresenter.getPage(0).getTemplateName());
        sharedPreferencesEditor.putString(key + "_0_title", storiesPresenter.getPage(0).getTitle());
        sharedPreferencesEditor.putString(key + "_0_text", storiesPresenter.getPage(0).getText());
        sharedPreferencesEditor.putStringSet(key + "_0_colors", colorsHashSet);
        sharedPreferencesEditor.putStringSet(key + "_0_file_paths", filePathsHashSet);

        // increment number of stories
        sharedPreferencesEditor.putInt(getResources().getString(R.string.saved_num_stories_keys), ++numStories);
        sharedPreferencesEditor.apply();
    }

    // creates intent that launches instagram app to post story
    void createInstagramIntent() {
        // create URI from media
        File media = new File(Environment.getExternalStorageDirectory() + "/Pictures/storywell/" + storiesPresenter.getPages().getName() + ".jpg");
        Uri uri = FileProvider.getUriForFile(StoryEditorActivity.this, "com.example.justindang.storywell.fileprovider", media);

        // create new intent to open instagram
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setType("image/*");

        // verify that intent will resolve to an activity
        if (intent.resolveActivity(this.getPackageManager()) != null) {
            startActivity(Intent.createChooser(intent, "Share Story"));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_editor);
        ButterKnife.bind(this);

        // initialize presenter
        storiesPresenter = new StoriesPresenter(this);

        // initialize list
        filePaths = new ArrayList<String>();

        // get data from intent
        storiesPresenter.addPage(new Stories.Page());
        storiesPresenter.updateName(getIntent().getStringExtra(EXTRA_NAME));

        // add Choose a template fragment
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frame_layout_fragment_placeholder_story_editor, new ChooseATemplateFragment());
        fragmentTransaction.commit();

        // clicklisteners
        downloadButtonImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveStoryDialogFragment.show(fragmentManager, DIALOG_SAVE_STORY);
            }
        });
        backButtonImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.popBackStack();
                if (fragmentManager.getBackStackEntryCount() == 0) {
                    finish();
                }
            }
        });

        plusIconImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                ChooseATemplateFragment chooseATemplateFragment = new ChooseATemplateFragment();
                fragmentTransaction.add(R.id.frame_layout_fragment_placeholder_story_editor, chooseATemplateFragment);
                fragmentTransaction.commit();
            }
        });
    }

    // SaveStoryDialog interface
    @Override
    public void saveStory() {
        Toast.makeText(StoryEditorActivity.this, "HERE", Toast.LENGTH_SHORT).show();
        onSaveImageListener.hideUI();
        saveImage();
        finish();
    }

    @Override
    public void saveStories() {
        Toast.makeText(getBaseContext(), "save stories", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void shareStoryToInstagram() {
        Toast.makeText(getBaseContext(), "sharing to instagram", Toast.LENGTH_SHORT).show();
        onSaveImageListener.hideUI();
        saveImage();
        createInstagramIntent();
    }

    // StoryPresenter interface
    @Override
    public void updateView() { }

    // OnTemplateListener
    @Override
    public void sendTemplate(String template) {
        storiesPresenter.updateTemplateName(0, template);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        templatePlaceholderFragment = templateManager.getTemplate(template);
        onSaveImageListener = (OnSaveImageListener) templatePlaceholderFragment;
        fragmentTransaction.add(R.id.frame_layout_fragment_placeholder_story_editor, templateManager.getTemplate(template));
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}

