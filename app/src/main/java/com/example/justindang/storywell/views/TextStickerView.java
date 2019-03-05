package com.example.justindang.storywell.views;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.IntDef;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.justindang.storywell.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.security.Key;

public class TextStickerView extends StickerView {
    EditText editText;

    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int CENTER = 2;

    @IntDef ({LEFT, RIGHT, CENTER})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Alignment { }

    public interface OnTextStickerListener {
        public void editTextInputDone();
    }
    OnTextStickerListener onTextStickerListener;

    public TextStickerView(Context context) {
        super(context);
        editText = new EditText(context);
        editText.setBackground(null);
        editText.setText("tap to edit");
        editText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        editText.setTextColor(getResources().getColor(R.color.colorWhite));
        containerLinearLayout.addView(editText);
        editText.requestFocus();
        editText.setInputType(InputType.TYPE_CLASS_TEXT);

        onTextStickerListener = (OnTextStickerListener) context;

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    editText.clearFocus();
                    onTextStickerListener.editTextInputDone();
                    return true;
                }
                return true;
            }
        });


        /*
        if (editText.requestFocus()) {
            InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT);
        }
        */
    }

    @Override
    public void setColor(int color) {
        editText.setTextColor(color);
    }

    public void setFontFamily(Typeface typeface) {
        editText.setTypeface(typeface);
    }

    public void setAlignment(@Alignment int alignment) {
        switch (alignment) {
            case LEFT:
                editText.setGravity(Gravity.START);
                break;
            case CENTER:
                editText.setGravity(Gravity.CENTER);
                break;
            case RIGHT:
                editText.setGravity(Gravity.END);
                break;
        }
    }

    public void setTextSize(int size) {
        editText.setTextSize(size);
    }

    public void setTextHeight(int height) {
        editText.setHeight(height);
    }

    public void setTextSpacing(int spacing) {
        editText.setLetterSpacing(spacing);
    }

    @Override
    public void hideUi() {
        super.hideUi();
        editText.setCursorVisible(false);
    }

    public EditText getEditText() {
        return editText;
    }
}
