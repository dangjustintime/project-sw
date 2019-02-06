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

public class FreeTemplate5View extends TemplateView {

    // views
    @BindView(R.id.image_view_template5_media) ImageView mediaImageView;
    @BindView(R.id.image_view_template5_add_media) ImageView addMediaImageView;
    @BindView(R.id.image_view_template5_remove_media) ImageView removeMediaImageView;
    @BindView(R.id.edit_text_template5_add_title) EditText addTitleEditText;
    @BindView(R.id.edit_text_template5_tap_to_add) EditText tapToAddEditText;
    @BindView(R.id.text_view_template5_tip) TextView tipTextView;
    @BindView(R.id.image_view_template5_color_picker) ImageView colorPickerImageView;
    @BindView(R.id.constraint_layout_template5_container) ConstraintLayout containerConstraintLayout;

    public FreeTemplate5View(Context context) {
        super(context);
        inflate(getContext(), R.layout.custom_view_template5, this.templateLayerConstraintLayout);
        ButterKnife.bind(this);
        hideUi();
        addTitleEditText.setCursorVisible(true);
        tapToAddEditText.setCursorVisible(true);
        colorPickerImageView.setVisibility(VISIBLE);

        // clicklisteners
        addMediaImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMediaImageView.setVisibility(View.INVISIBLE);
                removeMediaImageView.setVisibility(View.VISIBLE);
                templateHandler.getGalleryPhoto(getId(), 0);
            }
        });
        removeMediaImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaImageView.setImageBitmap(null);
                addMediaImageView.setVisibility(View.VISIBLE);
                removeMediaImageView.setVisibility(View.INVISIBLE);
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
        // 0 == media
        ImageHandler.setImageToImageView(getContext(), uri, mediaImageView,
                ImageView.ScaleType.CENTER_CROP);
        addMediaImageView.setVisibility(INVISIBLE);
        removeMediaImageView.setVisibility(VISIBLE);
    }

    @Override
    public void setColor(int colorIndex, int color) {
        containerConstraintLayout.setBackgroundColor(color);
    }

    @Override
    public void hideUi() {
        removeMediaImageView.setVisibility(View.INVISIBLE);
        colorPickerImageView.setVisibility(View.INVISIBLE);
        tipTextView.setVisibility(View.INVISIBLE);
        addTitleEditText.setCursorVisible(false);
        tapToAddEditText.setCursorVisible(false);
    }
}
