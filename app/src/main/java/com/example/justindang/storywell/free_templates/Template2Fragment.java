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

    // file paths
    ArrayList<String> filePaths;
    String innerMediaFilePath;
    Integer outerLayerColor;

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
        filePaths = new ArrayList<>();

        hideUI();
        colorPickerImageView.setVisibility(View.VISIBLE);


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
                Intent photoGalleryIntent = new Intent(Intent.ACTION_PICK);
                File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                innerMediaFilePath = pictureDirectory.getPath();
                Uri data = Uri.parse(innerMediaFilePath);
                photoGalleryIntent.setDataAndType(data, "image/*");
                startActivityForResult(photoGalleryIntent, IMAGE_GALLERY_REQUEST_INNER);
                removeInnerMediaImageView.setVisibility(View.VISIBLE);
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
                filePaths.remove(innerMediaFilePath);
            }
        });

        return view;
    }

    // return selected image from gallery
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == IMAGE_GALLERY_REQUEST_INNER) {
                filePaths.add(data.getData().toString());
                ImageHandler.setImageToImageView(getContext(), data, innerMediaImageView, ImageView.ScaleType.FIT_CENTER);
            }
        }
    }

    @Override
    public void hideUI() {
        colorPicker.setVisibility(View.INVISIBLE);
        removeInnerMediaImageView.setVisibility(View.INVISIBLE);
        colorPickerImageView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showUI() {

    }

    @Override
    public ArrayList<String> sendFilePaths() {
        return filePaths;
    }

    @Override
    public ArrayList<Integer> sendColors() {
        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.add(outerLayerColor);
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
