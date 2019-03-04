package com.example.justindang.storywell.views;

import android.content.Context;
import android.graphics.Typeface;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.justindang.storywell.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TextStickerEditorView extends ConstraintLayout {

    // views
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


    @TextStickerView.Alignment int alignment = TextStickerView.LEFT;

    Typeface fontFamily;
    int textSize;
    int textSpacing;
    int textHeight;


    public interface OnTextListener {
        void sendFontFamily(Typeface typeface);
        void sendTextSize(int size);
        void sendSpacing(int spacing);
        void sendHeight(int height);
        void sendAlignment(@TextStickerView.Alignment int alignment);
    }
    OnTextListener onTextListener;


    public TextStickerEditorView(Context context) {
        super(context);
        inflate(context, R.layout.fragment_text_sticker_editor, this);
        ButterKnife.bind(this);
        textEditorSeekBar.setVisibility(View.INVISIBLE);

        onTextListener = (OnTextListener) context;

        // clicklistener
        textAlignmentImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (alignment) {
                    case TextStickerView.LEFT:
                        alignment = TextStickerView.CENTER;
                        textAlignmentImageView.setImageResource(R.drawable.align_center);
                        break;
                    case TextStickerView.CENTER:
                        alignment = TextStickerView.RIGHT;
                        textAlignmentImageView.setImageResource(R.drawable.align_right);
                        break;
                    case TextStickerView.RIGHT:
                        alignment = TextStickerView.LEFT;
                        textAlignmentImageView.setImageResource(R.drawable.align_left);
                        break;
                }
                onTextListener.sendAlignment(alignment);
            }
        });
        familyTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Font Family", Toast.LENGTH_SHORT).show();
                textEditorSeekBar.setVisibility(View.INVISIBLE);
                fontFamilyContainerHorizontalScrollView.setVisibility(View.VISIBLE);
            }
        });
        sizeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Text Size", Toast.LENGTH_SHORT).show();
                textEditorSeekBar.setVisibility(View.VISIBLE);
                fontFamilyContainerHorizontalScrollView.setVisibility(View.INVISIBLE);
            }
        });
        spacingTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Spacing", Toast.LENGTH_SHORT).show();
                textEditorSeekBar.setVisibility(View.VISIBLE);
                fontFamilyContainerHorizontalScrollView.setVisibility(View.INVISIBLE);
            }
        });
        heightTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Height", Toast.LENGTH_SHORT).show();
                textEditorSeekBar.setVisibility(View.VISIBLE);
                fontFamilyContainerHorizontalScrollView.setVisibility(View.INVISIBLE);
            }
        });
        bookTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fontFamily = ResourcesCompat.getFont(getContext(), R.font.franklingothic_book);
                onTextListener.sendFontFamily(fontFamily);
            }
        });
        lightTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fontFamily = ResourcesCompat.getFont(getContext(), R.font.freight_big_w01_light_regular);
                onTextListener.sendFontFamily(fontFamily);
            }
        });
        sansBoldTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fontFamily = ResourcesCompat.getFont(getContext(), R.font.franklingothic_med);
                onTextListener.sendFontFamily(fontFamily);
            }
        });
        serifItalicTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fontFamily = ResourcesCompat.getFont(getContext(), R.font.freight_big_w01_book_italic);
                onTextListener.sendFontFamily(fontFamily);
            }
        });


        // seekbar listener
        textEditorSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Toast.makeText(getContext(), String.valueOf(progress), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
    }


    public int getAlignment() {
        return alignment;
    }

    public void setAlignment(int alignment) {
        this.alignment = alignment;
    }

    public Typeface getFontFamily() {
        return fontFamily;
    }

    public void setFontFamily(Typeface fontFamily) {
        this.fontFamily = fontFamily;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public int getTextSpacing() {
        return textSpacing;
    }

    public void setTextSpacing(int textSpacing) {
        this.textSpacing = textSpacing;
    }

    public int getTextHeight() {
        return textHeight;
    }

    public void setTextHeight(int textHeight) {
        this.textHeight = textHeight;
    }

}
