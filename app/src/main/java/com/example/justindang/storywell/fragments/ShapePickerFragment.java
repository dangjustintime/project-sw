package com.example.justindang.storywell.fragments;

import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.justindang.storywell.R;
import com.example.justindang.storywell.views.ShapeStickerView;
import com.example.justindang.storywell.views.StickerView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShapePickerFragment extends Fragment {

    // views
    @BindView(R.id.image_view_square_solid) ImageView squareSolidImageView;
    @BindView(R.id.image_view_square) ImageView squareImageView;
    @BindView(R.id.image_view_circle_solid) ImageView circleSolidImageView;
    @BindView(R.id.image_view_circle) ImageView circleImageView;
    @BindView(R.id.image_view_rectangle_solid) ImageView rectangleSolidImageView;
    @BindView(R.id.image_view_rectangle) ImageView rectangleImageView;

    // interface
    public interface OnShapeListener {
        void sendShape(@ShapeStickerView.Shape int shape, boolean isSolid);
    }
    OnShapeListener onShapeListener;

    // constructor
    public ShapePickerFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shape_picker, container, false);
        ButterKnife.bind(this, view);

        onShapeListener = (OnShapeListener) getActivity();

        // click listeners
        squareSolidImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onShapeListener.sendShape(ShapeStickerView.SQUARE, true);
            }
        });
        squareImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onShapeListener.sendShape(ShapeStickerView.SQUARE, false);
            }
        });
        circleSolidImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onShapeListener.sendShape(ShapeStickerView.CIRCLE, true);
            }
        });
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onShapeListener.sendShape(ShapeStickerView.CIRCLE, false);
            }
        });
        rectangleSolidImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onShapeListener.sendShape(ShapeStickerView.RECTANGLE, true);
            }
        });
        rectangleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onShapeListener.sendShape(ShapeStickerView.RECTANGLE, false);
            }
        });

        return view;
    }
}