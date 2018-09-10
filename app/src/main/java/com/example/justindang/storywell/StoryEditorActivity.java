package com.example.justindang.storywell;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;


public class StoryEditorActivity extends AppCompatActivity {
    // TAG
    private static final String TAG = "StoryEditorActivity";
    // views
    @BindView(R.id.image_view_aa_icon) ImageView aaIconImageView;
    @BindView(R.id.image_view_square_circle_icon) ImageView squareCircleIconImageView;
    @BindView(R.id.image_view_plus_icon) ImageView plusIconImageView;
    @BindView(R.id.image_view_three_circle_icon) ImageView threeCircleIconImageView;
    @BindView(R.id.image_view_angle_brackets_icon) ImageView angleBracketsIconImageView;
    @BindView(R.id.frame_layout_fragment_placeholder_story_editor) FrameLayout fragmentPlaceholderFrameLayout;

    // fragment
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Template1Fragment template1Fragment = new Template1Fragment();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_editor);
        ButterKnife.bind(this);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frame_layout_fragment_placeholder_story_editor, template1Fragment);
        fragmentTransaction.commit();

        plusIconImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // create bitmap from fragmentPlaceholderFrameLayout
                Bitmap bitmap = Bitmap.createBitmap(
                        fragmentPlaceholderFrameLayout.getWidth(),
                        fragmentPlaceholderFrameLayout.getHeight(),
                        Bitmap.Config.ARGB_8888);
                Canvas c = new Canvas(bitmap);
                // create file
                String filename = "test";
                File pictureDir = getPublicAlbumStorageDir("storywell");
                File imageFile = new File(pictureDir,"test");
                try {
                    FileOutputStream outputStream = new FileOutputStream(imageFile);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                    outputStream.close();
                    Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT);
                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT);
                    e.printStackTrace();
                }

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
}

