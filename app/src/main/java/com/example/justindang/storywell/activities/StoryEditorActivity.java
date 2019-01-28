package com.example.justindang.storywell.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.UnicodeSetSpanner;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.colorpicker.shishank.colorpicker.ColorPicker;
import com.example.justindang.storywell.fragments.ColorPickerFragment;
import com.example.justindang.storywell.fragments.SelectOrderFragment;
import com.example.justindang.storywell.fragments.ChooseATemplateFragment;
import com.example.justindang.storywell.R;
import com.example.justindang.storywell.fragments.SaveStoryDialogFragment;
import com.example.justindang.storywell.adapters.TemplateGridRecyclerAdapter;
import com.example.justindang.storywell.fragments.ShapePickerFragment;
import com.example.justindang.storywell.listeners.OnSwipeTouchListener;
import com.example.justindang.storywell.model.Page;
import com.example.justindang.storywell.model.Stories;
import com.example.justindang.storywell.utilities.ImageHandler;
import com.example.justindang.storywell.utilities.InstagramHandler;
import com.example.justindang.storywell.utilities.SharedPrefHandler;
import com.example.justindang.storywell.utilities.TemplateManager;
import com.example.justindang.storywell.view_model.StoriesViewModel;
import com.example.justindang.storywell.views.FreeTemplate1View;
import com.example.justindang.storywell.views.TemplateView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;

