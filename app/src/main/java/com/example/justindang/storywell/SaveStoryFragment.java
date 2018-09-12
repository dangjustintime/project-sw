package com.example.justindang.storywell;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;


/**
 * A simple {@link Fragment} subclass.
 */
public class SaveStoryFragment extends Fragment {
    public SaveStoryFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_save_story, container, false);

    }

    // creates intent that launches instagram app to post story
    void createInstagramIntent(String type, String mediaPath) {
        // create new intent using the 'send' action
        Intent share = new Intent(Intent.ACTION_SEND);

        // set MIME type
        share.setType(type);

        // create URI from media
        File media = new File(mediaPath);
        Uri uri = Uri.fromFile(media);

        // add URI to intent
        share.putExtra(Intent.EXTRA_STREAM, uri);

        // broadcast intent
        startActivity(Intent.createChooser(share, "Share to"));
    }
}
