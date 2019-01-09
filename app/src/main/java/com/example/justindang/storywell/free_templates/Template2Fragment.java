package com.example.justindang.storywell.free_templates;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.colorpicker.shishank.colorpicker.ColorPicker;
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

public class Template2Fragment extends Fragment implements StoryEditorActivity.OnSaveImageListener {

    // static data
    private static final String BUNDLE_CURRENT_PAGE = "current page";
    private static final String BUNDLE_IS_NEW_PAGE = "new page";
    private static final int IMAGE_GALLERY_REQUEST_INNER = 27;

    // view model
    StoriesViewModel storiesViewModel;

    // image uri and color strings
    String innerMediaUriString;
    Integer outerLayerColor;
    Page page;

    // views
    @BindView(R.id.image_view_template2_inner_media) ImageView innerMediaImageView;
    @BindView(R.id.image_view_template2_outer_layer) ImageView outerLayerImageView;
    @BindView(R.id.image_view_template2_add_inner_media) ImageView addInnerMediaImageView;
    @BindView(R.id.image_view_template2_color_picker_outer_layer) ImageView colorPickerImageView;
    @BindView(R.id.image_view_template2_remove_inner_media) ImageView removeInnerMediaImageView;

    public Template2Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_template2, container, false);
        ButterKnife.bind(this, view);

        // initialize page
        page = new Page();

        // view model
        storiesViewModel = ViewModelProviders.of(getActivity()).get(StoriesViewModel.class);
        storiesViewModel.getStories().observe(this, new Observer<Stories>() {
            @Override
            public void onChanged(@Nullable Stories stories) {
                // Toast.makeText(getContext(), stories.toString(), Toast.LENGTH_SHORT).show();
                if (stories.getColors(0).size() != 0) {
                    outerLayerColor = Integer.parseInt(stories.getPage(0).getColors().get(0));
                    outerLayerImageView.setBackgroundColor(outerLayerColor);
                }
            }
        });

        hideUI();
        colorPickerImageView.setVisibility(View.VISIBLE);

        // load previously saved page
        if (!getArguments().getBoolean(BUNDLE_IS_NEW_PAGE)) {
            addInnerMediaImageView.setVisibility(View.INVISIBLE);
            removeInnerMediaImageView.setVisibility(View.VISIBLE);

            // get page from bundle
            page = getArguments().getParcelable(BUNDLE_CURRENT_PAGE);

            // put images into image views
            outerLayerColor = Integer.valueOf(page.getColors().get(0));
            outerLayerImageView.setBackgroundColor(outerLayerColor);
            innerMediaUriString = page.getImageUris().get(0);
            Uri innerImageUri = Uri.parse(innerMediaUriString);
            ImageHandler.setImageToImageView(getContext(), innerImageUri, innerMediaImageView, ImageView.ScaleType.CENTER_CROP);

        }

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
        removeInnerMediaImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                innerMediaImageView.setImageBitmap(null);
                addInnerMediaImageView.setVisibility(View.VISIBLE);
                removeInnerMediaImageView.setVisibility(View.INVISIBLE);
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
                ImageHandler.setImageToImageView(getContext(), imageUri, innerMediaImageView, ImageView.ScaleType.CENTER_CROP);
            }
        }
    }

    // OnSaveImageListener
    @Override
    public void hideUI() {
        removeInnerMediaImageView.setVisibility(View.INVISIBLE);
        colorPickerImageView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void receiveColorFromColorPicker(int color) {
        // outerLayerColor = color;
        // outerLayerImageView.setBackgroundColor(outerLayerColor);
    }

    @Override
    public Page sendPage() {
        page.setTemplateName("free template 2");
        page.setTitle(null);
        page.setText(null);
        // set array data
        ArrayList<String> imageUriStrings = new ArrayList<>();
        imageUriStrings.add(innerMediaUriString);
        page.setImageUris(imageUriStrings);
        ArrayList<String> colors = new ArrayList<String>();
        if (outerLayerColor == null) {
            outerLayerColor = 0;
        }
        colors.add(outerLayerColor.toString());
        page.setColors(colors);
        return page;
    }
}
