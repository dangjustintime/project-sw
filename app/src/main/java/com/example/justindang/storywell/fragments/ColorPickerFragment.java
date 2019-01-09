package com.example.justindang.storywell.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
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

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ColorPickerFragment extends Fragment {
    // views
    @BindView(R.id.color_picker) ColorPicker colorPicker;

    // view model
    // private StoriesViewModel storiesViewModel;

    // fragment manager
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    // colorpicker listener
    public interface ColorPickerListener {
        void getSelectedColor(int color);
    }
    ColorPickerListener colorPickerListener;

    // constructor
    public ColorPickerFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_color_picker, container, false);
        ButterKnife.bind(this, view);

        StoriesViewModel storiesViewModel = ViewModelProviders.of(getActivity()).get(StoriesViewModel.class);

        // color picker listener
        colorPicker.setGradientView(R.drawable.color_gradient);
        colorPicker.setColorSelectedListener(new ColorPicker.ColorSelectedListener() {
            @Override
            public void onColorSelected(int color, boolean isTapUp) {
                Stories stories = storiesViewModel.getStories().getValue();
                ArrayList<String> newColors = new ArrayList<>();
                newColors.add(String.valueOf(color));
                stories.setColors(0, newColors);
                storiesViewModel.setStories(stories);
            }
        });

        return view;
    }
}
