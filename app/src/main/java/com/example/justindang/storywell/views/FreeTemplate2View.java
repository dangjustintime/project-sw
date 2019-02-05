package com.example.justindang.storywell.views;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import com.example.justindang.storywell.R;
import com.example.justindang.storywell.utilities.ImageHandler;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FreeTemplate2View extends TemplateView {
    // views
    @BindView(R.id.image_view_template2_inner_media) ImageView innerMediaImageView;
    @BindView(R.id.image_view_template2_outer_layer) ImageView outerLayerImageView;
    @BindView(R.id.image_view_template2_add_inner_media) ImageView addInnerMediaImageView;
    @BindView(R.id.image_view_template2_color_picker_outer_layer) ImageView colorPickerImageView;
    @BindView(R.id.image_view_template2_remove_inner_media) ImageView removeInnerMediaImageView;

    public FreeTemplate2View(Context context) {
        super(context);
        inflate(context, R.layout.custom_view_template2, this);
        ButterKnife.bind(this);

        hideUi();
        colorPickerImageView.setVisibility(VISIBLE);

        // clicklisteners
        addInnerMediaImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                addInnerMediaImageView.setVisibility(View.INVISIBLE);
                removeInnerMediaImageView.setVisibility(View.VISIBLE);
                templateHandler.getGalleryPhoto(getId(), 0);
            }
        });
        removeInnerMediaImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                innerMediaImageView.setImageBitmap(null);
                addInnerMediaImageView.setVisibility(View.VISIBLE);
                removeInnerMediaImageView.setVisibility(View.INVISIBLE);
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
        }
    }

    @Override
    public void setColor(int colorIndex, int color) {
        // 0 = outerlayer color
        outerLayerImageView.setBackgroundColor(color);
    }

    @Override
    public void hideUi() {
        removeInnerMediaImageView.setVisibility(INVISIBLE);
        colorPickerImageView.setVisibility(INVISIBLE);
    }
}