public class StoryEditorActivity extends AppCompatActivity
        implements SaveStoryDialogFragment.OnSaveListener,
        TemplateGridRecyclerAdapter.OnTemplateListener,
        TemplateView.MediaHandler {

    // static data
    private static final String EXTRA_IS_NEW_STORIES = "new stories";
    private static final String EXTRA_SAVED_STORIES = "saved stories";
    private static final String DIALOG_SAVE_STORY = "save story";
    private static final int IMAGE_GALLERY_REQUEST = 98;

    boolean isNewStories;
    boolean isShapeInserterOn;
    boolean isColorPickerOn;
    String template;

    String mediaString;
    int currentViewId;
    int currentMediaIndex;

    // model of story
    private Stories savedStories;
    private StoriesViewModel storiesViewModel;

    // interfaces
    public interface OnSaveImageListener {
        void hideUI();
    }
    OnSaveImageListener onSaveImageListener;

    public interface UpdateOrderListener {
        boolean allPagesSelected();
        ArrayList<Page> getNewPageOrder();
    }
    UpdateOrderListener updateOrderListener;

    // template manager
    TemplateManager templateManager = new TemplateManager();

    // views
    @BindView(R.id.image_view_story_editor_aa_icon)ImageView aaIconImageView;
    @BindView(R.id.image_view_story_editor_square_circle_icon) ImageView squareCircleIconImageView;
    @BindView(R.id.image_view_story_editor_plus_icon) ImageView plusIconImageView;
    @BindView(R.id.image_view_story_editor_three_circle_icon) ImageView threeCircleIconImageView;
    @BindView(R.id.image_view_story_editor_angle_brackets_icon) ImageView angleBracketsIconImageView;
    @BindView(R.id.image_view_story_editor_back_button) ImageView backButtonImageView;
    @BindView(R.id.image_view_story_editor_download_icon) ImageView downloadButtonImageView;
    @BindView(R.id.constraint_layout_icon_container) ConstraintLayout iconContainerConstraintLayout;
    @BindView(R.id.frame_layout_fragment_placeholder_save_story) FrameLayout fragmentPlaceholderSaveStoryFrameLayout;
    @BindView(R.id.frame_layout_fragment_placeholder_choose) FrameLayout fragmentPlaceholderChoose;
    @BindView(R.id.frame_layout_fragment_placeholder_inserter) FrameLayout fragmentPlaceholderInserter;
    @BindView(R.id.frame_layout_story_editor_anywhere) FrameLayout frameLayoutAnywhere;
    @BindView(R.id.text_view_story_editor_update_icon) TextView updateTextView;
    @BindView(R.id.image_view_eye_icon) ImageView eyeImageView;
    @BindView(R.id.text_view_story_editor_page_number) TextView pageNumberTextView;
    @BindView(R.id.linear_layout_sticker_layer) FrameLayout stickerLayerLinearLayout;
    @BindView(R.id.linear_layout_fragment_placeholder_story_editor) LinearLayout fragmentPlaceholderLinearLayout;

    // fragments
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Fragment templatePlaceholderFragment;

    // initialize fragments
    ShapePickerFragment shapePickerFragment = new ShapePickerFragment();
    ColorPickerFragment colorPickerFragment = new ColorPickerFragment();
    SaveStoryDialogFragment saveStoryDialogFragment = new SaveStoryDialogFragment();
    ChooseATemplateFragment chooseATemplateFragment = new ChooseATemplateFragment();

    // save photo to storage
    public void saveImage() {
        Toast.makeText(StoryEditorActivity.this, "saving image to device...",
                Toast.LENGTH_SHORT).show();

        /*
        ImageHandler.writeFile(StoryEditorActivity.this,
                frameLayoutArrayLists.get(storiesViewModel.getStories().getValue().getCurrentIndex()),
                storiesViewModel.getStories().getValue().getName());
        */

        // put stories in shared pref
        Toast.makeText(StoryEditorActivity.this, storiesViewModel.getStories().getValue().toString(),
                Toast.LENGTH_SHORT).show();
        SharedPrefHandler.putStories(this, storiesViewModel.getStories().getValue(),
                isNewStories);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_editor);
        ButterKnife.bind(this);

        updateTextView.setVisibility(View.INVISIBLE);
        eyeImageView.setVisibility(View.INVISIBLE);

        // get data from intent
        Intent intent = getIntent();
        savedStories = new Stories((Stories) intent.getParcelableExtra(EXTRA_SAVED_STORIES));
        savedStories.addPage(new Page("free template 1"));
        savedStories.addPage(new Page("free template 2"));

        isNewStories = intent.getBooleanExtra(EXTRA_IS_NEW_STORIES, true);

        // initialize view model
        storiesViewModel = ViewModelProviders.of(StoryEditorActivity.this).get(StoriesViewModel.class);
        storiesViewModel.setStories(savedStories);
        storiesViewModel.getStories().observeForever(new Observer<Stories>() {
            @Override
            public void onChanged(@Nullable Stories stories) {
                // page number
                pageNumberTextView.setText(
                        String.valueOf(storiesViewModel.getStories().getValue().getCurrentIndex()));
            }
        });

        // fragment booleans
        isShapeInserterOn = false;
        isColorPickerOn = false;

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

        // new page
        plusIconImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(StoryEditorActivity.this, "new page", Toast.LENGTH_SHORT).show();
                // add Choose a template fragment
                addChooseATemplateFragment();

                // go to next page
                Stories updatedStories = storiesViewModel.getStories().getValue();
                updatedStories.nextPage();
                storiesViewModel.setStories(updatedStories);
            }
        });

        // color picker
        threeCircleIconImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(StoryEditorActivity.this, "insert color", Toast.LENGTH_SHORT).show();
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();

                if (!isColorPickerOn) {
                    fragmentTransaction.add(R.id.frame_layout_fragment_placeholder_inserter, colorPickerFragment);
                    fragmentTransaction.addToBackStack(null);
                    isColorPickerOn = true;
                } else {
                    fragmentTransaction.remove(colorPickerFragment);
                    isColorPickerOn = false;
                }
                fragmentTransaction.commit();
            }
        });

        // change page order
        angleBracketsIconImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // change visibility of buttons
                Toast.makeText(StoryEditorActivity.this, "change page order", Toast.LENGTH_SHORT).show();
            }
        });

        // text inserter
        aaIconImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(StoryEditorActivity.this, "insert text", Toast.LENGTH_SHORT).show();
            }
        });

        // shape inserter
        squareCircleIconImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(StoryEditorActivity.this, "insert shape", Toast.LENGTH_SHORT).show();

                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();

                if (!isShapeInserterOn) {
                    fragmentTransaction.add(R.id.frame_layout_fragment_placeholder_inserter, shapePickerFragment);
                    fragmentTransaction.addToBackStack(null);
                    isShapeInserterOn = true;
                } else {
                    fragmentTransaction.remove(shapePickerFragment);
                    isShapeInserterOn = false;
                }
                fragmentTransaction.commit();
            }
        });

        updateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(StoryEditorActivity.this, "insert text", Toast.LENGTH_SHORT).show();
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

        // put stories in shared pref
        SharedPrefHandler.putStories(this, storiesViewModel.getStories().getValue(), isNewStories);
        finish();
    }

    @Override
    public void shareStoryToInstagram() {
        Toast.makeText(getBaseContext(), "sharing to instagram", Toast.LENGTH_SHORT).show();
        onSaveImageListener.hideUI();
        saveImage();
        Intent instagramIntent = InstagramHandler.createInstagramIntent(
                StoryEditorActivity.this, storiesViewModel.getStories().getValue().getName());

        // verify that intent will resolve to an activity
        if (instagramIntent.resolveActivity(this.getPackageManager()) != null) {
            startActivity(Intent.createChooser(instagramIntent, "Share Story"));
        }
        finish();
    }

    // OnTemplateListener
    @Override
    public void sendTemplate(String template) {
        // instantiate new page
        Page page = new Page();
        page.setTemplateName(template);

        // add page to stories
        Stories newStories = storiesViewModel.getStories().getValue();
        newStories.addPage(page);
        storiesViewModel.setStories(newStories);

        // remove choose a template fragment
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        templatePlaceholderFragment = templateManager.getTemplate(template);
        onSaveImageListener = (OnSaveImageListener) templatePlaceholderFragment;
        fragmentTransaction.remove(chooseATemplateFragment);
    }

    // add choose a template fragment to backstack
    public void addChooseATemplateFragment() {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frame_layout_fragment_placeholder_choose,
                chooseATemplateFragment);
        fragmentTransaction.commit();
    }

    // return selected image from gallery
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == IMAGE_GALLERY_REQUEST) {
            Log.e("checking", "REQUEST CODE CHECKED");
            if (resultCode == RESULT_OK) {
                mediaString = data.toUri(0);
                Log.e("checking", "RESULT OK\n media string: ".concat(mediaString));
                TemplateView templateView = findViewById(currentViewId);
                templateView.setMediaImageView(currentMediaIndex, data.getData());
            }
        }
    }

    // media handler
    public String getUriString() {
        Intent photoGalleryIntent = ImageHandler.createPhotoGalleryIntent();
        startActivityForResult(photoGalleryIntent, IMAGE_GALLERY_REQUEST);
        return mediaString;
    }

    @Override
    public void getGalleryPhoto(int viewId, int mediaIndex) {
        currentViewId = viewId;
        currentMediaIndex = mediaIndex;
        Intent photoGalleryIntent = ImageHandler.createPhotoGalleryIntent();
        startActivityForResult(photoGalleryIntent, IMAGE_GALLERY_REQUEST);
    }
}

