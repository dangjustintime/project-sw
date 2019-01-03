package com.example.justindang.storywell.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.colorpicker.shishank.colorpicker.ColorPicker;
import com.example.justindang.storywell.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ColorPickerFragment extends Fragment {
    // views
    @BindView(R.id.color_picker) ColorPicker colorPicker;

    // fragment manager
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    // constructor
    public ColorPickerFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_color_picker, container, false);
        ButterKnife.bind(this, view);

        return view;
    }
}
