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
import com.example.justindang.storywell.utilities.ImageHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;


public class Template6Fragment extends Fragment implements StoryEditorActivity.OnSaveImageListener {
    // request codes
    private static final int IMAGE_GALLERY_REQUEST_OUTER = 19;
    private static final int IMAGE_GALLERY_REQUEST_INNER = 25;

    // image uri strings and color
    ArrayList<String> imageUriStrings;
    String innerMediaUriString;
    String outerMediaUriString;
    Integer backgroundColor;

    // views
    @BindView(R.id.edit_text_template6_add_title) EditText addTitleEditText;
    @BindView(R.id.edit_text_template6_tap_to_add) EditText tapToAddEditText;
    @BindView(R.id.edit_text_template6_tip) EditText tipEditText;
    @BindView(R.id.image_view_template6_outer_media) ImageView outerMediaImageView;
    @BindView(R.id.image_view_template6_inner_media) ImageView innerMediaImageView;
    @BindView(R.id.image_view_template6_add_outer_media) ImageView addOuterMediaImageView;
    @BindView(R.id.image_view_template6_add_inner_media) ImageView addInnerMediaImageView;
    @BindView(R.id.image_view_template6_remove_outer_media) ImageView removeOuterMediaImageView;
    @BindView(R.id.image_view_template6_remove_inner_media) ImageView removeInnerMediaImageView;
    @BindView(R.id.image_view_template6_color_picker) ImageView colorPickerImageView;
    @BindView(R.id.color_picker_template6) ColorPicker colorPicker;
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


        // initialize filePaths
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
                addInnerMediaImageView.setVisibility(View.VISIBLE);
                removeInnerMediaImageView.setVisibility(View.INVISIBLE);
                imageUriStrings.remove(innerMediaUriString);
            }
        });
        removeOuterMediaImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                outerMediaImageView.setImageBitmap(null);
                addOuterMediaImageView.setVisibility(View.VISIBLE);
                removeOuterMediaImageView.setVisibility(View.INVISIBLE);
                imageUriStrings.remove(outerMediaUriString);
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
            if (requestCode == IMAGE_GALLERY_REQUEST_OUTER) {
                outerMediaUriString = data.getDataString();
                imageUriStrings.add(outerMediaUriString);
                ImageHandler.setImageToImageView(getContext(), data, outerMediaImageView, ImageView.ScaleType.CENTER_CROP);
            } else if (requestCode == IMAGE_GALLERY_REQUEST_INNER) {
                innerMediaUriString = data.getDataString();
                imageUriStrings.add(innerMediaUriString);
                ImageHandler.setImageToImageView(getContext(), data, innerMediaImageView, ImageView.ScaleType.CENTER_CROP);
            }
        }
    }

    @Override
    public void hideUI() {
        removeOuterMediaImageView.setVisibility(View.INVISIBLE);
        removeInnerMediaImageView.setVisibility(View.INVISIBLE);
        colorPicker.setVisibility(View.INVISIBLE);
        colorPickerImageView.setVisibility(View.INVISIBLE);
    }

    @Override
    public ArrayList<String> sendFilePaths() {
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
}
