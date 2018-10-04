package com.example.justindang.storywell.utilities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImageHandler {
    // TAG
    private static final String TAG = "PERMISSION NOT GRANTED";
    // request code
    private static final int REQUEST_WRITE_PERMISSION = 200;

    // empty constructor
    private ImageHandler() { }

    // returns File object representing Pictures directory
    private static File getPublicAlbumStorageDir(String albumName) {
        // get directory for the user's public pictures directory
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            Log.e(TAG, "Directory not created");
        }
        return file;
    };

    // creates bitmap and writes file
    public static boolean writeFile(Activity activity, FrameLayout frameLayout, String fileName) {
        // check if write permissions are granted
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Log.e(TAG,"permission not granted");

            // ask for permission
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
            return false;
        } else {

            // create bitmap from fragmentPlaceholderFrameLayout
            Bitmap bitmap = Bitmap.createBitmap(
                    frameLayout.getWidth(),
                    frameLayout.getHeight(),
                    Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            frameLayout.draw(canvas);

            // create file
            File pictureDir = getPublicAlbumStorageDir("storywell");
            File imageFile = new File(pictureDir, fileName + ".jpg");
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
            return true;
        }
    }

    public static void setImageToImageView(Context context, Intent intent, ImageView imageView, ImageView.ScaleType scaleType) {
        Uri imageUri = intent.getData();
        InputStream inputStream;
        try {
            inputStream = context.getContentResolver().openInputStream(imageUri);
            Bitmap imageBitmap = BitmapFactory.decodeStream(inputStream);
            imageView.setScaleType(scaleType);
            imageView.setImageBitmap(imageBitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static String getPictureDirectory() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath();
    }

    public static Intent createPhotoGalleryIntent() {
        Intent photoGalleryIntent = new Intent(Intent.ACTION_PICK);
        Uri data = Uri.parse(getPictureDirectory());
        photoGalleryIntent.setDataAndType(data, "image/*");
        return photoGalleryIntent;
    }
}
