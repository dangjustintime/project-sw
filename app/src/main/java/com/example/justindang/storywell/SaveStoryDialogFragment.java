package com.example.justindang.storywell;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class SaveStoryDialogFragment extends DialogFragment {
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
                // createInstagramIntent();
                dismiss();
            }
        });


        return view;
    }

    // creates intent that launches instagram app to post story
    void createInstagramIntent() {
        // create new intent using the 'send' action
        Intent share = new Intent(Intent.ACTION_SEND);

        // set MIME type
        share.setType("image/*");

        // create URI from media
        File media = new File(Environment.getExternalStorageDirectory() +  "test photo.jpg");
        Uri uri = Uri.fromFile(media);

        // add URI to intent
        share.putExtra(Intent.EXTRA_STREAM, uri);

        // broadcast intent
        startActivity(Intent.createChooser(share, "Share to"));
    }
}
