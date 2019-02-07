package com.example.justindang.storywell.views;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import com.example.justindang.storywell.R;
import com.example.justindang.storywell.utilities.ImageHandler;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FreeTemplate3View extends TemplateView {

    // views
    @BindView(R.id.image_view_template3_add_bottom_media) ImageView addBottomMediaImageView;
    @BindView(R.id.image_view_template3_add_top_media) ImageView addTopMediaImageView;
    @BindView(R.id.image_view_template3_bottom_media) ImageView bottomMediaImageView;
    @BindView(R.id.image_view_template3_top_media) ImageView topMediaImageView;
    @BindView(R.id.image_view_template3_remove_bottom_media) ImageView removeBottomMediaImageView;
    @BindView(R.id.image_view_template3_remove_top_media) ImageView removeTopMediaImageView;

    public FreeTemplate3View(Context context) {
        super(context);
        inflate(context, R.layout.custom_view_template3, this.templateLayerConstraintLayout);
        ButterKnife.bind(this);
        hideUi();

        // clicklisteners
        addTopMediaImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTopMediaImageView.setVisibility(View.INVISIBLE);
                removeTopMediaImageView.setVisibility(View.VISIBLE);
                templateHandler.getGalleryPhoto(getId(), 0);
            }
        });
        addBottomMediaImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBottomMediaImageView.setVisibility(View.INVISIBLE);
                removeBottomMediaImageView.setVisibility(View.VISIBLE);
                templateHandler.getGalleryPhoto(getId(), 1);
            }
        });
        removeTopMediaImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                topMediaImageView.setImageBitmap(null);
                addTopMediaImageView.setVisibility(View.VISIBLE);
                removeTopMediaImageView.setVisibility(View.INVISIBLE);
            }
        });
        removeBottomMediaImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomMediaImageView.setImageBitmap(null);
                addBottomMediaImageView.setVisibility(View.VISIBLE);
                removeBottomMediaImageView.setVisibility(View.INVISIBLE);
            }
        });

    }

    @Override
    public void setMediaImageView(int mediaIndex, Uri uri) {
        // 0 == top media
        if (mediaIndex == 0) {
            ImageHandler.setImageToImageView(getContext(), uri, topMediaImageView,
                    ImageView.ScaleType.CENTER_CROP);
            addTopMediaImageView.setVisibility(INVISIBLE);
            removeTopMediaImageView.setVisibility(VISIBLE);
            // 1 == bottom media
        } else if (mediaIndex == 1) {
            ImageHandler.setImageToImageView(getContext(), uri, bottomMediaImageView,
                    ImageView.ScaleType.MATRIX);
            addBottomMediaImageView.setVisibility(INVISIBLE);
            removeBottomMediaImageView.setVisibility(VISIBLE);
        }
    }

    @Override
    public void hideUi() {
        removeBottomMediaImageView.setVisibility(View.INVISIBLE);
        removeTopMediaImageView.setVisibility(View.INVISIBLE);
    }
}
