package com.example.justindang.storywell.views;

import android.content.Context;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.justindang.storywell.R;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TextStickerEditorDialogFragment extends DialogFragment {
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

    public interface OnTextListener {
        void sendFontFamily(String font);
        void sendTextSize(int size);
        void sendSpacing(int spacing);
        void sendHeight(int height);
        void sendAlignment(@TextStickerView.Alignment int alignment);
    }
    OnTextListener onTextListener;

    public TextStickerEditorDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_text_sticker_editor, container, false);
        ButterKnife.bind(this, view);
        textEditorSeekBar.setVisibility(View.INVISIBLE);

        //onTextListener = (OnTextListener) getActivity();

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
                //onTextListener.sendAlignment(alignment);
            }
        });
        familyTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textEditorSeekBar.setVisibility(View.INVISIBLE);
                fontFamilyContainerHorizontalScrollView.setVisibility(View.VISIBLE);
            }
        });
        sizeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textEditorSeekBar.setVisibility(View.VISIBLE);
                fontFamilyContainerHorizontalScrollView.setVisibility(View.INVISIBLE);
            }
        });
        spacingTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textEditorSeekBar.setVisibility(View.VISIBLE);
                fontFamilyContainerHorizontalScrollView.setVisibility(View.INVISIBLE);
            }
        });
        heightTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textEditorSeekBar.setVisibility(View.VISIBLE);
                fontFamilyContainerHorizontalScrollView.setVisibility(View.INVISIBLE);
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


        return view;
    }
}