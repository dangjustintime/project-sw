package com.example.justindang.storywell;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ImageReader;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class Template4Fragment extends Fragment implements StoryEditorActivity.OnSaveImageListener {
    // request codes
    private static final int IMAGE_GALLERY_REQUEST_TOP = 40;
    private static final int IMAGE_GALLERY_REQUEST_BOTTOM = 48;

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

        removeTopMediaImageView.setVisibility(View.INVISIBLE);
        removeBottomMediaImageView.setVisibility(View.INVISIBLE);

        // clicklisteners
        addTopMediaImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTopMediaImageView.setVisibility(View.INVISIBLE);
                Intent photoGalleryIntent = new Intent(Intent.ACTION_PICK);
                File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                String pictureDirectoryPath = pictureDirectory.getPath();
                Uri data = Uri.parse(pictureDirectoryPath);
                photoGalleryIntent.setDataAndType(data, "image/*");
                startActivityForResult(photoGalleryIntent, IMAGE_GALLERY_REQUEST_TOP);
                removeTopMediaImageView.setVisibility(View.VISIBLE);
            }
        });
        addBottomMediaImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBottomMediaImageView.setVisibility(View.INVISIBLE);
                Intent photoGalleryIntent = new Intent(Intent.ACTION_PICK);
                File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                String pictureDirectoryPath = pictureDirectory.getPath();
                Uri data = Uri.parse(pictureDirectoryPath);
                photoGalleryIntent.setDataAndType(data, "image/*");
                startActivityForResult(photoGalleryIntent, IMAGE_GALLERY_REQUEST_BOTTOM);
                removeBottomMediaImageView.setVisibility(View.VISIBLE);
            }
        });
        removeTopMediaImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                topMediaImageView.setImageBitmap(null);
                removeTopMediaImageView.setVisibility(View.INVISIBLE);
                addTopMediaImageView.setVisibility(View.VISIBLE);
            }
        });
        removeBottomMediaImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomMediaImageView.setImageBitmap(null);
                removeBottomMediaImageView.setVisibility(View.INVISIBLE);
                addBottomMediaImageView.setVisibility(View.VISIBLE);
            }
        });

        return view;
    }

    // return selected image from gallery
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == IMAGE_GALLERY_REQUEST_TOP) {
                Uri imageUri = data.getData();
                InputStream inputStream;
                try {
                    inputStream = getContext().getContentResolver().openInputStream(imageUri);
                    Bitmap imageBitmap = BitmapFactory.decodeStream(inputStream);
                    topMediaImageView.setScaleType(ImageView.ScaleType.MATRIX);
                    topMediaImageView.setImageBitmap(imageBitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            } else if (requestCode == IMAGE_GALLERY_REQUEST_BOTTOM) {
                Uri imageUri = data.getData();
                InputStream inputStream;
                try {
                    inputStream = getContext().getContentResolver().openInputStream(imageUri);
                    Bitmap imageBitmap = BitmapFactory.decodeStream(inputStream);
                    bottomMediaImageView.setScaleType(ImageView.ScaleType.MATRIX);
                    bottomMediaImageView.setImageBitmap(imageBitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    @Override
    public void hideUI() {
        removeTopMediaImageView.setVisibility(View.INVISIBLE);
        removeBottomMediaImageView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showUI() {

    }
}
