package com.example.justindang.storywell.views;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.justindang.storywell.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TextStickerEditorView extends ConstraintLayout {
    @BindView(R.id.constraint_layout_top_bar) ConstraintLayout topBarConstraintLayout;
    @BindView(R.id.linear_layout_bottom_bar) LinearLayout bottomBarLinearLayout;
    @BindView(R.id.seek_bar_text_editor) SeekBar textEditorSeekBar;
    @BindView(R.id.horizontal_scroll_view_font_family_container) HorizontalScrollView fontFamilyContainerHorizontalScrollView;
    @BindView(R.id.text_view_book) TextView bookTextView;
    @BindView(R.id.text_view_light) TextView lightTextView;
    @BindView(R.id.text_view_sans_bold) TextView sansBoldTextView;
    @BindView(R.id.text_view_serif_italic) TextView serifItalicTextView;
    @BindView(R.id.text_view_family) TextView familyTextView;
    @BindView(R.id.text_view_size) TextView sizeTextView;
    @BindView(R.id.text_view_spacing) TextView spacingTextView;
    @BindView(R.id.text_view_height) TextView heightTextView;
    @BindView(R.id.image_view_text_alignment) ImageView textAlignmentImageView;

    public TextStickerEditorView(Context context) {
        super(context);
        inflate(context, R.layout.custom_view_text_sticker_editor, TextStickerEditorView.this);
        ButterKnife.bind(this);
        fontFamilyContainerHorizontalScrollView.setVisibility(INVISIBLE);
    }
}
