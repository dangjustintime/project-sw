package com.example.justindang.storywell;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.colorpicker.shishank.colorpicker.ColorPicker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;


public class Template6Fragment extends Fragment implements StoryEditorActivity.OnSaveImageListener {
    // request codes
    private static final int IMAGE_GALLERY_REQUEST_OUTER = 19;
    private static final int IMAGE_GALLERY_REQUEST_INNER = 25;

    // views
    @BindView(R.id.edit_text_template6_add_title) EditText addTitleEditText;
    @BindView(R.id.edit_text_template6_tap_to_add) EditText tapToAddEditText;
    @BindView(R.id.edit_text_template6_tip) EditText tipEditText;
    @BindView(R.id.image_view_template6_outer_media) ImageView outerMediaImageView;
    @BindView(R.id.image_view_template6_inner_media) ImageView innerMediaImageView;
    @BindView(R.id.image_view_template6_add_outer_media) ImageView addOuterMediaImageView;
    @BindView(R.id.image_view_template6_add_inner_media) ImageView addInnerMediaImageView;
    @BindView(R.id.image_view_template6_remove_outer_media) ImageView removeOuterMediaImageView;
    @BindView(R.id.image_view_template6_remove_inner_media) ImageView removeInnerMediaImageView;
    @BindView(R.id.image_view_template6_color_picker) ImageView colorPickerImageView;
    @BindView(R.id.color_picker_template6) ColorPicker colorPicker;
    @BindView(R.id.constraint_layout_template6_container) ConstraintLayout containerConstraintLayout;

    public Template6Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_template6, container, false);
        ButterKnife.bind(this, view);

        removeOuterMediaImageView.setVisibility(View.INVISIBLE);
        removeInnerMediaImageView.setVisibility(View.INVISIBLE);
        colorPicker.setVisibility(View.INVISIBLE);

        // color picker
        colorPicker.setGradientView(R.drawable.color_gradient);
        colorPicker.setColorSelectedListener(new ColorPicker.ColorSelectedListener() {
            @Override
            public void onColorSelected(int color, boolean isTapUp) {
                containerConstraintLayout.setBackgroundColor(color);
            }
        });

        // clicklisteners
        addOuterMediaImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addOuterMediaImageView.setVisibility(View.INVISIBLE);
                Intent photoGalleryIntent = new Intent(Intent.ACTION_PICK);
                File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                String pictureDirectoryPath = pictureDirectory.getPath();
                Uri data = Uri.parse(pictureDirectoryPath);
                photoGalleryIntent.setDataAndType(data, "image/*");
                startActivityForResult(photoGalleryIntent, IMAGE_GALLERY_REQUEST_OUTER);
                removeOuterMediaImageView.setVisibility(View.VISIBLE);
            }
        });
        addInnerMediaImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addInnerMediaImageView.setVisibility(View.INVISIBLE);
                Intent photoGalleryIntent = new Intent(Intent.ACTION_PICK);
                File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                String pictureDirectoryPath = pictureDirectory.getPath();
                Uri data = Uri.parse(pictureDirectoryPath);
                photoGalleryIntent.setDataAndType(data, "image/*");
                startActivityForResult(photoGalleryIntent, IMAGE_GALLERY_REQUEST_INNER);
                removeInnerMediaImageView.setVisibility(View.VISIBLE);
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
        removeOuterMediaImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                outerMediaImageView.setImageBitmap(null);
                addOuterMediaImageView.setVisibility(View.VISIBLE);
                removeOuterMediaImageView.setVisibility(View.INVISIBLE);
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

        return view;
    }

    // return selected image from gallery
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == IMAGE_GALLERY_REQUEST_OUTER) {
                Uri imageUri = data.getData();
                InputStream inputStream;
                try {
                    inputStream = getContext().getContentResolver().openInputStream(imageUri);
                    Bitmap imageBitmap = BitmapFactory.decodeStream(inputStream);
                    outerMediaImageView.setScaleType(ImageView.ScaleType.MATRIX);
                    outerMediaImageView.setImageBitmap(imageBitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            } else if (requestCode == IMAGE_GALLERY_REQUEST_INNER) {
                Uri imageUri = data.getData();
                InputStream inputStream;
                try {
                    inputStream = getContext().getContentResolver().openInputStream(imageUri);
                    Bitmap imageBitmap = BitmapFactory.decodeStream(inputStream);
                    innerMediaImageView.setScaleType(ImageView.ScaleType.MATRIX);
                    innerMediaImageView.setImageBitmap(imageBitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void hideUI() {
        removeOuterMediaImageView.setVisibility(View.INVISIBLE);
        removeInnerMediaImageView.setVisibility(View.INVISIBLE);
        colorPicker.setVisibility(View.INVISIBLE);
        colorPickerImageView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showUI() {

    }
}
