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

    // file paths
    ArrayList<String> filePaths;
    String bottomMediaFilePath;
    String topMediaFilePath;

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
        filePaths = new ArrayList<>();

        hideUI();

        // clicklisteners
        addTopMediaImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTopMediaImageView.setVisibility(View.INVISIBLE);
                Intent photoGalleryIntent = new Intent(Intent.ACTION_PICK);
                File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                topMediaFilePath = pictureDirectory.getPath();
                Uri data = Uri.parse(topMediaFilePath);
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
                bottomMediaFilePath = pictureDirectory.getPath();
                Uri data = Uri.parse(bottomMediaFilePath);
                photoGalleryIntent.setDataAndType(data, "image/*");
                startActivityForResult(photoGalleryIntent, IMAGE_GALLERY_REQUEST_BOTTOM);
                removeBottomMediaImageView.setVisibility(View.VISIBLE);
            }
        });
        removeTopMediaImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                topMediaImageView.setImageBitmap(null);
                addTopMediaImageView.setVisibility(View.VISIBLE);
                removeTopMediaImageView.setVisibility(View.INVISIBLE);
                filePaths.remove(topMediaFilePath);
            }
        });
        removeBottomMediaImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomMediaImageView.setImageBitmap(null);
                addBottomMediaImageView.setVisibility(View.VISIBLE);
                removeBottomMediaImageView.setVisibility(View.INVISIBLE);
                filePaths.remove(bottomMediaFilePath);
            }
        });

        return view;
    }

    // return selected image from gallery
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == IMAGE_GALLERY_REQUEST_TOP) {
                filePaths.add(data.getData().toString());
                ImageHandler.setImageToImageView(getContext(), data, topMediaImageView, ImageView.ScaleType.MATRIX);
            } else if (requestCode == IMAGE_GALLERY_REQUEST_BOTTOM) {
                filePaths.add(data.getData().toString());
                ImageHandler.setImageToImageView(getContext(), data, bottomMediaImageView, ImageView.ScaleType.MATRIX);
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
        return filePaths;
    }

    @Override
    public ArrayList<Integer> sendColors() {
        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.add(0);
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
