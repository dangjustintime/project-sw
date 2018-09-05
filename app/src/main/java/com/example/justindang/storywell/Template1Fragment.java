package com.example.justindang.storywell;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class Template1Fragment extends Fragment {

    public static final int IMAGE_GALLERY_REQUEST = 20;
    // views
    @BindView(R.id.image_view_inner_media) ImageView innerMediaImageView;
    @BindView(R.id.image_view_outter_media) ImageView outterMediaImageView;




    public Template1Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_template1, container, false);
        ButterKnife.bind(this, view);

        outterMediaImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoGalleryIntent = new Intent(Intent.ACTION_PICK);
                File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                String pictureDirectroyPath = pictureDirectory.getPath();
                Uri data = Uri.parse(pictureDirectroyPath);
                photoGalleryIntent.setDataAndType(data, "image/*");
                startActivityForResult(photoGalleryIntent, IMAGE_GALLERY_REQUEST);
            }

        });
        return view;
    }

}
