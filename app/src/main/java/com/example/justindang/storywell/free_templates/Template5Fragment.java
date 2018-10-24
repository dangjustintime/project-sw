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
    private static final String BUNDLE_CURRENT_PAGE = "current page";
    private static final String BUNDLE_IS_NEW_PAGE = "new page";

    // image uri strings, color, title, text
    String mediaUriString;
    Integer backgroundColor;
    String title;
    String text;
    Page page;

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

        // set default color
        backgroundColor = Integer.valueOf(getContext().getResources().getColor(R.color.colorPeach));

        hideUI();
        colorPickerImageView.setVisibility(View.VISIBLE);

        // initialize page
        page = new Page();

        if (!getArguments().getBoolean(BUNDLE_IS_NEW_PAGE)) {
            addMediaImageView.setVisibility(View.INVISIBLE);
            removeMediaImageView.setVisibility(View.VISIBLE);
            tipEditText.setVisibility(View.INVISIBLE);

            // get page from bundle
            page = getArguments().getParcelable(BUNDLE_CURRENT_PAGE);

            // get data and put into views
            mediaUriString = page.getImageUris().get(0);
            title = page.getTitle();
            text = page.getText();
            backgroundColor = Integer.valueOf(page.getColors().get(0));

            // get data and put into views
            Uri imageUri = Uri.parse(mediaUriString);
            ImageHandler.setImageToImageView(getContext(), imageUri, mediaImageView, ImageView.ScaleType.CENTER_CROP);
            addTitleEditText.setText(title);
            tapToAddEditText.setText(text);
            containerConstraintLayout.setBackgroundColor(backgroundColor);
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
                addMediaImageView.setVisibility(View.VISIBLE);
                removeMediaImageView.setVisibility(View.INVISIBLE);
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

    // OnSaveImageListener
    @Override
    public void hideUI() {
        removeMediaImageView.setVisibility(View.INVISIBLE);
        colorPickerImageView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void recieveColorFromColorPicker(int color) {
        backgroundColor = color;
        containerConstraintLayout.setBackgroundColor(backgroundColor);
    }

    @Override
    public Page sendPage() {
        page.setTemplateName("free template 5");
        title = addTitleEditText.getText().toString();
        page.setTitle(title);
        text = tapToAddEditText.getText().toString();
        page.setText(text);
        // set array data
        ArrayList<String> imageUriStrings = new ArrayList<>();
        imageUriStrings.add(mediaUriString);
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
