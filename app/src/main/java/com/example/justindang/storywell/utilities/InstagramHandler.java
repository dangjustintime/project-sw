package com.example.justindang.storywell.utilities;

import android.app.Activity;
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
        File mediaFile = new File(Environment.getExternalStorageDirectory()
                + "/Pictures/storywell/" + filename + ".jpg");
        Uri mediaUri = FileProvider.getUriForFile(context,
                "com.example.justindang.storywell.fileprovider", mediaFile);

        // create URI from sticker file
        File stickerFile = new File(Environment.getExternalStorageDirectory()
                + "/Pictures/storywell/stickerLayer.jpg");
        Uri stickerUri = FileProvider.getUriForFile(context,
                "com.example.justindang.storywell.fileprovider", stickerFile);

        // create new intent to open instagram
        Intent intent = new Intent("com.instagram.share.ADD_TO_STORY");
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(mediaUri, "image/*");
        intent.putExtra("interactive_asset_uri", stickerUri);

        Activity activity = (Activity) context;
        activity.grantUriPermission("com.instagram.android", stickerUri,
                Intent.FLAG_GRANT_READ_URI_PERMISSION);

        return intent;
    }
}
