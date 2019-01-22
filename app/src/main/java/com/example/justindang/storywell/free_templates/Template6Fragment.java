package com.example.justindang.storywell.free_templates;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.colorpicker.shishank.colorpicker.ColorPicker;
import com.example.justindang.storywell.R;
import com.example.justindang.storywell.activities.StoryEditorActivity;
import com.example.justindang.storywell.model.Page;
import com.example.justindang.storywell.model.Stories;
import com.example.justindang.storywell.utilities.ImageHandler;
import com.example.justindang.storywell.view_model.StoriesViewModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;


public class Template6Fragment extends Fragment implements StoryEditorActivity.OnSaveImageListener {

    // static data
    private static final String BUNDLE_IS_NEW_PAGE = "new page";
    private static final int IMAGE_GALLERY_REQUEST_OUTER = 19;
    private static final int IMAGE_GALLERY_REQUEST_INNER = 25;

    // view model
    StoriesViewModel storiesViewModel;

    // image uri strings and color
    // index 0 = outer media
    // index 1 = inner media
    String outerMediaUriString;
    String innerMediaUriString;
    Integer backgroundColor;
    String title;
    String text;

    // views
    @BindView(R.id.edit_text_template6_add_title) EditText addTitleEditText;
    @BindView(R.id.edit_text_template6_tap_to_add) EditText tapToAddEditText;
    @BindView(R.id.text_view_template6_tip) TextView tipTextView;
    @BindView(R.id.image_view_template6_outer_media) ImageView outerMediaImageView;
    @BindView(R.id.image_view_template6_inner_media) ImageView innerMediaImageView;
    @BindView(R.id.image_view_template6_add_outer_media) ImageView addOuterMediaImageView;
    @BindView(R.id.image_view_template6_add_inner_media) ImageView addInnerMediaImageView;
    @BindView(R.id.image_view_template6_remove_outer_media) ImageView removeOuterMediaImageView;
    @BindView(R.id.image_view_template6_remove_inner_media) ImageView removeInnerMediaImageView;
    @BindView(R.id.image_view_template6_color_picker) ImageView colorPickerImageView;
    @BindView(R.id.constraint_layout_template6_container) ConstraintLayout containerConstraintLayout;

