package com.example.justindang.storywell.free_templates;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;

public class Template5Fragment extends Fragment implements StoryEditorActivity.OnSaveImageListener {
    // request code
    private static final int IMAGE_GALLERY_REQUEST_MEDIA = 13;

    // tags
    private static final String EXTRA_IS_NEW_STORIES = "new stories";
    private static final String EXTRA_SAVED_STORIES = "saved stories";

    // image uri strings, color, title, text
    ArrayList<String> imageUriStrings;
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
    @BindView(R.id.color_picker_template5) ColorPicker colorPicker;
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

        if (!getActivity().getIntent().getBooleanExtra(EXTRA_IS_NEW_STORIES, true)) {
            addMediaImageView.setVisibility(View.INVISIBLE);
            removeMediaImageView.setVisibility(View.VISIBLE);
            tipEditText.setVisibility(View.INVISIBLE);

            // get data and put into views
            Stories savedStories = getActivity().getIntent().getParcelableExtra(EXTRA_SAVED_STORIES);
            mediaUriString = savedStories.getImageUris(0).get(0);
            title = savedStories.getTitle(0);
            text = savedStories.getText(0);
            backgroundColor = Integer.valueOf(savedStories.getColors(0).get(0));

            Uri imageUri = Uri.parse(mediaUriString);
            ImageHandler.setImageToImageView(getContext(), imageUri, mediaImageView, ImageView.ScaleType.CENTER_CROP);
            addTitleEditText.setText(title);
            tapToAddEditText.setText(text);
            containerConstraintLayout.setBackgroundColor(backgroundColor);
        }

        // initialize filePath
        imageUriStrings = new ArrayList<>();

        // color picker
        colorPicker.setGradientView(R.drawable.color_gradient);
        colorPicker.setColorSelectedListener(new ColorPicker.ColorSelectedListener() {
            @Override
            public void onColorSelected(int color, boolean isTapUp) {
                backgroundColor = color;
                containerConstraintLayout.setBackgroundColor(backgroundColor);
            }
        });

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
                addMediaImageView.setVisibility(View.VISIBLE);
                removeMediaImageView.setVisibility(View.INVISIBLE);
                imageUriStrings.remove(mediaUriString);
            }
        });
        colorPickerImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (colorPicker.getVisibility() == View.INVISIBLE) {
                    colorPicker.setVisibility(View.VISIBLE);
                } else {
                    colorPicker.setVisibility(View.INVISIBLE);
                }
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
        }
    }

    @Override
    public void hideUI() {
        removeMediaImageView.setVisibility(View.INVISIBLE);
        colorPickerImageView.setVisibility(View.INVISIBLE);
        colorPicker.setVisibility(View.INVISIBLE);
    }

    @Override
    public ArrayList<String> sendFilePaths() {
        imageUriStrings.add(mediaUriString);
        return imageUriStrings;
    }

    @Override
    public ArrayList<String> sendColors() {
        ArrayList<String> colors = new ArrayList<String>();
        if (backgroundColor == null) {
            backgroundColor = 0;
        }
        colors.add(backgroundColor.toString());
        return colors;
    }

    @Override
    public String sendTitle() {
        return addTitleEditText.getText().toString();
    }

    @Override
    public String sendText() {
        return tapToAddEditText.getText().toString();
    }

    @Override
    public Page sendPage() {
        return null;
    }
}
