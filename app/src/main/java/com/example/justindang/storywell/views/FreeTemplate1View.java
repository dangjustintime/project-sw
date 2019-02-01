package com.example.justindang.storywell.views;

import android.content.Context;
import android.net.Uri;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;

import com.example.justindang.storywell.R;
import com.example.justindang.storywell.listeners.OnScaleListener;
import com.example.justindang.storywell.utilities.ImageHandler;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FreeTemplate1View extends TemplateView {

    private ScaleGestureDetector scaleGestureDetector;

    // views
    @BindView(R.id.image_view_template1_inner_media) ImageView innerMediaImageView;
    @BindView(R.id.image_view_template1_outer_media) ImageView outerMediaImageView;
    @BindView(R.id.image_view_template1_add_inner_media) ImageView addInnerMediaImageView;
    @BindView(R.id.image_view_template1_add_outer_media) ImageView addOuterMediaImageView;
    @BindView(R.id.image_view_template1_remove_inner_media) ImageView removeInnerMediaImageView;
    @BindView(R.id.image_view_template1_remove_outer_media) ImageView removeOuterMediaImageView;

    // constructor
    public FreeTemplate1View(Context context) {
        super(context);
        inflate(getContext(), R.layout.custom_view_template1,  this);
        ButterKnife.bind(this);
        scaleGestureDetector = new ScaleGestureDetector(getContext(),
                new OnScaleListener(outerMediaImageView));
        hideUi();

        // clicklisteners
        addOuterMediaImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addOuterMediaImageView.setVisibility(View.INVISIBLE);
                removeOuterMediaImageView.setVisibility(View.VISIBLE);
                templateHandler.getGalleryPhoto(getId(), 1);
            }
        });
        addInnerMediaImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addInnerMediaImageView.setVisibility(View.INVISIBLE);
                removeInnerMediaImageView.setVisibility(View.VISIBLE);
                templateHandler.getGalleryPhoto(getId(), 0);
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

        // onScaleListener
        outerMediaImageView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                scaleGestureDetector.onTouchEvent(event);
                return true;
            }
        });
    }

    @Override
    public void setMediaImageView(int mediaIndex, Uri uri) {
        // 0 == inner media
        if (mediaIndex == 0) {
            ImageHandler.setImageToImageView(getContext(), uri, innerMediaImageView,
                    ImageView.ScaleType.CENTER_CROP);
            addInnerMediaImageView.setVisibility(INVISIBLE);
            removeInnerMediaImageView.setVisibility(VISIBLE);
        // 1 == outer media
        } else if (mediaIndex == 1) {
            ImageHandler.setImageToImageView(getContext(), uri, outerMediaImageView,
                    ImageView.ScaleType.MATRIX);
            addOuterMediaImageView.setVisibility(INVISIBLE);
            removeOuterMediaImageView.setVisibility(VISIBLE);
        }
    }
    @Override
    public void hideUi() {
        removeInnerMediaImageView.setVisibility(View.INVISIBLE);
        removeOuterMediaImageView.setVisibility(View.INVISIBLE);
    }
}
