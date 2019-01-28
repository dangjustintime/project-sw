package com.example.justindang.storywell.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.text.Html;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.justindang.storywell.R;
import com.example.justindang.storywell.activities.StoryEditorActivity;
import com.example.justindang.storywell.model.Page;
import com.example.justindang.storywell.utilities.ImageHandler;
import com.example.justindang.storywell.view_model.StoriesViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FreeTemplate1View extends TemplateView {

    private static final int IMAGE_GALLERY_REQUEST = 98;

    // views
    @BindView(R.id.image_view_template1_inner_media) ImageView innerMediaImageView;
    @BindView(R.id.image_view_template1_outer_media) ImageView outerMediaImageView;
    @BindView(R.id.image_view_template1_add_inner_media) ImageView addInnerMediaImageView;
    @BindView(R.id.image_view_template1_add_outer_media) ImageView addOuterMediaImageView;
    @BindView(R.id.image_view_template1_remove_inner_media) ImageView removeInnerMediaImageView;
    @BindView(R.id.image_view_template1_remove_outer_media) ImageView removeOuterMediaImageView;

    // ScaleGestureDetector
    private ScaleGestureDetector scaleGestureDetector;
    private float scaleFactor = 1.5f;
    private Matrix outerMediaMatrix = new Matrix();

    // view model
    StoriesViewModel storiesViewModel;

    // scalelistener
    private class OnScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            Drawable imageDrawable = outerMediaImageView.getDrawable();
            float offsetX = (outerMediaImageView.getWidth() - imageDrawable.getIntrinsicWidth()) / 2f;
            float offsetY = (outerMediaImageView.getHeight() - imageDrawable.getIntrinsicHeight()) / 2f;
            float centerX = outerMediaImageView.getWidth() / 2f;
            float centerY = outerMediaImageView.getHeight() / 2f;
            scaleFactor *= scaleGestureDetector.getScaleFactor();
            scaleFactor = Math.max(1f, Math.min(scaleFactor, 2f));
            outerMediaMatrix.setScale(scaleFactor, scaleFactor, centerX, centerY);
            outerMediaMatrix.preTranslate(offsetX, offsetY);
            outerMediaImageView.setImageMatrix(outerMediaMatrix);
            return true;
        }
    }

    // constructor
    public FreeTemplate1View(Context context) {
        super(context);
        inflate(getContext(), R.layout.fragment_template1,  this);
        ButterKnife.bind(this);

        // clicklisteners
        addOuterMediaImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addOuterMediaImageView.setVisibility(View.INVISIBLE);
                removeOuterMediaImageView.setVisibility(View.VISIBLE);
            }
        });
        addInnerMediaImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addInnerMediaImageView.setVisibility(View.INVISIBLE);
                removeInnerMediaImageView.setVisibility(View.VISIBLE);
                Pair<Integer, Integer> pair = new Pair<>(getId(), 0);
                mediaHandler.getGalleryPhoto(pair);
            }
        });
        removeInnerMediaImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                innerMediaImageView.setImageBitmap(null);
                addInnerMediaImageView.setVisibility(View.VISIBLE);
                removeInnerMediaImageView.setVisibility(View.INVISIBLE);
            }
        });
        removeOuterMediaImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                outerMediaImageView.setImageBitmap(null);
                addOuterMediaImageView.setVisibility(View.VISIBLE);
                removeOuterMediaImageView.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void setMediaImageView(Pair<Integer, Uri> pair) {
        if (pair.first == 0) {
            ImageHandler.setImageToImageView(getContext(), pair.second, innerMediaImageView, ImageView.ScaleType.CENTER_CROP);
        }
    }

    /*
    public void setInnerMediaImageView(Uri uri) {
        ImageHandler.setImageToImageView(getContext(), uri, innerMediaImageView, ImageView.ScaleType.CENTER_CROP);
    }
    */

}
