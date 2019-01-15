package com.example.justindang.storywell.free_templates;


import android.arch.lifecycle.ViewModelProviders;
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
import com.example.justindang.storywell.view_model.StoriesViewModel;

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

    // view model
    StoriesViewModel storiesViewModel;

    // image uri strings
    // index 0 = top Media
    // index 1 = bottom Media
    String topMediaUriString;
    String bottomMediaUriString;

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

        // view model
        storiesViewModel = ViewModelProviders.of(getActivity()).get(StoriesViewModel.class);

        // load previously saved page
        if (!getArguments().getBoolean(BUNDLE_IS_NEW_PAGE)) {
            topMediaUriString = storiesViewModel.getStories().getValue().getImageUris().get(0);
            bottomMediaUriString = storiesViewModel.getStories().getValue().getImageUris().get(1);

            if (topMediaUriString.equals("")) {
                addTopMediaImageView.setVisibility(View.VISIBLE);
                removeTopMediaImageView.setVisibility(View.INVISIBLE);
            } else {
                addTopMediaImageView.setVisibility(View.INVISIBLE);
                removeTopMediaImageView.setVisibility(View.VISIBLE);
                Uri topImageUri = Uri.parse(topMediaUriString);
                ImageHandler.setImageToImageView(getContext(), topImageUri, topMediaImageView, ImageView.ScaleType.CENTER_CROP);

            }

            if (bottomMediaUriString.equals("")) {
                addBottomMediaImageView.setVisibility(View.VISIBLE);
                removeBottomMediaImageView.setVisibility(View.INVISIBLE);
            } else {
                addBottomMediaImageView.setVisibility(View.INVISIBLE);
                removeBottomMediaImageView.setVisibility(View.VISIBLE);
                Uri bottomImageUri = Uri.parse(bottomMediaUriString);
                ImageHandler.setImageToImageView(getContext(), bottomImageUri, bottomMediaImageView, ImageView.ScaleType.CENTER_CROP);
            }
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
                topMediaUriString = "";
                removeTopMediaImageView.setVisibility(View.INVISIBLE);
                addTopMediaImageView.setVisibility(View.VISIBLE);
                updateViewModel();
            }
        });
        removeBottomMediaImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomMediaImageView.setImageBitmap(null);
                bottomMediaUriString = "";
                removeBottomMediaImageView.setVisibility(View.INVISIBLE);
                addBottomMediaImageView.setVisibility(View.VISIBLE);
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
            if (requestCode == IMAGE_GALLERY_REQUEST_TOP) {
                topMediaUriString = data.getDataString();
                ImageHandler.setImageToImageView(getContext(), imageUri, topMediaImageView, ImageView.ScaleType.CENTER_CROP);
            } else if (requestCode == IMAGE_GALLERY_REQUEST_BOTTOM) {
                bottomMediaUriString = data.getDataString();
                ImageHandler.setImageToImageView(getContext(), imageUri, bottomMediaImageView, ImageView.ScaleType.CENTER_CROP);
            }
            updateViewModel();
        }
    }

    @Override
    public void hideUI() {
        removeTopMediaImageView.setVisibility(View.INVISIBLE);
        removeBottomMediaImageView.setVisibility(View.INVISIBLE);
    }

    // update data for view model
    private void updateViewModel() {
        ArrayList<String> updatedImageUris = new ArrayList<>();
        updatedImageUris.add(topMediaUriString);
        updatedImageUris.add(bottomMediaUriString);
        Stories updatedStories = new Stories(storiesViewModel.getStories().getValue());
        updatedStories.setImageUris(updatedImageUris);
        storiesViewModel.setStories(updatedStories);
    }
}
