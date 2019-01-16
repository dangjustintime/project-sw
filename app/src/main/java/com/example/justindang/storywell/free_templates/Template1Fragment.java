package com.example.justindang.storywell.free_templates;


import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
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
import com.example.justindang.storywell.view_model.StoriesViewModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;

public class Template1Fragment extends Fragment implements StoryEditorActivity.OnSaveImageListener {

    // static data
    private static final String BUNDLE_IS_NEW_PAGE = "new page";
    private static final int IMAGE_GALLERY_REQUEST_OUTER = 20;
    private static final int IMAGE_GALLERY_REQUEST_INNER = 21;

    // view model
    StoriesViewModel storiesViewModel;

    // uri strings
    // index 0 = inner Media
    // index 1 = outer Media
    String innerMediaUriString;
    String outerMediaUriString;

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

    // scalelistener
    private class OnScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
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

    public Template1Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_template1, container, false);
        ButterKnife.bind(this, view);

        hideUI();

        // instantiate view model
        storiesViewModel = ViewModelProviders.of(getActivity()).get(StoriesViewModel.class);

        // load previously saved page
        if (!getArguments().getBoolean(BUNDLE_IS_NEW_PAGE)) {
            innerMediaUriString = storiesViewModel.getStories().getValue().getImageUris().get(0);
            outerMediaUriString = storiesViewModel.getStories().getValue().getImageUris().get(1);
            Toast.makeText(getContext(), outerMediaUriString, Toast.LENGTH_SHORT).show();


            if (innerMediaUriString.equals("") || innerMediaUriString.equals("NOT FOUND")) {
                addInnerMediaImageView.setVisibility(View.VISIBLE);
                removeInnerMediaImageView.setVisibility(View.INVISIBLE);
            } else {
                addInnerMediaImageView.setVisibility(View.INVISIBLE);
                removeInnerMediaImageView.setVisibility(View.VISIBLE);
                Uri innerImageUri = Uri.parse(innerMediaUriString);
                ImageHandler.setImageToImageView(getContext(), innerImageUri, innerMediaImageView, ImageView.ScaleType.CENTER_CROP);
            }

            if (outerMediaUriString.equals("") || outerMediaUriString.equals("NOT FOUND")) {
                addOuterMediaImageView.setVisibility(View.VISIBLE);
                removeOuterMediaImageView.setVisibility(View.INVISIBLE);
            } else {
                addOuterMediaImageView.setVisibility(View.INVISIBLE);
                removeOuterMediaImageView.setVisibility(View.VISIBLE);
                Uri outerImageUri = Uri.parse(outerMediaUriString);
                ImageHandler.setImageToImageView(getContext(), outerImageUri, outerMediaImageView, ImageView.ScaleType.MATRIX);
            }
        }

        // gesture listener
        scaleGestureDetector = new ScaleGestureDetector(getContext(), new OnScaleListener());

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
                innerMediaUriString = "";
                addInnerMediaImageView.setVisibility(View.VISIBLE);
                removeInnerMediaImageView.setVisibility(View.INVISIBLE);
                updateViewModel();
            }
        });
        removeOuterMediaImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                outerMediaImageView.setImageBitmap(null);
                outerMediaUriString = "";
                addOuterMediaImageView.setVisibility(View.VISIBLE);
                removeOuterMediaImageView.setVisibility(View.INVISIBLE);
                updateViewModel();
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
            updateViewModel();
        }
    }

    // OnSaveImageListener
    @Override
    public void hideUI() {
        removeInnerMediaImageView.setVisibility(View.INVISIBLE);
        removeOuterMediaImageView.setVisibility(View.INVISIBLE);
    }

    // update data for view model
    private void updateViewModel() {
        ArrayList<String> updatedImageUris = new ArrayList<>();
        updatedImageUris.add(innerMediaUriString);
        updatedImageUris.add(outerMediaUriString);
        Stories updatedStories = new Stories(storiesViewModel.getStories().getValue());
        updatedStories.setImageUris(updatedImageUris);
        storiesViewModel.setStories(updatedStories);
    }
}
