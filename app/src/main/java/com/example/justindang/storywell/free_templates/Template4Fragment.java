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


/**
 * A simple {@link Fragment} subclass.
 */
public class Template4Fragment extends Fragment implements StoryEditorActivity.OnSaveImageListener {
    // request codes
    private static final int IMAGE_GALLERY_REQUEST_TOP = 40;
    private static final int IMAGE_GALLERY_REQUEST_BOTTOM = 48;

    // file paths
    ArrayList<String> filePaths;
    String bottomMediaFilePath;
    String topMediaFilePath;

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
        filePaths = new ArrayList<>();

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
                removeTopMediaImageView.setVisibility(View.INVISIBLE);
                addTopMediaImageView.setVisibility(View.VISIBLE);
                filePaths.remove(topMediaFilePath);
            }
        });
        removeBottomMediaImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomMediaImageView.setImageBitmap(null);
                removeBottomMediaImageView.setVisibility(View.INVISIBLE);
                addBottomMediaImageView.setVisibility(View.VISIBLE);
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
        removeTopMediaImageView.setVisibility(View.INVISIBLE);
        removeBottomMediaImageView.setVisibility(View.INVISIBLE);
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
