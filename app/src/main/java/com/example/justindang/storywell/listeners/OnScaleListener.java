package com.example.justindang.storywell.listeners;

import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.view.ScaleGestureDetector;
import android.widget.ImageView;

public class OnScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
    private ImageView imageView;
    private float scaleFactor;
    private Matrix outerMediaMatrix;

    // constructor
    public OnScaleListener(ImageView imageView) {
        this.imageView = imageView;
        scaleFactor = 1.5f;
        this.outerMediaMatrix = new Matrix();
    }

    @Override
    public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
        Drawable imageDrawable = imageView.getDrawable();
        float offsetX = (imageView.getWidth() - imageDrawable.getIntrinsicWidth()) / 2f;
        float offsetY = (imageView.getHeight() - imageDrawable.getIntrinsicHeight()) / 2f;
        float centerX = imageView.getWidth() / 2f;
        float centerY = imageView.getHeight() / 2f;
        scaleFactor *= scaleGestureDetector.getScaleFactor();
        scaleFactor = Math.max(1f, Math.min(scaleFactor, 2f));
        outerMediaMatrix.setScale(scaleFactor, scaleFactor, centerX, centerY);
        outerMediaMatrix.preTranslate(offsetX, offsetY);
        imageView.setImageMatrix(outerMediaMatrix);
        return true;
    }
}
