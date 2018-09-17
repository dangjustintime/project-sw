package com.example.justindang.storywell;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class SaveStoryDialogFragment extends DialogFragment {
    // TAG
    public static final String TAG = "save story";

    // views
    @BindView(R.id.button_save_story) Button saveStoryButton;
    @BindView(R.id.button_save_stories) Button saveStoriesButton;
    @BindView(R.id.button_share_story_to_instagram) Button shareStoryToInstagramButton;

    public SaveStoryDialogFragment() {
        // Required empty public constructor
    }

    // save listener
    public interface OnSaveListener {
        void saveStory();
        void saveStories();
        void shareStoryToInstagram();
    }
    OnSaveListener onSaveListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_save_story, container, false);
        ButterKnife.bind(this, view);
        onSaveListener = (OnSaveListener) getActivity();

        // clicklisteners
        saveStoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSaveListener.saveStory();
                dismiss();
            }
        });
        saveStoriesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSaveListener.saveStories();
                dismiss();
            }
        });
        shareStoryToInstagramButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSaveListener.shareStoryToInstagram();
                createInstagramIntent();
                dismiss();
            }
        });
        return view;
    }

    // creates intent that launches instagram app to post story
    void createInstagramIntent() {
        // create URI from media
        File media = new File(getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/testing.jpg");
       // File path = new File(getContext().getFilesDir(), "images");
        //File newFile = new File(path, "test photo.jpg");
        Uri uri = FileProvider.getUriForFile(getContext(), "com.example.justindang.storywell.fileprovider", media);

        // create new intent to open instagram
        Intent intent = new Intent("com.instagram.share.ADD_TO_STORY");
        intent.setAction(Intent.ACTION_SEND);
        intent.setDataAndType(uri, "image/*");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        Activity activity = getActivity();
        if (activity.getPackageManager().resolveActivity(intent, 0) != null) {
            activity.startActivityForResult(intent, 0);
        }
    }
}
