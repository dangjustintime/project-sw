package com.example.justindang.storywell.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.colorpicker.shishank.colorpicker.ColorPicker;
import com.example.justindang.storywell.R;
import com.example.justindang.storywell.activities.StoryEditorActivity;
import com.example.justindang.storywell.model.Page;
import com.example.justindang.storywell.model.Stories;
import com.example.justindang.storywell.view_model.StoriesViewModel;
import com.example.justindang.storywell.views.TemplateView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ColorPickerFragment extends Fragment {
    // views
    @BindView(R.id.color_picker) ColorPicker colorPicker;

    int targetViewType;

    public interface OnColorListener {
        void sendColor(int color, @TemplateView.ViewType int viewType);
    }
    OnColorListener onColorListener;

    // constructor
    public ColorPickerFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_color_picker, container, false);
        ButterKnife.bind(this, view);

        onColorListener = (OnColorListener) getActivity();

        // color picker listener
        colorPicker.setGradientView(R.drawable.color_gradient);
        colorPicker.setColorSelectedListener(new ColorPicker.ColorSelectedListener() {
            @Override
            public void onColorSelected(int color, boolean isTapUp) {
                onColorListener.sendColor(color,  targetViewType);
            }
        });

        return view;
    }

    public @TemplateView.ViewType int getTargetViewType() {
        return this.targetViewType;
    }

    public void setTargetViewType(@TemplateView.ViewType int viewType) {
        this.targetViewType = viewType;
    }
}
