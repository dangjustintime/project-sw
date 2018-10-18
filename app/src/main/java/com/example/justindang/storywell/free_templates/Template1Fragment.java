package com.example.justindang.storywell.free_templates;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.icu.text.UnicodeSetSpanner;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
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

public class Template1Fragment extends Fragment implements StoryEditorActivity.OnSaveImageListener {

    // uri strings
    // index 0 = inner Media
    // index 1 = outer Media
    String innerMediaUriString;
    String outerMediaUriString;
    Page page;

    // tags
    private static final String EXTRA_IS_NEW_STORIES = "new stories";
    private static final String BUNDLE_CURRENT_PAGE = "current page";

    // request codes
    private static final int IMAGE_GALLERY_REQUEST_OUTER = 20;
    private static final int IMAGE_GALLERY_REQUEST_INNER = 21;

    // views
    @BindView(R.id.image_view_template1_inner_media) ImageView innerMediaImageView;
    @BindView(R.id.image_view_template1_outer_media) ImageView outerMediaImageView;
    @BindView(R.id.image_view_template1_add_inner_media) ImageView addInnerMediaImageView;
    @BindView(R.id.image_view_template1_add_outer_media) ImageView addOuterMediaImageView;
    @BindView(R.id.image_view_template1_remove_inner_media) ImageView removeInnerMediaImageView;
    @BindView(R.id.image_view_template1_remove_outer_media) ImageView removeOuterMediaImageView;

    // ScaleGestureDetector
    private ScaleGestureDetector scaleGestureDetector;
    private float scaleFactor = 1.5f;
    private Matrix outerMediaMatrix = new Matrix();

    public Template1Fragment() {
        // Required empty public constructor
    }

    // scalelistener
    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            Drawable imageDrawable = outerMediaImageView.getDrawable();
            float offsetX = (outerMediaImageView.getWidth() - imageDrawable.getIntrinsicWidth()) / 2f;
            float offsetY = (outerMediaImageView.getHeight() - imageDrawable.getIntrinsicHeight()) / 2f;
            float centerX = outerMediaImageView.getWidth() / 2f;
            float centerY = outerMediaImageView.getHeight() / 2f;
            scaleFactor *= scaleGestureDetector.getScaleFactor();
            scaleFactor = Math.max(1f, Math.min(scaleFactor, 2f));
            outerMediaMatrix.setScale(scaleFactor, scaleFactor, centerX, centerY);
            outerMediaMatrix.preTranslate(offsetX, offsetY);
            outerMediaImageView.setImageMatrix(outerMediaMatrix);
            return true;
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_template1, container, false);
        ButterKnife.bind(this, view);

        hideUI();

        // initialize page
        page = new Page();

        // load previously saved page
        if (!getActivity().getIntent().getBooleanExtra(EXTRA_IS_NEW_STORIES, true)) {
            addOuterMediaImageView.setVisibility(View.INVISIBLE);
            removeOuterMediaImageView.setVisibility(View.VISIBLE);
            addInnerMediaImageView.setVisibility(View.INVISIBLE);
            removeInnerMediaImageView.setVisibility(View.VISIBLE);

            // get page from bundle
            page = getArguments().getParcelable(BUNDLE_CURRENT_PAGE);

            // put images into image views
            innerMediaUriString = page.getImageUris().get(0);
            outerMediaUriString = page.getImageUris().get(1);
            Uri innerImageUri = Uri.parse(innerMediaUriString);
            Uri outerImageUri = Uri.parse(outerMediaUriString);
            ImageHandler.setImageToImageView(getContext(), innerImageUri, innerMediaImageView, ImageView.ScaleType.CENTER_CROP);
            ImageHandler.setImageToImageView(getContext(), outerImageUri, outerMediaImageView, ImageView.ScaleType.MATRIX);
        }

        // gesture listener
        scaleGestureDetector = new ScaleGestureDetector(getContext(), new ScaleListener());

        // clicklisteners
        addOuterMediaImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addOuterMediaImageView.setVisibility(View.INVISIBLE);
                removeOuterMediaImageView.setVisibility(View.VISIBLE);
                Intent photoGalleryIntent = ImageHandler.createPhotoGalleryIntent();
                startActivityForResult(photoGalleryIntent, IMAGE_GALLERY_REQUEST_OUTER);
            }

        });
        addInnerMediaImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addInnerMediaImageView.setVisibility(View.INVISIBLE);
                removeInnerMediaImageView.setVisibility(View.VISIBLE);
                Intent photoGalleryIntent = ImageHandler.createPhotoGalleryIntent();
                startActivityForResult(photoGalleryIntent, IMAGE_GALLERY_REQUEST_INNER);
            }
        });
        removeInnerMediaImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                innerMediaImageView.setImageBitmap(null);
                addInnerMediaImageView.setVisibility(View.VISIBLE);
                removeInnerMediaImageView.setVisibility(View.INVISIBLE);
            }
        });
        removeOuterMediaImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                outerMediaImageView.setImageBitmap(null);
                addOuterMediaImageView.setVisibility(View.VISIBLE);
                removeOuterMediaImageView.setVisibility(View.INVISIBLE);
            }
        });

        // touchlisteners
        outerMediaImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                scaleGestureDetector.onTouchEvent(event);
                return true;
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
                ImageHandler.setImageToImageView(getContext(), imageUri, outerMediaImageView, ImageView.ScaleType.MATRIX);
            } else if (requestCode == IMAGE_GALLERY_REQUEST_INNER) {
                innerMediaUriString = data.getDataString();
                ImageHandler.setImageToImageView(getContext(), imageUri, innerMediaImageView, ImageView.ScaleType.CENTER_CROP);
            }
        }
    }

    // OnSaveImageListener
    @Override
    public void hideUI() {
        removeInnerMediaImageView.setVisibility(View.INVISIBLE);
        removeOuterMediaImageView.setVisibility(View.INVISIBLE);
    }

    @Override
    public ArrayList<String> sendFilePaths() {
        ArrayList<String> imageUriStrings = new ArrayList<String>();
        imageUriStrings.add(innerMediaUriString);
        imageUriStrings.add(outerMediaUriString);
        page.setImageUris(imageUriStrings);
        return imageUriStrings;
    }

    @Override
    public ArrayList<String> sendColors() {
        ArrayList<String> colors = new ArrayList<String>();
        colors.add("0");
        page.setColors(colors);
        return colors;
    }

    @Override
    public String sendTitle() {
        page.setTitle(null);
        return null;
    }

    @Override
    public String sendText() {
        page.setText(null);
        return null;
    }

    @Override
    public Page sendPage() {
        page.setTitle(null);
        page.setText(null);
        // set array data
        ArrayList<String> imageUriStrings = new ArrayList<>();
        imageUriStrings.add(innerMediaUriString);
        imageUriStrings.add(outerMediaUriString);
        page.setImageUris(imageUriStrings);
        ArrayList<String> colors = new ArrayList<String>();
        colors.add("0");
        page.setColors(colors);
        return page;
    }
}