    public Template6Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_template6, container, false);
        ButterKnife.bind(this, view);

        hideUI();
        colorPickerImageView.setVisibility(View.VISIBLE);
        tipTextView.setVisibility(View.VISIBLE);

        // view model
        storiesViewModel = ViewModelProviders.of(getActivity()).get(StoriesViewModel.class);
        storiesViewModel.getStories().observe(this, new Observer<Stories>() {
            @Override
            public void onChanged(@Nullable Stories stories) {
                if (stories.getColors().size() == 0 || stories.getColors().get(0).equals("NOT FOUND")) {
                    backgroundColor = Integer.valueOf(getContext().getResources().getColor(R.color.colorPeach));
                } else {
                    backgroundColor = Integer.valueOf(storiesViewModel.getStories().getValue().getColors().get(0));
                }
                containerConstraintLayout.setBackgroundColor(backgroundColor);
            }
        });

        // load previously saved page
        if (!getArguments().getBoolean(BUNDLE_IS_NEW_PAGE)) {
            outerMediaUriString = storiesViewModel.getStories().getValue().getImageUris().get(0);
            innerMediaUriString = storiesViewModel.getStories().getValue().getImageUris().get(1);
            title = storiesViewModel.getStories().getValue().getTitle();
            text = storiesViewModel.getStories().getValue().getText();

            if (outerMediaUriString.equals("") || outerMediaUriString.equals("NOT FOUND")) {
                addOuterMediaImageView.setVisibility(View.VISIBLE);
                removeOuterMediaImageView.setVisibility(View.INVISIBLE);
            } else {
                addOuterMediaImageView.setVisibility(View.INVISIBLE);
                removeOuterMediaImageView.setVisibility(View.VISIBLE);
                Uri outerImageUri = Uri.parse(outerMediaUriString);
                ImageHandler.setImageToImageView(getContext(), outerImageUri, outerMediaImageView, ImageView.ScaleType.CENTER_CROP);
            }

            if (innerMediaUriString.equals("") || innerMediaUriString.equals("NOT FOUND")) {
                addInnerMediaImageView.setVisibility(View.VISIBLE);
                removeInnerMediaImageView.setVisibility(View.INVISIBLE);
            } else {
                addInnerMediaImageView.setVisibility(View.INVISIBLE);
                removeInnerMediaImageView.setVisibility(View.VISIBLE);
                Uri innerImageUri = Uri.parse(innerMediaUriString);
                ImageHandler.setImageToImageView(getContext(), innerImageUri, innerMediaImageView, ImageView.ScaleType.CENTER_CROP);
            }

            tipTextView.setVisibility(View.INVISIBLE);

            addTitleEditText.setText(title);
            tapToAddEditText.setText(text);
        }

        // clicklisteners
        addOuterMediaImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addOuterMediaImageView.setVisibility(View.INVISIBLE);
                removeOuterMediaImageView.setVisibility(View.VISIBLE);
                Intent photoGalleryIntent = ImageHandler.createPhotoGalleryIntent();
                startActivityForResult(photoGalleryIntent, IMAGE_GALLERY_REQUEST_OUTER);
            }
        });
        addInnerMediaImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addInnerMediaImageView.setVisibility(View.INVISIBLE);
                removeInnerMediaImageView.setVisibility(View.VISIBLE);
                Intent photoGalleryIntent = ImageHandler.createPhotoGalleryIntent();
                startActivityForResult(photoGalleryIntent, IMAGE_GALLERY_REQUEST_INNER);
            }
        });
        removeInnerMediaImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                innerMediaImageView.setImageBitmap(null);
                innerMediaUriString = "";
                addInnerMediaImageView.setVisibility(View.VISIBLE);
                removeInnerMediaImageView.setVisibility(View.INVISIBLE);
                updateViewModel();
            }
        });
        removeOuterMediaImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                outerMediaImageView.setImageBitmap(null);
                outerMediaUriString = "";
                addOuterMediaImageView.setVisibility(View.VISIBLE);
                removeOuterMediaImageView.setVisibility(View.INVISIBLE);
                updateViewModel();
            }
        });

        // text changed listeners
        addTitleEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                updateViewModel();
            }
        });
        tapToAddEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                updateViewModel();
            }
        });

        return view;
    }

    // return selected image from gallery
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            if (requestCode == IMAGE_GALLERY_REQUEST_OUTER) {
                outerMediaUriString = data.getDataString();
                ImageHandler.setImageToImageView(getContext(), imageUri, outerMediaImageView, ImageView.ScaleType.CENTER_CROP);
            } else if (requestCode == IMAGE_GALLERY_REQUEST_INNER) {
                innerMediaUriString = data.getDataString();
                ImageHandler.setImageToImageView(getContext(), imageUri, innerMediaImageView, ImageView.ScaleType.CENTER_CROP);
            }
            updateViewModel();
        }
    }

    @Override
    public void hideUI() {
        removeOuterMediaImageView.setVisibility(View.INVISIBLE);
        removeInnerMediaImageView.setVisibility(View.INVISIBLE);
        colorPickerImageView.setVisibility(View.INVISIBLE);
        tipTextView.setVisibility(View.INVISIBLE);
    }

    // update data for view model
    private void updateViewModel() {
        ArrayList<String> updatedImageUris = new ArrayList<>();
        updatedImageUris.add(outerMediaUriString);
        updatedImageUris.add(innerMediaUriString);
        ArrayList<String> updatedColors = new ArrayList<>();
        updatedColors.add(String.valueOf(backgroundColor));
        Stories updatedStories = new Stories(storiesViewModel.getStories().getValue());
        updatedStories.setImageUris(updatedImageUris);
        updatedStories.setColors(updatedColors);
        title = addTitleEditText.getText().toString();
        updatedStories.setTitle(title);
        text = tapToAddEditText.getText().toString();
        updatedStories.setText(text);
        storiesViewModel.setStories(updatedStories);
    }
}
