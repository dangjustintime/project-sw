package com.example.justindang.storywell.views;

import android.content.Context;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.justindang.storywell.R;
import com.example.justindang.storywell.utilities.ImageHandler;


import butterknife.BindView;
import butterknife.ButterKnife;

public class FreeTemplate6View extends TemplateView {

    // views
    @BindView(R.id.edit_text_template6_add_title) EditText addTitleEditText;
    @BindView(R.id.edit_text_template6_tap_to_add) EditText tapToAddEditText;
    @BindView(R.id.text_view_template6_tip) TextView tipTextView;
    @BindView(R.id.image_view_template6_outer_media) ImageView outerMediaImageView;
    @BindView(R.id.image_view_template6_inner_media) ImageView innerMediaImageView;
    @BindView(R.id.image_view_template6_add_outer_media) ImageView addOuterMediaImageView;
    @BindView(R.id.image_view_template6_add_inner_media) ImageView addInnerMediaImageView;
    @BindView(R.id.image_view_template6_remove_outer_media) ImageView removeOuterMediaImageView;
    @BindView(R.id.image_view_template6_remove_inner_media) ImageView removeInnerMediaImageView;
    @BindView(R.id.image_view_template6_color_picker) ImageView colorPickerImageView;
    @BindView(R.id.constraint_layout_template6_container) ConstraintLayout containerConstraintLayout;

    public FreeTemplate6View(Context context) {
        super(context);
        inflate(getContext(), R.layout.custom_view_template6, this);
        ButterKnife.bind(this);
        hideUi();
        addTitleEditText.setCursorVisible(true);
        tapToAddEditText.setCursorVisible(true);
        colorPickerImageView.setVisibility(VISIBLE);

        // clicklisteners
        addOuterMediaImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addOuterMediaImageView.setVisibility(View.INVISIBLE);
                removeOuterMediaImageView.setVisibility(View.VISIBLE);
                templateHandler.getGalleryPhoto(getId(), 0);
            }
        });
        addInnerMediaImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addInnerMediaImageView.setVisibility(View.INVISIBLE);
                removeInnerMediaImageView.setVisibility(View.VISIBLE);
                templateHandler.getGalleryPhoto(getId(), 1);
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
        removeOuterMediaImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                outerMediaImageView.setImageBitmap(null);
                addOuterMediaImageView.setVisibility(View.VISIBLE);
                removeOuterMediaImageView.setVisibility(View.INVISIBLE);
            }
        });

        // text changed listeners
        addTitleEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                templateHandler.sendTitle(addTitleEditText.getText().toString());
            }
        });
        tapToAddEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                templateHandler.sendText(tapToAddEditText.getText().toString());
            }
        });
    }

    @Override
    public void setTitle(String title) {
        addTitleEditText.setText(title);
    }

    @Override
    public void setText(String text) {
        tapToAddEditText.setText(text);
    }

    @Override
    public void setMediaImageView(int mediaIndex, Uri uri) {
        // 0 == outer media
        if (mediaIndex == 0) {
            ImageHandler.setImageToImageView(getContext(), uri, outerMediaImageView,
                    ImageView.ScaleType.CENTER_CROP);
            addOuterMediaImageView.setVisibility(INVISIBLE);
            removeOuterMediaImageView.setVisibility(VISIBLE);
        // 1 == inner media
        } else if (mediaIndex == 1) {
            ImageHandler.setImageToImageView(getContext(), uri, innerMediaImageView,
                    ImageView.ScaleType.CENTER_CROP);
            addInnerMediaImageView.setVisibility(INVISIBLE);
            removeInnerMediaImageView.setVisibility(VISIBLE);
        }
    }

    @Override
    public void setColor(int colorIndex, int color) {
        containerConstraintLayout.setBackgroundColor(color);
    }

    @Override
    public void hideUi() {
        removeOuterMediaImageView.setVisibility(View.INVISIBLE);
        removeInnerMediaImageView.setVisibility(View.INVISIBLE);
        colorPickerImageView.setVisibility(View.INVISIBLE);
        tipTextView.setVisibility(View.INVISIBLE);
        addTitleEditText.setCursorVisible(false);
        tapToAddEditText.setCursorVisible(false);
    }
}
