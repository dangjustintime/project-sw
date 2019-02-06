package com.example.justindang.storywell.utilities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.FileProvider;

import com.example.justindang.storywell.activities.StoryEditorActivity;

import java.io.File;

public class InstagramHandler {
    public static Intent createInstagramIntent(Context context, String filename) {
        // create URI from media
        File media = new File(Environment.getExternalStorageDirectory()
                + "/Pictures/storywell/" + filename + ".jpg");
        Uri uri = FileProvider.getUriForFile(context,
                "com.example.justindang.storywell.fileprovider", media);

        // create new intent to open instagram
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setType("image/*");

        return intent;
    }
}
