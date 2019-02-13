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
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
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
import com.example.justindang.storywell.views.FreeTemplate2View;
import com.example.justindang.storywell.views.FreeTemplate3View;
import com.example.justindang.storywell.views.FreeTemplate4View;
import com.example.justindang.storywell.views.FreeTemplate5View;
import com.example.justindang.storywell.views.FreeTemplate6View;
import com.example.justindang.storywell.views.ShapeStickerView;
import com.example.justindang.storywell.views.StickerView;
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
        TemplateView.TemplateHandler,
        ColorPickerFragment.OnColorListener,
        ShapePickerFragment.OnShapeListener,
        StickerView.OnStickerListener {
    // static data
    private static final String EXTRA_IS_NEW_STORIES = "new stories";
    private static final String EXTRA_SAVED_STORIES = "saved stories";
    private static final String DIALOG_SAVE_STORY = "save story";
    private static final int IMAGE_GALLERY_REQUEST = 98;
    // flags
    boolean isNewStories;
    boolean isShapeInserterOn;
    boolean isColorPickerOn;
    // current page data
    String mediaString;
    int currentTemplateViewId = 1;

    int currentMediaIndex;
    // model of story
    private StoriesViewModel storiesViewModel;
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
    @BindView(R.id.linear_layout_fragment_placeholder_story_editor) LinearLayout pagesPlaceholderLinearLayout;
    // fragments
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    // initialize fragments
    ShapePickerFragment shapePickerFragment = new ShapePickerFragment();
    ColorPickerFragment colorPickerFragment = new ColorPickerFragment();
    SaveStoryDialogFragment saveStoryDialogFragment = new SaveStoryDialogFragment();
    ChooseATemplateFragment chooseATemplateFragment = new ChooseATemplateFragment();
    SelectOrderFragment selectOrderFragment = new SelectOrderFragment();
    // save photo to storage
    public void saveImage() {
        Toast.makeText(StoryEditorActivity.this, "saving image to device...",
                Toast.LENGTH_SHORT).show();
        TemplateView templateView = findViewById(currentTemplateViewId);
        templateView.hideUi();
        ConstraintLayout templateLayer = findViewById(templateView.getTemplateLayerViewId());
        FrameLayout stickerLayer = findViewById(templateView.getStickerLayerViewId());
        for (int i = 0; i < stickerLayer.getChildCount(); i++) {
            StickerView stickerView = (StickerView) stickerLayer.getChildAt(i);
            stickerView.hideUi();
        }
        ImageHandler.writeBackgroundLayerFile(StoryEditorActivity.this, templateLayer,
                storiesViewModel.getStories().getValue().getName().concat(String.valueOf(currentTemplateViewId)));
        ImageHandler.writeStickerLayerFile(StoryEditorActivity.this, stickerLayer);
        saveStories();
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
        // set flags
        isNewStories = intent.getBooleanExtra(EXTRA_IS_NEW_STORIES, true);
        isShapeInserterOn = false;
        isColorPickerOn = false;
        // initialize view model
        storiesViewModel = ViewModelProviders.of(StoryEditorActivity.this).get(StoriesViewModel.class);
        storiesViewModel.setStories(intent.getParcelableExtra(EXTRA_SAVED_STORIES));
        // render UI based on isNewStories
        if (isNewStories) {
            addChooseATemplateFragment();
        } else {
            loadPages();
        }
        // clicklisteners
        downloadButtonImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager = getSupportFragmentManager();
                saveStoryDialogFragment.show(fragmentManager, DIALOG_SAVE_STORY);
            }
        });
        backButtonImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
                    if (colorPickerFragment.getTargetViewType() == TemplateView.STICKER) {
                        colorPickerFragment.setTargetViewType(TemplateView.TEMPLATE);
                    }
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
                Toast.makeText(StoryEditorActivity.this, "change page order", Toast.LENGTH_SHORT).show();
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.frame_layout_fragment_placeholder_choose, selectOrderFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                // change toolbar
                updateTextView.setVisibility(View.VISIBLE);
                eyeImageView.setVisibility(View.VISIBLE);
                downloadButtonImageView.setVisibility(View.INVISIBLE);
                backButtonImageView.setVisibility(View.INVISIBLE);
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
        // update page order
        updateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectOrderFragment.getNewOrderPageList()) {
                    selectOrderFragment.getNewOrderPageList();
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.remove(selectOrderFragment);
                    fragmentTransaction.commit();
                    pagesPlaceholderLinearLayout.removeAllViews();
                    loadPages();
                    updateTextView.setVisibility(View.INVISIBLE);
                    eyeImageView.setVisibility(View.INVISIBLE);
                    downloadButtonImageView.setVisibility(View.VISIBLE);
                    backButtonImageView.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(StoryEditorActivity.this, "all pages must be selected", Toast.LENGTH_SHORT).show();
                }
            }
        });
        // frameLayoutAnywhere
        frameLayoutAnywhere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { }
        });
    }
    // SaveStoryDialog interface
    @Override
    public void saveStory() {
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
        saveImage();
        Intent instagramIntent = InstagramHandler.createInstagramIntent(
                StoryEditorActivity.this, storiesViewModel.getStories().getValue().getName().concat(String.valueOf(currentTemplateViewId)));
        // verify that intent will resolve to an activity
        if (instagramIntent.resolveActivity(this.getPackageManager()) != null) {
            // startActivity(Intent.createChooser(instagramIntent, "Share Story"));
            startActivityForResult(instagramIntent, 0);
        }
        finish();
    }
    // OnTemplateListener interface
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
        fragmentTransaction.remove(chooseATemplateFragment);
        fragmentTransaction.commit();

        TemplateView templateView;
        if (template.equals("free template 1")) {
            templateView = new FreeTemplate1View(StoryEditorActivity.this);
        } else if (template.equals("free template 2")) {
            templateView = new FreeTemplate2View(StoryEditorActivity.this);
        } else if (template.equals("free template 3")) {
            templateView = new FreeTemplate3View(StoryEditorActivity.this);
        } else if (template.equals("free template 4")) {
            templateView = new FreeTemplate4View(StoryEditorActivity.this);
        } else if (template.equals("free template 5")) {
            templateView = new FreeTemplate5View(StoryEditorActivity.this);
        } else if (template.equals("free template 6")) {
            templateView = new FreeTemplate6View(StoryEditorActivity.this);
        } else {
            templateView = new TemplateView(getApplicationContext());
        }
        pagesPlaceholderLinearLayout.addView(templateView);
    }
    // return selected image from gallery
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == IMAGE_GALLERY_REQUEST) {
            if (resultCode == RESULT_OK) {
                mediaString = data.toUri(0);
                TemplateView templateView = findViewById(currentTemplateViewId);
                templateView.setMediaImageView(currentMediaIndex, data.getData());
                // updated view model
                Stories updatedStories = storiesViewModel.getStories().getValue();
                ArrayList<String> updatedUris = new ArrayList<>();
                updatedUris.add("NOT FOUND");
                updatedUris.add("NOT FOUND");
                if (updatedStories.getImageUris(getCurrentPageIndex()).size() > 0) {
                    updatedUris = updatedStories.getImageUris(getCurrentPageIndex());
                }
                updatedUris.set(currentMediaIndex, mediaString);
                updatedStories.setImageUris(getCurrentPageIndex(), updatedUris);
                storiesViewModel.setStories(updatedStories);
            }
        }
    }
    // MediaHandler
    @Override
    public void getGalleryPhoto(int viewId, int mediaIndex) {
        currentTemplateViewId = viewId;
        currentMediaIndex = mediaIndex;
        Intent photoGalleryIntent = ImageHandler.createPhotoGalleryIntent();
        startActivityForResult(photoGalleryIntent, IMAGE_GALLERY_REQUEST);
    }
    @Override
    public void sendViewId(int id) {
        currentTemplateViewId = id;
    }

    @Override
    public void sendTitle(String title) {
        Stories updatedStories = storiesViewModel.getStories().getValue();
        updatedStories.setTitle(getCurrentPageIndex(), title);
        storiesViewModel.setStories(updatedStories);
    }

    @Override
    public void sendText(String text) {
        Stories updatedStories = storiesViewModel.getStories().getValue();
        updatedStories.setText(getCurrentPageIndex(), text);
        storiesViewModel.setStories(updatedStories);
    }

    // OnColorListener
    @Override
    public void sendColor(int color, @TemplateView.ViewType int viewType) {
        TemplateView templateView = findViewById(currentTemplateViewId);
        if (viewType == TemplateView.TEMPLATE) {
            templateView.setColor(0, color);
            Stories updatedStories = storiesViewModel.getStories().getValue();
            ArrayList<String> newColorsList = new ArrayList<>();
            newColorsList.add(String.valueOf(color));
            updatedStories.setColors(getCurrentPageIndex(), newColorsList);
            storiesViewModel.setStories(updatedStories);
        } else if (viewType == TemplateView.STICKER) {
            FrameLayout stickerLayerFrameLayout = findViewById(templateView.getStickerLayerViewId());
            StickerView stickerView = (StickerView) stickerLayerFrameLayout.getChildAt(stickerLayerFrameLayout.getChildCount() - 1);
            stickerView.setColor(color);
        }
    }
    // OnShapeListener
    @Override
    public void sendShape(@ShapeStickerView.Shape int shape, boolean isSolid) {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout_fragment_placeholder_inserter, colorPickerFragment);
        colorPickerFragment.setTargetViewType(TemplateView.STICKER);
        fragmentTransaction.commit();
        isShapeInserterOn = false;
        isColorPickerOn = true;

        TemplateView templateView = findViewById(currentTemplateViewId);
        FrameLayout stickerLayerFrameLayout = findViewById(templateView.getStickerLayerViewId());
        StickerView stickerView = new ShapeStickerView(StoryEditorActivity.this, shape, isSolid);
        stickerLayerFrameLayout.addView(stickerView);
    }
    // OnStickerListener
    @Override
    public void sendStickerViewId(int id) { }

    // add choose a template fragment to backstack
    public void addChooseATemplateFragment() {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frame_layout_fragment_placeholder_choose,
                chooseATemplateFragment);
        fragmentTransaction.commit();
    }
    // load saved stories
    public void loadPages() {
        for(Page page : storiesViewModel.getStories().getValue().getPagesList()) {
            new TemplateView(StoryEditorActivity.this);
            TemplateView templateView;
            // free template 1
            if (page.getTemplateName().equals("free template 1")) {
                templateView = new FreeTemplate1View(StoryEditorActivity.this);
                if (!page.getImageUris().get(0).equals("NOT FOUND")) {
                    templateView.setMediaImageView(0, Uri.parse(page.getImageUris().get(0)));
                }
                if (!page.getImageUris().get(1).equals("NOT FOUND")) {
                    templateView.setMediaImageView(1, Uri.parse(page.getImageUris().get(1)));
                }
            // free template 2
            } else if (page.getTemplateName().equals("free template 2")) {
                templateView = new FreeTemplate2View(StoryEditorActivity.this);
                if (!page.getImageUris().get(0).equals("NOT FOUND")) {
                    templateView.setMediaImageView(0, Uri.parse(page.getImageUris().get(0)));
                }
                if (!page.getColors().get(0).equals("NOT FOUND")) {
                    templateView.setColor(0, Integer.parseInt(page.getColors().get(0)));
                }
            // free template 3
            } else if (page.getTemplateName().equals("free template 3")) {
                templateView = new FreeTemplate3View(StoryEditorActivity.this);
                if (!page.getImageUris().get(0).equals("NOT FOUND")) {
                    templateView.setMediaImageView(0, Uri.parse(page.getImageUris().get(0)));
                }
                if (!page.getImageUris().get(1).equals("NOT FOUND")) {
                    templateView.setMediaImageView(1, Uri.parse(page.getImageUris().get(1)));
                }
            // template 4
            } else if (page.getTemplateName().equals("free template 4")) {
                templateView = new FreeTemplate4View(StoryEditorActivity.this);
                if (!page.getImageUris().get(0).equals("NOT FOUND")) {
                    templateView.setMediaImageView(0, Uri.parse(page.getImageUris().get(0)));
                }
                if (!page.getImageUris().get(1).equals("NOT FOUND")) {
                    templateView.setMediaImageView(1, Uri.parse(page.getImageUris().get(1)));
                }
            // template 5
            } else if (page.getTemplateName().equals("free template 5")) {
                templateView = new FreeTemplate5View(StoryEditorActivity.this);
                if (!page.getImageUris().get(0).equals("NOT FOUND")) {
                    templateView.setMediaImageView(0, Uri.parse(page.getImageUris().get(0)));
                }
                if (!page.getColors().get(0).equals("NOT FOUND")) {
                    templateView.setColor(0, Integer.parseInt(page.getColors().get(0)));
                }
            // template 6
            } else if (page.getTemplateName().equals("free template 6")) {
                templateView = new FreeTemplate6View(StoryEditorActivity.this);
                if (!page.getImageUris().get(0).equals("NOT FOUND")) {
                    templateView.setMediaImageView(0, Uri.parse(page.getImageUris().get(0)));
                }
                if (!page.getImageUris().get(1).equals("NOT FOUND")) {
                    templateView.setMediaImageView(1, Uri.parse(page.getImageUris().get(1)));
                }
                if (!page.getColors().get(0).equals("NOT FOUND")) {
                    templateView.setColor(0, Integer.parseInt(page.getColors().get(0)));
                }
            } else {
                templateView = new TemplateView(StoryEditorActivity.this);
            }
            currentTemplateViewId = templateView.getId();
            pagesPlaceholderLinearLayout.addView(templateView);
            templateView.setTitle(page.getTitle());
            templateView.setText(page.getText());
        }
    }

    public int getCurrentPageIndex() {
        TemplateView templateView = findViewById(currentTemplateViewId);
        for (int i = 0; i < storiesViewModel.getStories().getValue().getNumPages(); i++) {
            TemplateView templateView1 = (TemplateView) pagesPlaceholderLinearLayout.getChildAt(0);
            if (templateView == templateView1) return i;
        }
        return -1;
    }
}