package com.example.justindang.storywell.adapters;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.justindang.storywell.R;
import com.example.justindang.storywell.activities.StoryEditorActivity;
import com.example.justindang.storywell.model.Stories;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SavedStoriesGridRecyclerAdapter extends RecyclerView.Adapter<SavedStoriesGridRecyclerAdapter.SavedStoryViewHolder> {

    // member data
    Context context;
    List<Stories> savedStoriesList;

    // constructor
    public SavedStoriesGridRecyclerAdapter(Context context, List<Stories> savedStoriesList) {
        this.context = context;
        this.savedStoriesList = savedStoriesList;
    }

    public interface OnStoryListener {
        public void sendStory();
    }
    OnStoryListener onStoryListener;

    // view holder
    public static class SavedStoryViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_view_recycler_view_saved_story_name) TextView savedStoryNameTextView;
        @BindView(R.id.text_view_recycler_view_saved_story_date) TextView savedStoryDateTextView;
        @BindView(R.id.image_view_recycler_view_saved_story_image) ImageView savedStoryImageView;
        @BindView(R.id.image_view_recycler_view_edit_saved_story_name) ImageView savedStoryEditNameImageView;
        @BindView(R.id.constraint_layout_recycler_view_saved_story_container) ConstraintLayout savedStoryContainerConstraintLayout;
        @BindView(R.id.image_view_recycler_view_delete) ImageView savedStoryDeleteImageView;

        public SavedStoryViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    // inflater
    @NonNull
    @Override
    public SavedStoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_view_item_saved_story, viewGroup, false);
        return new SavedStoriesGridRecyclerAdapter.SavedStoryViewHolder(view);
    }

    // binder
    @Override
    public void onBindViewHolder(@NonNull SavedStoryViewHolder savedStoryViewHolder, int i) {
        final Stories savedStories = savedStoriesList.get(i);
        savedStoryViewHolder.savedStoryNameTextView.setText(savedStories.getName());
        savedStoryViewHolder.savedStoryDateTextView.setText(savedStories.getDate());

        Uri imageUri = Uri.parse(savedStories.getImageUris(0).get(0));
        InputStream inputStream = null;
        try {
            inputStream = context.getContentResolver().openInputStream(imageUri);
            Bitmap imageBitmap = BitmapFactory.decodeStream(inputStream);
            savedStoryViewHolder.savedStoryImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            savedStoryViewHolder.savedStoryImageView.setImageBitmap(imageBitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // clicklisteners
        savedStoryViewHolder.savedStoryContainerConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, savedStories.getName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, StoryEditorActivity.class);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (savedStoriesList == null) {
            return 0;
        }
        return savedStoriesList.size();
    }
}
