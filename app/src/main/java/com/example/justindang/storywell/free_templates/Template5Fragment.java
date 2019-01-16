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

public class Template5Fragment extends Fragment implements StoryEditorActivity.OnSaveImageListener {

    // static data
    private static final String BUNDLE_IS_NEW_PAGE = "new page";
    private static final int IMAGE_GALLERY_REQUEST_MEDIA = 13;

    // view model
    StoriesViewModel storiesViewModel;

    // image uri strings, color, title, text
    String mediaUriString;
    Integer backgroundColor;
    String title;
    String text;

    // views
    @BindView(R.id.image_view_template5_media) ImageView mediaImageView;
    @BindView(R.id.image_view_template5_add_media) ImageView addMediaImageView;
    @BindView(R.id.image_view_template5_remove_media) ImageView removeMediaImageView;
    @BindView(R.id.edit_text_template5_add_title) EditText addTitleEditText;
    @BindView(R.id.edit_text_template5_tap_to_add) EditText tapToAddEditText;
    @BindView(R.id.edit_text_template5_tip) EditText tipEditText;
    @BindView(R.id.image_view_template5_color_picker) ImageView colorPickerImageView;
    @BindView(R.id.constraint_layout_template5_container) ConstraintLayout containerConstraintLayout;

    public Template5Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_template5, container, false);
        ButterKnife.bind(this, view);

        hideUI();
        colorPickerImageView.setVisibility(View.VISIBLE);

        // view model
        storiesViewModel = ViewModelProviders.of(getActivity()).get(StoriesViewModel.class);
        storiesViewModel.getStories().observe(this, new Observer<Stories>() {
            @Override
            public void onChanged(@Nullable Stories stories) {
                backgroundColor = Integer.valueOf(storiesViewModel.getStories().getValue().getColors().get(0));
                containerConstraintLayout.setBackgroundColor(backgroundColor);
            }
        });

        // load previously saved page
        if (!getArguments().getBoolean(BUNDLE_IS_NEW_PAGE)) {
            mediaUriString = storiesViewModel.getStories().getValue().getImageUris().get(0);
            title = storiesViewModel.getStories().getValue().getTitle();
            text = storiesViewModel.getStories().getValue().getText();

            if (mediaUriString.equals("") || mediaUriString.equals("NOT FOUND")) {
                addMediaImageView.setVisibility(View.VISIBLE);
                removeMediaImageView.setVisibility(View.INVISIBLE);
            } else {
                addMediaImageView.setVisibility(View.INVISIBLE);
                removeMediaImageView.setVisibility(View.VISIBLE);
                tipEditText.setVisibility(View.INVISIBLE);
                Uri imageUri = Uri.parse(mediaUriString);
                ImageHandler.setImageToImageView(getContext(), imageUri, mediaImageView, ImageView.ScaleType.CENTER_CROP);
            }

            addTitleEditText.setText(title);
            tapToAddEditText.setText(text);
            tipEditText.setVisibility(View.INVISIBLE);
            containerConstraintLayout.setBackgroundColor(backgroundColor);
        } else {
            // set default color
            backgroundColor = Integer.valueOf(getContext().getResources().getColor(R.color.colorPeach));
        }

        // clickListeners
        addMediaImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMediaImageView.setVisibility(View.INVISIBLE);
                removeMediaImageView.setVisibility(View.VISIBLE);
                Intent photoGalleryIntent = ImageHandler.createPhotoGalleryIntent();
                startActivityForResult(photoGalleryIntent, IMAGE_GALLERY_REQUEST_MEDIA);
            }
        });
        removeMediaImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaImageView.setImageBitmap(null);
                mediaUriString = "";
                addMediaImageView.setVisibility(View.VISIBLE);
                removeMediaImageView.setVisibility(View.INVISIBLE);
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
            if (requestCode == IMAGE_GALLERY_REQUEST_MEDIA) {
                mediaUriString = data.getDataString();
                ImageHandler.setImageToImageView(getContext(), imageUri, mediaImageView, ImageView.ScaleType.CENTER_CROP);
            }
            updateViewModel();
        }
    }

    // OnSaveImageListener
    @Override
    public void hideUI() {
        removeMediaImageView.setVisibility(View.INVISIBLE);
        colorPickerImageView.setVisibility(View.INVISIBLE);
    }

    // update data for view model
    private void updateViewModel() {
        ArrayList<String> updatedImageUris = new ArrayList<>();
        updatedImageUris.add(mediaUriString);
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
