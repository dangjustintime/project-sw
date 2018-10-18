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

public class Template2Fragment extends Fragment implements StoryEditorActivity.OnSaveImageListener {

    // request code
    private static final int IMAGE_GALLERY_REQUEST_INNER = 27;

    // tags
    private static final String EXTRA_IS_NEW_STORIES = "new stories";
    private static final String EXTRA_SAVED_STORIES = "saved stories";

    // image uri and color strings
    ArrayList<String> imageUriStrings;
    String innerMediaUriString;
    Integer outerLayerColor;
    Page page;

    // views
    @BindView(R.id.image_view_template2_inner_media) ImageView innerMediaImageView;
    @BindView(R.id.image_view_template2_outer_layer) ImageView outerLayerImageView;
    @BindView(R.id.image_view_template2_add_inner_media) ImageView addInnerMediaImageView;
    @BindView(R.id.image_view_template2_color_picker_outer_layer) ImageView colorPickerImageView;
    @BindView(R.id.image_view_template2_remove_inner_media) ImageView removeInnerMediaImageView;
    @BindView(R.id.color_picker_template2_outer_later) ColorPicker colorPicker;

    public Template2Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_template2, container, false);
        ButterKnife.bind(this, view);

        // initialize filePaths
        imageUriStrings = new ArrayList<>();

        hideUI();
        colorPickerImageView.setVisibility(View.VISIBLE);

        if (!getActivity().getIntent().getBooleanExtra(EXTRA_IS_NEW_STORIES, true)) {
            addInnerMediaImageView.setVisibility(View.INVISIBLE);
            removeInnerMediaImageView.setVisibility(View.VISIBLE);

            // get values of saved stories
            Stories savedStories = getActivity().getIntent().getParcelableExtra(EXTRA_SAVED_STORIES);
            outerLayerColor = Integer.valueOf(savedStories.getColors(0).get(0));
            outerLayerImageView.setBackgroundColor(outerLayerColor);
            innerMediaUriString = savedStories.getImageUris(0).get(0);
            Uri innerImageUri = Uri.parse(innerMediaUriString);
            ImageHandler.setImageToImageView(getContext(), innerImageUri, innerMediaImageView, ImageView.ScaleType.CENTER_CROP);

        }

        // color picker
        colorPicker.setGradientView(R.drawable.color_gradient);
        colorPicker.setColorSelectedListener(new ColorPicker.ColorSelectedListener() {
            @Override
            public void onColorSelected(int color, boolean isTapUp) {
                    outerLayerColor = color;
                    outerLayerImageView.setBackgroundColor(outerLayerColor);
            }
        });

        // clicklisteners
        addInnerMediaImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addInnerMediaImageView.setVisibility(View.INVISIBLE);
                removeInnerMediaImageView.setVisibility(View.VISIBLE);
                Intent photoGalleryIntent = ImageHandler.createPhotoGalleryIntent();
                startActivityForResult(photoGalleryIntent, IMAGE_GALLERY_REQUEST_INNER);
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
        removeInnerMediaImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                innerMediaImageView.setImageBitmap(null);
                addInnerMediaImageView.setVisibility(View.VISIBLE);
                removeInnerMediaImageView.setVisibility(View.INVISIBLE);
                imageUriStrings.remove(innerMediaUriString);
            }
        });

        return view;
    }

    // return selected image from gallery
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            if (requestCode == IMAGE_GALLERY_REQUEST_INNER) {
                innerMediaUriString = data.getDataString();
                ImageHandler.setImageToImageView(getContext(), imageUri, innerMediaImageView, ImageView.ScaleType.FIT_CENTER);
            }
        }
    }

    // OnSaveImageListener
    @Override
    public void hideUI() {
        colorPicker.setVisibility(View.INVISIBLE);
        removeInnerMediaImageView.setVisibility(View.INVISIBLE);
        colorPickerImageView.setVisibility(View.INVISIBLE);
    }

    @Override
    public ArrayList<String> sendFilePaths() {
        imageUriStrings.add(innerMediaUriString);
        return imageUriStrings;
    }

    @Override
    public ArrayList<String> sendColors() {
        ArrayList<String> colors = new ArrayList<String>();
        if (outerLayerColor == null) {
            outerLayerColor = 0;
        }
        colors.add(outerLayerColor.toString());
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

    @Override
    public Page sendPage() {
        return null;
    }
}
