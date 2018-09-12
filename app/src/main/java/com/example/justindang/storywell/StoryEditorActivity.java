package com.example.justindang.storywell;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;


public class StoryEditorActivity extends AppCompatActivity {
    // intent keys
    private static final String EXTRA_NAME = "name";

    // variables
    private String storyName;

    // TAG
    private static final String TAG = "StoryEditorActivity";

    // request code
    private static final int REQUEST_WRITE_PERMISSION = 200;

    // views
    @BindView(R.id.image_view_aa_icon) ImageView aaIconImageView;
    @BindView(R.id.image_view_square_circle_icon) ImageView squareCircleIconImageView;
    @BindView(R.id.image_view_plus_icon) ImageView plusIconImageView;
    @BindView(R.id.image_view_three_circle_icon) ImageView threeCircleIconImageView;
    @BindView(R.id.image_view_angle_brackets_icon) ImageView angleBracketsIconImageView;
    @BindView(R.id.frame_layout_fragment_placeholder_story_editor) FrameLayout fragmentPlaceholderFrameLayout;
    @BindView(R.id.image_view_back_button) ImageView backButtonImageView;
    @BindView(R.id.image_view_download_icon) ImageView downloadButtonImageView;

    // fragment
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Template1Fragment template1Fragment = new Template1Fragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_editor);
        ButterKnife.bind(this);

        // get data from intent
        storyName = getIntent().getStringExtra(EXTRA_NAME);

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frame_layout_fragment_placeholder_story_editor, template1Fragment);
        fragmentTransaction.commit();

        // clicklisteners
        downloadButtonImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(), "saving image to device....", Toast.LENGTH_SHORT).show();
                saveImage();
            }
        });
        backButtonImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    // checks if external storage is available for read and write
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    // returns File object representing Pictures directory
    public File getPublicAlbumStorageDir(String albumName) {
        // get directory for the user's public pictures directory
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            Log.e(TAG, "Directory not created");
        }
        return file;
    };

    // save photo to storage
    public void saveImage() {
        // check if write permissions are granted
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            Log.e(TAG,"permission not granted");

            // ask for permission
            ActivityCompat.requestPermissions(StoryEditorActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
        } else {

            // create bitmap from fragmentPlaceholderFrameLayout
            Bitmap bitmap = Bitmap.createBitmap(
                    fragmentPlaceholderFrameLayout.getWidth(),
                    fragmentPlaceholderFrameLayout.getHeight(),
                    Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            fragmentPlaceholderFrameLayout.draw(canvas);

            // create file
            File pictureDir = getPublicAlbumStorageDir("storywell");
            File imageFile = new File(pictureDir, storyName + ".jpg");
            try {
                // place bitmap onto output stream
                FileOutputStream outputStream = new FileOutputStream(imageFile);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                outputStream.close();
                Log.e(TAG, "Success");

                // go back to main activity
                finish();
            } catch (IOException e) {
                Log.e(TAG, "Failed");
                e.printStackTrace();
            }
        }
    }
}

