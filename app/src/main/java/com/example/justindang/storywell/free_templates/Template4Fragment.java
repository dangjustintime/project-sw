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

import com.example.justindang.storywell.R;
import com.example.justindang.storywell.activities.StoryEditorActivity;
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
    // request codes
    private static final int IMAGE_GALLERY_REQUEST_TOP = 40;
    private static final int IMAGE_GALLERY_REQUEST_BOTTOM = 48;

    // tags
    private static final String EXTRA_IS_NEW_STORIES = "new stories";
    private static final String EXTRA_SAVED_STORIES = "saved stories";

    // image uri strings
    // index 0 = inner Media
    // index 1 = outer Media
    ArrayList<String> imageUriStrings;
    String bottomMediaUriString;
    String topMediaUriString;

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

        // initalize filePaths
        imageUriStrings = new ArrayList<>();

        if (!getActivity().getIntent().getBooleanExtra(EXTRA_IS_NEW_STORIES, true)) {
            addTopMediaImageView.setVisibility(View.INVISIBLE);
            addBottomMediaImageView.setVisibility(View.INVISIBLE);
            removeTopMediaImageView.setVisibility(View.VISIBLE);
            removeBottomMediaImageView.setVisibility(View.VISIBLE);

            // get data and put into views
            Stories savedStories = getActivity().getIntent().getParcelableExtra(EXTRA_SAVED_STORIES);
            topMediaUriString = savedStories.getImageUris(0).get(0);
            bottomMediaUriString = savedStories.getImageUris(0).get(1);
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
                imageUriStrings.remove(topMediaUriString);
            }
        });
        removeBottomMediaImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomMediaImageView.setImageBitmap(null);
                removeBottomMediaImageView.setVisibility(View.INVISIBLE);
                addBottomMediaImageView.setVisibility(View.VISIBLE);
                imageUriStrings.remove(bottomMediaUriString);
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
    public ArrayList<String> sendFilePaths() {
        imageUriStrings.add(topMediaUriString);
        imageUriStrings.add(bottomMediaUriString);
        return imageUriStrings;
    }

    @Override
    public ArrayList<String> sendColors() {
        ArrayList<String> colors = new ArrayList<String>();
        colors.add("0");
        return colors;
    }

    @Override
    public String sendTitle() {
        return null;
    }

    @Override
    public String sendText() {
        return null;
    }
}
