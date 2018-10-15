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
import com.example.justindang.storywell.utilities.ImageHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;

public class Template3Fragment extends Fragment implements StoryEditorActivity.OnSaveImageListener {

    // request codes
    private static final int IMAGE_GALLERY_REQUEST_TOP = 29;
    private static final int IMAGE_GALLERY_REQUEST_BOTTOM = 28;

    // image uri strings
    ArrayList<String> imageUriStrings;
    String bottomMediaUriString;
    String topMediaUriString;

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

        // initialize filePaths
        imageUriStrings = new ArrayList<>();

        hideUI();

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
                imageUriStrings.remove(topMediaUriString);
            }
        });
        removeBottomMediaImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomMediaImageView.setImageBitmap(null);
                addBottomMediaImageView.setVisibility(View.VISIBLE);
                removeBottomMediaImageView.setVisibility(View.INVISIBLE);
                imageUriStrings.remove(bottomMediaUriString);
            }
        });

        return view;
    }

    // return selected image from gallery
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == IMAGE_GALLERY_REQUEST_TOP) {
                topMediaUriString = data.getDataString();
                imageUriStrings.add(topMediaUriString);
                ImageHandler.setImageToImageView(getContext(), data, topMediaImageView, ImageView.ScaleType.CENTER_CROP);
            } else if (requestCode == IMAGE_GALLERY_REQUEST_BOTTOM) {
                bottomMediaUriString = data.getDataString();
                imageUriStrings.add(bottomMediaUriString);
                ImageHandler.setImageToImageView(getContext(), data, bottomMediaImageView, ImageView.ScaleType.CENTER_CROP);
            }
        }
    }

    @Override
    public void hideUI() {
        removeBottomMediaImageView.setVisibility(View.INVISIBLE);
        removeTopMediaImageView.setVisibility(View.INVISIBLE);
    }

    @Override
    public ArrayList<String> sendFilePaths() {
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
