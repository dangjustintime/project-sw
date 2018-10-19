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


public class Template6Fragment extends Fragment implements StoryEditorActivity.OnSaveImageListener {
    // request codes
    private static final int IMAGE_GALLERY_REQUEST_OUTER = 19;
    private static final int IMAGE_GALLERY_REQUEST_INNER = 25;

    // tags
    private static final String EXTRA_IS_NEW_STORIES = "new stories";
    private static final String BUNDLE_CURRENT_PAGE = "current page";

    // image uri strings and color
    // index 0 = outer media
    // index 1 = inner media
    String innerMediaUriString;
    String outerMediaUriString;
    Integer backgroundColor;
    String title;
    String text;
    Page page;

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

        // initialize page
        page = new Page();

        if (!getActivity().getIntent().getBooleanExtra(EXTRA_IS_NEW_STORIES, true)) {
            addOuterMediaImageView.setVisibility(View.INVISIBLE);
            addInnerMediaImageView.setVisibility(View.INVISIBLE);
            removeOuterMediaImageView.setVisibility(View.VISIBLE);
            removeInnerMediaImageView.setVisibility(View.VISIBLE);
            tipEditText.setVisibility(View.INVISIBLE);

            // get page from bundle
            page = getArguments().getParcelable(BUNDLE_CURRENT_PAGE);

            // get data and put into views
            outerMediaUriString = page.getImageUris().get(0);
            innerMediaUriString = page.getImageUris().get(1);
            title = page.getTitle();
            text = page.getText();
            backgroundColor = Integer.valueOf(page.getColors().get(0));

            Uri outerImageUri = Uri.parse(outerMediaUriString);
            Uri innerImageUri = Uri.parse(innerMediaUriString);
            ImageHandler.setImageToImageView(getContext(), outerImageUri, outerMediaImageView, ImageView.ScaleType.CENTER_CROP);
            ImageHandler.setImageToImageView(getContext(), innerImageUri, innerMediaImageView, ImageView.ScaleType.CENTER_CROP);
            addTitleEditText.setText(title);
            tapToAddEditText.setText(text);
            containerConstraintLayout.setBackgroundColor(backgroundColor);
        }


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
            }
        });
        removeOuterMediaImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                outerMediaImageView.setImageBitmap(null);
                addOuterMediaImageView.setVisibility(View.VISIBLE);
                removeOuterMediaImageView.setVisibility(View.INVISIBLE);
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
            if (requestCode == IMAGE_GALLERY_REQUEST_OUTER) {
                outerMediaUriString = data.getDataString();
                ImageHandler.setImageToImageView(getContext(), imageUri, outerMediaImageView, ImageView.ScaleType.CENTER_CROP);
            } else if (requestCode == IMAGE_GALLERY_REQUEST_INNER) {
                innerMediaUriString = data.getDataString();
                ImageHandler.setImageToImageView(getContext(), imageUri, innerMediaImageView, ImageView.ScaleType.CENTER_CROP);
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
    public Page sendPage() {
        page.setTemplateName("free template 6");
        title = addTitleEditText.getText().toString();
        page.setTitle(title);
        text = tapToAddEditText.getText().toString();
        page.setText(text);
        // set array data
        ArrayList<String> imageUriStrings = new ArrayList<>();
        imageUriStrings.add(outerMediaUriString);
        imageUriStrings.add(innerMediaUriString);
        page.setImageUris(imageUriStrings);
        ArrayList<String> colors = new ArrayList<String>();
        if (backgroundColor == null) {
            backgroundColor = 0;
        }
        colors.add(backgroundColor.toString());
        page.setColors(colors);
        return page;
    }
}
