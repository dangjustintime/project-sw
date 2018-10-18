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
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.justindang.storywell.fragments.ChooseATemplateFragment;
import com.example.justindang.storywell.R;
import com.example.justindang.storywell.fragments.SaveStoryDialogFragment;
import com.example.justindang.storywell.adapters.TemplateGridRecyclerAdapter;
import com.example.justindang.storywell.model.Page;
import com.example.justindang.storywell.model.Stories;
import com.example.justindang.storywell.presenter.StoriesPresenter;
import com.example.justindang.storywell.utilities.ImageHandler;
import com.example.justindang.storywell.utilities.SharedPrefHandler;
import com.example.justindang.storywell.utilities.TemplateManager;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.CopyOnWriteArraySet;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StoryEditorActivity extends AppCompatActivity
        implements SaveStoryDialogFragment.OnSaveListener,
        StoriesPresenter.View,
        TemplateGridRecyclerAdapter.OnTemplateListener {

    // intent keys
    private static final String EXTRA_IS_NEW_STORIES = "new stories";
    private static final String EXTRA_SAVED_STORIES = "saved stories";

    private int currentPageIndex;
    boolean isNewStories;

    // model of story
    private StoriesPresenter storiesPresenter;

    // TAG
    private static final String DIALOG_SAVE_STORY = "save story";
    private static final String BUNDLE_CURRENT_PAGE = "current page";

    public interface OnSaveImageListener {
        void hideUI();
        ArrayList<String> sendFilePaths();
        ArrayList<String> sendColors();
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
    @BindView(R.id.image_view_story_editor_back_button) ImageView backButtonImageView;
    @BindView(R.id.image_view_story_editor_download_icon) ImageView downloadButtonImageView;
    @BindView(R.id.constraint_layout_icon_container) ConstraintLayout iconContainerConstraintLayout;
    @BindView(R.id.frame_layout_fragment_placeholder_story_editor) FrameLayout fragmentPlaceholderFrameLayout;
    @BindView(R.id.frame_layout_fragment_placeholder_save_story) FrameLayout fragmentPlaceholderSaveStoryFrameLayout;
    @BindView(R.id.frame_layout_fragment_placeholder_choose) FrameLayout fragmentPlaceholderChoose;

    // testing placeholder views
    @BindView(R.id.button_story_editor_prev) Button prevTextView;
    @BindView(R.id.button_story_editor_next) Button nextTextView;

    // fragments
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Fragment templatePlaceholderFragment;
    SaveStoryDialogFragment saveStoryDialogFragment = new SaveStoryDialogFragment();
    ChooseATemplateFragment chooseATemplateFragment = new ChooseATemplateFragment();

    // save photo to storage
    public void saveImage() {
        Toast.makeText(getBaseContext(), "saving image to device...", Toast.LENGTH_SHORT).show();

        ImageHandler.writeFile(StoryEditorActivity.this, fragmentPlaceholderFrameLayout, storiesPresenter.getPages().getName());

        // get values from template fragments
        storiesPresenter.updateImageUris(currentPageIndex, onSaveImageListener.sendFilePaths());
        storiesPresenter.updateColors(currentPageIndex, onSaveImageListener.sendColors());
        storiesPresenter.updateTitle(currentPageIndex, onSaveImageListener.sendTitle());
        storiesPresenter.updateText(currentPageIndex, onSaveImageListener.sendText());

        // put stories in shared pref
        SharedPrefHandler.putStories(this, storiesPresenter, isNewStories);
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

        // get data from intent
        Stories savedStories = getIntent().getParcelableExtra(EXTRA_SAVED_STORIES);

        // initialize presenter
        storiesPresenter = new StoriesPresenter(this, savedStories);
        currentPageIndex = 0;

        isNewStories = getIntent().getBooleanExtra(EXTRA_IS_NEW_STORIES, true);

        if (isNewStories) {
            storiesPresenter.addPage(new Page());
            // add Choose a template fragment
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.frame_layout_fragment_placeholder_choose, chooseATemplateFragment);
            fragmentTransaction.commit();
        } else {
            loadSavedPageToTemplate();
        }

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

                // get values from template fragments
                storiesPresenter.updateImageUris(currentPageIndex, onSaveImageListener.sendFilePaths());
                storiesPresenter.updateColors(currentPageIndex, onSaveImageListener.sendColors());
                storiesPresenter.updateTitle(currentPageIndex, onSaveImageListener.sendTitle());
                storiesPresenter.updateText(currentPageIndex, onSaveImageListener.sendText());

                // add new page to stories
                currentPageIndex++;
                storiesPresenter.addPage(new Page());

                // start new template fragment
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.frame_layout_fragment_placeholder_choose, chooseATemplateFragment);
                fragmentTransaction.commit();
            }
        });
        prevTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "prev", Toast.LENGTH_SHORT).show();
                currentPageIndex--;
                loadSavedPageToTemplate();
            }
        });
        nextTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "next", Toast.LENGTH_SHORT).show();
                currentPageIndex++;
                loadSavedPageToTemplate();
            }
        });
    }

    // SaveStoryDialog interface
    @Override
    public void saveStory() {
        onSaveImageListener.hideUI();
        saveImage();
        finish();
    }

    @Override
    public void saveStories() {
        Toast.makeText(getBaseContext(), "saving stories...", Toast.LENGTH_SHORT).show();

        // get values from template fragments
        storiesPresenter.updateImageUris(currentPageIndex, onSaveImageListener.sendFilePaths());
        storiesPresenter.updateColors(currentPageIndex, onSaveImageListener.sendColors());
        storiesPresenter.updateTitle(currentPageIndex, onSaveImageListener.sendTitle());
        storiesPresenter.updateText(currentPageIndex, onSaveImageListener.sendText());

        // put stories in shared pref
        SharedPrefHandler.putStories(this, storiesPresenter, isNewStories);

        finish();
    }

    @Override
    public void shareStoryToInstagram() {
        Toast.makeText(getBaseContext(), "sharing to instagram", Toast.LENGTH_SHORT).show();
        onSaveImageListener.hideUI();
        saveImage();
        createInstagramIntent();
        finish();
    }

    // OnTemplateListener
    @Override
    public void sendTemplate(String template) {
        storiesPresenter.updateTemplateName(currentPageIndex, template);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        templatePlaceholderFragment = templateManager.getTemplate(template);

        onSaveImageListener = (OnSaveImageListener) templatePlaceholderFragment;
        fragmentTransaction.remove(chooseATemplateFragment);
        fragmentTransaction.add(R.id.frame_layout_fragment_placeholder_story_editor, templatePlaceholderFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void updateView() {

    }

    public void loadSavedPageToTemplate() {
        // get template fragment for saved page
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        String template = storiesPresenter.getPage(currentPageIndex).getTemplateName();
        storiesPresenter.updateTemplateName(currentPageIndex, template);
        templatePlaceholderFragment = templateManager.getTemplate(template);
        onSaveImageListener = (OnSaveImageListener) templatePlaceholderFragment;

        // put page in bundle for template fragment
        Page page = storiesPresenter.getPage(currentPageIndex);
        Bundle bundle = new Bundle();
        bundle.putParcelable(BUNDLE_CURRENT_PAGE, page);
        templatePlaceholderFragment.setArguments(bundle);
        fragmentTransaction.add(R.id.frame_layout_fragment_placeholder_story_editor, templatePlaceholderFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}

