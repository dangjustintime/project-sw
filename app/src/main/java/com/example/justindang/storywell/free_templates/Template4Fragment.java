package com.example.justindang.storywell.free_templates;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

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

public class Template4Fragment extends Fragment implements StoryEditorActivity.OnSaveImageListener {
    // static data
    private static final String BUNDLE_CURRENT_PAGE = "current page";
    private static final String BUNDLE_IS_NEW_PAGE = "new page";
    private static final int IMAGE_GALLERY_REQUEST_TOP = 40;
    private static final int IMAGE_GALLERY_REQUEST_BOTTOM = 48;

    // image uri strings
    // index 0 = top Media
    // index 1 = bottom Media
    String bottomMediaUriString;
    String topMediaUriString;
    Page page;

    // views
    @BindView(R.id.image_view_template4_top_media) ImageView topMediaImageView;
    @BindView(R.id.image_view_template4_bottom_media) ImageView bottomMediaImageView;
    @BindView(R.id.image_view_template4_add_top_media) ImageView addTopMediaImageView;
    @BindView(R.id.image_view_template4_add_bottom_media) ImageView addBottomMediaImageView;
    @BindView(R.id.image_view_template4_remove_top_media) ImageView removeTopMediaImageView;
    @BindView(R.id.image_view_template4_remove_bottom_media) ImageView removeBottomMediaImageView;

    public Template4Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_template4, container, false);
        ButterKnife.bind(this, view);

        hideUI();

        // initialize page
        page = new Page();

        if (!getArguments().getBoolean(BUNDLE_IS_NEW_PAGE)) {
            addTopMediaImageView.setVisibility(View.INVISIBLE);
            addBottomMediaImageView.setVisibility(View.INVISIBLE);
            removeTopMediaImageView.setVisibility(View.VISIBLE);
            removeBottomMediaImageView.setVisibility(View.VISIBLE);

            // get page from bundle
            page = getArguments().getParcelable(BUNDLE_CURRENT_PAGE);

            // get data and put into views
            topMediaUriString = page.getImageUris().get(0);
            bottomMediaUriString = page.getImageUris().get(1);
            Uri topImageUri = Uri.parse(topMediaUriString);
            Uri bottomImageUri = Uri.parse(bottomMediaUriString);
            ImageHandler.setImageToImageView(getContext(), topImageUri, topMediaImageView, ImageView.ScaleType.CENTER_CROP);
            ImageHandler.setImageToImageView(getContext(), bottomImageUri, bottomMediaImageView, ImageView.ScaleType.CENTER_CROP);
        }

        // clicklisteners
        addTopMediaImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTopMediaImageView.setVisibility(View.INVISIBLE);
                removeTopMediaImageView.setVisibility(View.VISIBLE);
                Intent photoGalleryIntent = ImageHandler.createPhotoGalleryIntent();
                startActivityForResult(photoGalleryIntent, IMAGE_GALLERY_REQUEST_TOP);
            }
        });
        addBottomMediaImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBottomMediaImageView.setVisibility(View.INVISIBLE);
                removeBottomMediaImageView.setVisibility(View.VISIBLE);
                Intent photoGalleryIntent = ImageHandler.createPhotoGalleryIntent();
                startActivityForResult(photoGalleryIntent, IMAGE_GALLERY_REQUEST_BOTTOM);
            }
        });
        removeTopMediaImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                topMediaImageView.setImageBitmap(null);
                removeTopMediaImageView.setVisibility(View.INVISIBLE);
                addTopMediaImageView.setVisibility(View.VISIBLE);
            }
        });
        removeBottomMediaImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomMediaImageView.setImageBitmap(null);
                removeBottomMediaImageView.setVisibility(View.INVISIBLE);
                addBottomMediaImageView.setVisibility(View.VISIBLE);
            }
        });

        return view;
    }

    // return selected image from gallery
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            if (requestCode == IMAGE_GALLERY_REQUEST_TOP) {
                topMediaUriString = data.getDataString();
                ImageHandler.setImageToImageView(getContext(), imageUri, topMediaImageView, ImageView.ScaleType.CENTER_CROP);
            } else if (requestCode == IMAGE_GALLERY_REQUEST_BOTTOM) {
                bottomMediaUriString = data.getDataString();
                ImageHandler.setImageToImageView(getContext(), imageUri, bottomMediaImageView, ImageView.ScaleType.CENTER_CROP);
            }
        }
    }

    @Override
    public void hideUI() {
        removeTopMediaImageView.setVisibility(View.INVISIBLE);
        removeBottomMediaImageView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void receiveColorFromColorPicker(int color) {
        Toast.makeText(getContext(), "no color", Toast.LENGTH_SHORT).show();
    }
}
