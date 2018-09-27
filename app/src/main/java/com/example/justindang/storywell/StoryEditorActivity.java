package com.example.justindang.storywell;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.justindang.storywell.model.Story;
import com.example.justindang.storywell.presenter.StoryPresenter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;


public class StoryEditorActivity extends AppCompatActivity implements SaveStoryDialogFragment.OnSaveListener, StoryPresenter.View {
    // intent keys
    private static final String EXTRA_NAME = "name";
    private static final String EXTRA_TEMPLATE = "template";

    // model of story
    private ArrayList<String> filePaths;
    private StoryPresenter storyPresenter;

    // TAG
    private static final String TAG = "StoryEditorActivity";
    private static final String DIALOG_SAVE_STORY = "save story";

    // request code
    private static final int REQUEST_WRITE_PERMISSION = 200;

    public interface OnSaveImageListener {
        void hideUI();
        void showUI();
        ArrayList<String> sendFilePaths();
        ArrayList<Integer> sendColors();
    }
    OnSaveImageListener onSaveImageListener;

    // template manager
    TemplateManager templateManager = new TemplateManager();

    // views
    @BindView(R.id.image_view_story_editor_aa_icon) ImageView aaIconImageView;
    @BindView(R.id.image_view_story_editor_square_circle_icon) ImageView squareCircleIconImageView;
    @BindView(R.id.image_view_story_editor_plus_icon) ImageView plusIconImageView;
    @BindView(R.id.image_view_story_editor_three_circle_icon) ImageView threeCircleIconImageView;
    @BindView(R.id.image_view_story_editor_angle_brackets_icon) ImageView angleBracketsIconImageView;
    @BindView(R.id.frame_layout_fragment_placeholder_story_editor) FrameLayout fragmentPlaceholderFrameLayout;
    @BindView(R.id.image_view_story_editor_back_button) ImageView backButtonImageView;
    @BindView(R.id.image_view_story_editor_download_icon) ImageView downloadButtonImageView;
    @BindView(R.id.frame_layout_fragment_placeholder_save_story) FrameLayout fragmentPlaceholderSaveStoryFrameLayout;

    // fragments
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Fragment templatePlaceholderFragment;
    SaveStoryDialogFragment saveStoryDialogFragment = new SaveStoryDialogFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_editor);
        ButterKnife.bind(this);

        // initialize presenter
        storyPresenter = new StoryPresenter(this);

        // get data from intent
        storyPresenter.updateName(getIntent().getStringExtra(EXTRA_NAME));
        storyPresenter.updateTemplateName(getIntent().getStringExtra(EXTRA_TEMPLATE));
        templatePlaceholderFragment = templateManager.getTemplate(storyPresenter.getStory().getTemplateName());
        onSaveImageListener = (OnSaveImageListener) templatePlaceholderFragment;

        // initialize list
        filePaths = new ArrayList<String>();

        // add fragment to back stack
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frame_layout_fragment_placeholder_story_editor, templatePlaceholderFragment);
        fragmentTransaction.commit();

        // clicklisteners
        downloadButtonImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveStoryDialogFragment.show(fragmentManager, DIALOG_SAVE_STORY);
            }
        });
        backButtonImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        plusIconImageView.setOnClickListener(new View.OnClickListener() {
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
        // get values from templates
        storyPresenter.updateImagePaths(onSaveImageListener.sendFilePaths());
        //storyPresenter.updateColor(onSaveImageListener.sendColor());

        Toast.makeText(getBaseContext(), "saving image to device....", Toast.LENGTH_SHORT).show();

        // check if write permissions are granted
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
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
            File imageFile = new File(pictureDir, storyPresenter.getStory().getName() + ".jpg");
            try {
                // place bitmap onto output stream
                FileOutputStream outputStream = new FileOutputStream(imageFile);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                outputStream.close();
                Log.e(TAG, "Success");
            } catch (IOException e) {
                Log.e(TAG, "Failed");
                e.printStackTrace();
            }
        }

        // put values in shared preferences
        SharedPreferences sharedPreferences = this.getSharedPreferences(getResources().getString(R.string.saved_stories), 0);
        int numStories = sharedPreferences.getInt(getResources().getString(R.string.saved_num_stories_keys), 0);

        // generate key
        storyPresenter.generateSharedPrefKey(numStories);
        String key = storyPresenter.getStory().getSharedPrefKey();

        // put values in shared preferences
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.putString(key + "_name", storyPresenter.getStory().getName());
        sharedPreferencesEditor.putString(key + "_template", storyPresenter.getStory().getTemplateName());
        //sharedPreferencesEditor.putInt(key + "_color", storyPresenter.getStory().getColor());

        // convert Arraylist to HashSet and put values in shared preferences
        HashSet<String> filePathsHashSet = new HashSet<String>(storyPresenter.getStory().getPicturePaths());
        sharedPreferencesEditor.putStringSet(key + "_file_paths", filePathsHashSet);

        // increment number of stories
        sharedPreferencesEditor.putInt(getResources().getString(R.string.saved_num_stories_keys), ++numStories);
        sharedPreferencesEditor.apply();
    }

    // creates intent that launches instagram app to post story
    void createInstagramIntent() {
        // create URI from media
        File media = new File(Environment.getExternalStorageDirectory() + "/Pictures/storywell/" + storyPresenter.getStory().getName() + ".jpg");
        Uri uri = FileProvider.getUriForFile(StoryEditorActivity.this, "com.example.justindang.storywell.fileprovider", media);

        // create new intent to open instagram
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setType("image/*");

        // verify that intent will resolve to an activity
        Intent intentChooser = Intent.createChooser(intent, "Share Story");
        if (intent.resolveActivity(this.getPackageManager()) != null) {
            startActivity(Intent.createChooser(intent, "Share Story"));
        }
    }

    // SaveStoryDialog interface
    @Override
    public void saveStory() {
        onSaveImageListener.hideUI();
        saveImage();
        finish();
    }

    @Override
    public void saveStories() {
        Toast.makeText(getBaseContext(), "save stories", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void shareStoryToInstagram() {
        Toast.makeText(getBaseContext(), "sharing to instagram", Toast.LENGTH_SHORT).show();
        onSaveImageListener.hideUI();
        saveImage();
        createInstagramIntent();
    }

    // StoryPresenter interface
    @Override
    public void updateView(Story story) { }
}

