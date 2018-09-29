package com.example.justindang.storywell;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.OpenableColumns;
import android.support.v4.app.Fragment;
import android.support.v4.view.MotionEventCompat;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.justindang.storywell.model.Story;
import com.example.justindang.storywell.presenter.StoryPresenter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;

public class Template1Fragment extends Fragment implements StoryEditorActivity.OnSaveImageListener {

    // invalid pointer id
    private static final int INVALID_POINTER_ID = 0;

    // file paths
    ArrayList<String> filePaths;
    String innerMediaFilePath;
    String outerMediaFilePath;

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

    // pointer location
    private float lastTouchX;
    private float lastTouchY;
    private float posX = 0;
    private float posY = 0;

    // ScaleGestureDetector
    private ScaleGestureDetector scaleGestureDetector;
    private float scaleFactor = 1.5f;
    private Matrix outerMediaMatrix = new Matrix();

    // active pointer for moving image
    private int activePointerId = INVALID_POINTER_ID;

    // Uri
    Uri imageUri;

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

        // initialize filePaths
        filePaths = new ArrayList<String>();

        // gesture listener
        scaleGestureDetector = new ScaleGestureDetector(getContext(), new ScaleListener());

        // get image from gallery onclick
        addOuterMediaImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addOuterMediaImageView.setVisibility(View.INVISIBLE);
                Intent photoGalleryIntent = new Intent(Intent.ACTION_PICK);
                File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                outerMediaFilePath = pictureDirectory.getPath();
                Uri data = Uri.parse(outerMediaFilePath);
                photoGalleryIntent.setDataAndType(data, "image/*");
                startActivityForResult(photoGalleryIntent, IMAGE_GALLERY_REQUEST_OUTER);
                removeOuterMediaImageView.setVisibility(View.VISIBLE);
            }

        });
        addInnerMediaImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        removeInnerMediaImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                innerMediaImageView.setImageBitmap(null);
                addInnerMediaImageView.setVisibility(View.VISIBLE);
                removeInnerMediaImageView.setVisibility(View.INVISIBLE);
                filePaths.remove(innerMediaFilePath);
            }
        });
        removeOuterMediaImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                outerMediaImageView.setImageBitmap(null);
                addOuterMediaImageView.setVisibility(View.VISIBLE);
                removeOuterMediaImageView.setVisibility(View.INVISIBLE);
                filePaths.remove(outerMediaFilePath);
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
            if (requestCode == IMAGE_GALLERY_REQUEST_OUTER) {
                imageUri = data.getData();
                InputStream inputStream;

                // get absolute path for image
                Cursor cursor = getActivity().getContentResolver().query(imageUri, null, null, null, null);
                int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                cursor.moveToFirst();
                outerMediaFilePath = cursor.getString(nameIndex);
                filePaths.add(outerMediaFilePath);

                try {
                    inputStream = getContext().getContentResolver().openInputStream(imageUri);
                    Bitmap imageBitmap = BitmapFactory.decodeStream(inputStream);
                    outerMediaImageView.setScaleType(ImageView.ScaleType.MATRIX);
                    outerMediaImageView.setImageBitmap(imageBitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            } else if (requestCode == IMAGE_GALLERY_REQUEST_INNER) {
                imageUri = data.getData();
                InputStream inputStream;

                // get absolute path for image
                Cursor cursor = getActivity().getContentResolver().query(imageUri, null, null, null, null);
                int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                cursor.moveToFirst();
                innerMediaFilePath = cursor.getString(nameIndex);
                filePaths.add(innerMediaFilePath);

                try {
                    inputStream = getActivity().getContentResolver().openInputStream(imageUri);
                    Bitmap imageBitmap = BitmapFactory.decodeStream(inputStream);
                    innerMediaImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    innerMediaImageView.setImageBitmap(imageBitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // hide UI
    @Override
    public void hideUI() {
        removeInnerMediaImageView.setVisibility(View.INVISIBLE);
        removeOuterMediaImageView.setVisibility(View.INVISIBLE);
    }

    // show UI
    @Override
    public void showUI() {
        removeInnerMediaImageView.setVisibility(View.VISIBLE);
        removeOuterMediaImageView.setVisibility(View.VISIBLE);
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
