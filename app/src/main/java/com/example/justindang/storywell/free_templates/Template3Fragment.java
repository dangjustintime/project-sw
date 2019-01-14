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

public class Template3Fragment extends Fragment implements StoryEditorActivity.OnSaveImageListener {
    // static data
    private static final String BUNDLE_CURRENT_PAGE = "current page";
    private static final String BUNDLE_IS_NEW_PAGE = "new page";
    private static final int IMAGE_GALLERY_REQUEST_TOP = 29;
    private static final int IMAGE_GALLERY_REQUEST_BOTTOM = 28;

    // image uri strings
    // index 0 = top media
    // index 1 = bottom media
    String bottomMediaUriString;
    String topMediaUriString;
    Page page;

    // views
    @BindView(R.id.image_view_template3_add_bottom_media) ImageView addBottomMediaImageView;
    @BindView(R.id.image_view_template3_add_top_media) ImageView addTopMediaImageView;
    @BindView(R.id.image_view_template3_bottom_media) ImageView bottomMediaImageView;
    @BindView(R.id.image_view_template3_top_media) ImageView topMediaImageView;
    @BindView(R.id.image_view_template3_remove_bottom_media) ImageView removeBottomMediaImageView;
    @BindView(R.id.image_view_template3_remove_top_media) ImageView removeTopMediaImageView;

    public Template3Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_template3, container, false);
        ButterKnife.bind(this, view);

        hideUI();

        // initialize page
        page = new Page();

        if (!getArguments().getBoolean(BUNDLE_IS_NEW_PAGE)) {
            addBottomMediaImageView.setVisibility(View.INVISIBLE);
            addTopMediaImageView.setVisibility(View.INVISIBLE);
            removeBottomMediaImageView.setVisibility(View.VISIBLE);
            removeTopMediaImageView.setVisibility(View.VISIBLE);

            // get page from bundle
            page = getArguments().getParcelable(BUNDLE_CURRENT_PAGE);

            // get saved stories and put data into views
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
                addTopMediaImageView.setVisibility(View.VISIBLE);
                removeTopMediaImageView.setVisibility(View.INVISIBLE);
            }
        });
        removeBottomMediaImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomMediaImageView.setImageBitmap(null);
                addBottomMediaImageView.setVisibility(View.VISIBLE);
                removeBottomMediaImageView.setVisibility(View.INVISIBLE);
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

    // OnSaveImageListener
    @Override
    public void hideUI() {
        removeBottomMediaImageView.setVisibility(View.INVISIBLE);
        removeTopMediaImageView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void receiveColorFromColorPicker(int color) {
        Toast.makeText(getContext(), "no color", Toast.LENGTH_SHORT).show();
    }
}
