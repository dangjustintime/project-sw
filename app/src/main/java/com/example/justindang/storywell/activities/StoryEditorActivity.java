package com.example.justindang.storywell.activities;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.UnicodeSetSpanner;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import com.example.justindang.storywell.utilities.SharedPrefHandler;
import com.example.justindang.storywell.utilities.TemplateManager;
import com.example.justindang.storywell.view_model.StoriesViewModel;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;

public class StoryEditorActivity extends AppCompatActivity
        implements SaveStoryDialogFragment.OnSaveListener,
        TemplateGridRecyclerAdapter.OnTemplateListener {

    // static data
    private static final String EXTRA_IS_NEW_STORIES = "new stories";
    private static final String EXTRA_SAVED_STORIES = "saved stories";
    private static final String DIALOG_SAVE_STORY = "save story";
    private static final String BUNDLE_CURRENT_PAGE = "current page";
    private static final String BUNDLE_IS_NEW_PAGE = "new page";

    boolean isNewStories;
    boolean isShapeInserterOn;
    boolean isColorPickerOn;
    String template;

    // frame layout IDs
    ArrayList<FrameLayout> frameLayoutArrayLists = new ArrayList<>();
    ArrayList<Integer> frameLayoutIds = new ArrayList<>();

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

        ImageHandler.writeFile(StoryEditorActivity.this, frameLayoutArrayLists.get(0),
                storiesViewModel.getStories().getValue().getName());

        // put stories in shared pref
        Toast.makeText(StoryEditorActivity.this, storiesViewModel.getStories().getValue().toString(),
                Toast.LENGTH_SHORT).show();
        SharedPrefHandler.putStories(this, storiesViewModel.getStories().getValue(),
                isNewStories);
    }

    // creates intent that launches instagram app to post story
    void createInstagramIntent() {
        // create URI from media
        File media = new File(Environment.getExternalStorageDirectory()
                + "/Pictures/storywell/"
                + storiesViewModel.getStories().getValue().getName()
                + ".jpg");
        Uri uri = FileProvider.getUriForFile(StoryEditorActivity.this,
                "com.example.justindang.storywell.fileprovider", media);

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

    @SuppressLint("ClickableViewAccessibility")
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
        isNewStories = intent.getBooleanExtra(EXTRA_IS_NEW_STORIES, true);

        // initialize view model
        storiesViewModel = ViewModelProviders.of(StoryEditorActivity.this).get(StoriesViewModel.class);
        storiesViewModel.setStories(savedStories);

        Toast.makeText(getApplicationContext(), savedStories.toString(), Toast.LENGTH_SHORT).show();
        // fragment booleans
        isShapeInserterOn = false;
        isColorPickerOn = false;

        if (isNewStories) {
            // add Choose a template fragment
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.frame_layout_fragment_placeholder_choose,
                    chooseATemplateFragment);
            fragmentTransaction.commit();
        } else {
            // get template fragment for saved page
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            template = storiesViewModel.getStories()
                    .getValue()
                    .getPage()
                    .getTemplateName();
            templatePlaceholderFragment = templateManager.getTemplate(template);
            onSaveImageListener = (OnSaveImageListener) templatePlaceholderFragment;

            // put page in bundle for template fragment
            Page page = storiesViewModel.getStories().getValue().getPage();
            Bundle bundle = new Bundle();
            bundle.putParcelable(BUNDLE_CURRENT_PAGE, page);
            bundle.putBoolean(BUNDLE_IS_NEW_PAGE, false);
            templatePlaceholderFragment.setArguments(bundle);

            addNewFrameLayoutToLinearLayout();

            // add fragment
            fragmentTransaction.add(frameLayoutIds.get(0),
                    templateManager.getTemplate(template));
            fragmentTransaction.commit();
        }

        // page number
        pageNumberTextView.setText(
                String.valueOf(storiesViewModel.getStories().getValue().getCurrentIndex() + 1));

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
            }
        });
        frameLayoutAnywhere.setOnTouchListener(new OnSwipeTouchListener(StoryEditorActivity.this) {
            @Override
            public void onSwipeLeft() {
                Toast.makeText(StoryEditorActivity.this, "swipe left", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onSwipeRight() {
                Toast.makeText(StoryEditorActivity.this, "swipe right", Toast.LENGTH_SHORT).show();
            }
        });
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
        createInstagramIntent();
        finish();
    }

    // OnTemplateListener
    @Override
    public void sendTemplate(String template) {
        // instantiate new page
        Page page = new Page();
        page.setTemplateName(template);

        // add page to stories
        Stories newStories = new Stories(storiesViewModel.getStories().getValue());
        newStories.addPage(page);
        storiesViewModel.setStories(newStories);

        addNewFrameLayoutToLinearLayout();

        // remove choose a template fragment
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        templatePlaceholderFragment = templateManager.getTemplate(template);
        onSaveImageListener = (OnSaveImageListener) templatePlaceholderFragment;
        fragmentTransaction.remove(chooseATemplateFragment);

        // add new page to bundle
        Bundle bundle = new Bundle();
        bundle.putParcelable(BUNDLE_CURRENT_PAGE, page);
        bundle.putBoolean(BUNDLE_IS_NEW_PAGE, true);
        templatePlaceholderFragment.setArguments(bundle);

        // add fragment to backstack
        fragmentTransaction.add(frameLayoutIds.get(0), templatePlaceholderFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void loadSavedPageToTemplate() {
        // get template fragment for saved page
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        template = storiesViewModel.getStories()
                .getValue()
                .getPage()
                .getTemplateName();
        templatePlaceholderFragment = templateManager.getTemplate(template);
        onSaveImageListener = (OnSaveImageListener) templatePlaceholderFragment;

        // put page in bundle for template fragment
        Page page = storiesViewModel.getStories().getValue().getPage();
        Bundle bundle = new Bundle();
        bundle.putParcelable(BUNDLE_CURRENT_PAGE, page);
        bundle.putBoolean(BUNDLE_IS_NEW_PAGE, false);
        templatePlaceholderFragment.setArguments(bundle);

        addNewFrameLayoutToLinearLayout();

        // add fragment to backstack
        fragmentTransaction.replace(frameLayoutIds.get(0), templatePlaceholderFragment);
        fragmentTransaction.commit();
    }

    // create a framelayout
    public void addNewFrameLayoutToLinearLayout() {
        FrameLayout frameLayout = new FrameLayout(StoryEditorActivity.this);
        frameLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        frameLayoutIds.add(frameLayout.generateViewId());
        frameLayout.setId(frameLayoutIds.get(0));
        frameLayoutArrayLists.add(frameLayout);
        fragmentPlaceholderLinearLayout.addView(frameLayout);
    }
}

