package com.example.justindang.storywell.utilities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
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
            Log.e(TAG, "directory not created");
        }
        return file;
    };

    // creates bitmap and writes file
    public static boolean writeBackgroundLayerFile(Activity activity, ViewGroup viewGroup, String fileName) {
        // check if write permissions are granted
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Log.e(TAG,"write file permission not granted");
            // ask for permission
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_WRITE_PERMISSION);
            return false;
        } else {
            // create bitmap from fragmentPlaceholderFrameLayout
            Bitmap bitmap = Bitmap.createBitmap(viewGroup.getWidth(), viewGroup.getHeight(),
                    Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            viewGroup.draw(canvas);
            // create file
            File pictureDir = getPublicAlbumStorageDir("storywell");
            File imageFile = new File(pictureDir, fileName + ".jpg");
            try {
                // place bitmap onto output stream
                FileOutputStream outputStream = new FileOutputStream(imageFile);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
    }

    public static boolean writeStickerLayerFile(Activity activity, ViewGroup viewGroup) {
        // check if write permissions are granted
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Log.e(TAG,"write file permission not granted");
            // ask for permission
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_WRITE_PERMISSION);
            return false;
        } else {
            // create bitmap from fragmentPlaceholderFrameLayout
            Bitmap bitmap = Bitmap.createBitmap(480, 640, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            viewGroup.draw(canvas);
            // create file
            File pictureDir = getPublicAlbumStorageDir("storywell");
            File imageFile = new File(pictureDir, "stickerLayer.png");
            try {
                // place bitmap onto output stream
                FileOutputStream outputStream = new FileOutputStream(imageFile);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
    }

    public static void setImageToImageView(Context context, Uri imageUri, ImageView imageView, ImageView.ScaleType scaleType) {
        InputStream inputStream;
        try {
            inputStream = context.getContentResolver().openInputStream(imageUri);
            Bitmap imageBitmap = BitmapFactory.decodeStream(inputStream);
            Matrix matrix = new Matrix();
            matrix.postRotate(90);
            Bitmap rotatedBitmap = Bitmap.createBitmap(imageBitmap, 0, 0,
                    imageBitmap.getWidth(), imageBitmap.getHeight(), matrix, true);
            imageView.setScaleType(scaleType);
            imageView.setImageBitmap(rotatedBitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void setImageToViewGroup(Context context, Uri imageUri, ViewGroup viewGroup) {
        InputStream inputStream;
        try {
            inputStream = context.getContentResolver().openInputStream(imageUri);
            Bitmap imageBitmap = BitmapFactory.decodeStream(inputStream);
            Matrix matrix = new Matrix();
            matrix.postRotate(90);
            Bitmap rotatedBitmap = Bitmap.createBitmap(imageBitmap, 0, 0,
                    imageBitmap.getWidth(), imageBitmap.getHeight(), matrix, true);
            BitmapDrawable drawable = new BitmapDrawable(rotatedBitmap);
            viewGroup.setBackground(drawable);
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
