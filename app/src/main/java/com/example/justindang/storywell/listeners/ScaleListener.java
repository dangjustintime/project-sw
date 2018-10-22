package com.example.justindang.storywell.listeners;

import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;

import com.example.justindang.storywell.presenter.StoriesPresenter;

// scalelistener
public class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {

    public boolean onScale(ScaleGestureDetector scaleGestureDetector, ImageView imageView) {
        float scaleFactor = 1.5f;
        Matrix matrix = new Matrix();

        Drawable imageDrawable = imageView.getDrawable();
        float offsetX = (imageView.getWidth() - imageDrawable.getIntrinsicWidth()) / 2f;
        float offsetY = (imageView.getHeight() - imageDrawable.getIntrinsicHeight()) / 2f;
        float centerX = imageView.getWidth() / 2f;
        float centerY = imageView.getHeight() / 2f;
        scaleFactor *= scaleGestureDetector.getScaleFactor();
        scaleFactor = Math.max(1f, Math.min(scaleFactor, 2f));
        matrix.setScale(scaleFactor, scaleFactor, centerX, centerY);
        matrix.preTranslate(offsetX, offsetY);
        imageView.setImageMatrix(matrix);
        return true;
    }
}