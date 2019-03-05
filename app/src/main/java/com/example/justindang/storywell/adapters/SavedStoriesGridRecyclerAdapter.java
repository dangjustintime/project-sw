package com.example.justindang.storywell.adapters;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
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
import com.example.justindang.storywell.activities.MainActivity;
import com.example.justindang.storywell.activities.StoryEditorActivity;
import com.example.justindang.storywell.model.Stories;
import com.example.justindang.storywell.utilities.ImageHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SavedStoriesGridRecyclerAdapter extends RecyclerView.Adapter<SavedStoriesGridRecyclerAdapter.SavedStoryViewHolder> {

    // tags
    private static final String EXTRA_IS_NEW_STORIES = "new stories";
    private static final String EXTRA_SAVED_STORIES = "saved stories";

    // member data
    Context context;
    List<Stories> savedStoriesList;

    // interface
    public interface OnItemListener {
        void getNewName(int position);
    }
    OnItemListener onItemListener;

    // constructor
    public SavedStoriesGridRecyclerAdapter(Context context, List<Stories> savedStoriesList) {
        this.context = context;
        this.savedStoriesList = savedStoriesList;
    }

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

            // hide delete and change name buttons
            toggleDeleteImageView();
            toggleEditNameImageView();
        }

        public void toggleEditNameImageView () {
            if (savedStoryEditNameImageView.getVisibility() == View.VISIBLE) {
                savedStoryEditNameImageView.setVisibility(View.INVISIBLE);
            } else {
                savedStoryEditNameImageView.setVisibility(View.VISIBLE);
            }
        }

        public void toggleDeleteImageView() {
            if (savedStoryDeleteImageView.getVisibility() == View.VISIBLE) {
                savedStoryDeleteImageView.setVisibility(View.INVISIBLE);
            } else {
                savedStoryDeleteImageView.setVisibility(View.VISIBLE);
            }
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
    public void onBindViewHolder(@NonNull final SavedStoryViewHolder savedStoryViewHolder, int i) {
        final Stories savedStories = savedStoriesList.get(i);
        savedStoryViewHolder.savedStoryNameTextView.setText(savedStories.getName());
        savedStoryViewHolder.savedStoryDateTextView.setText(savedStories.getDate());

        // put Uri into imageView
        Uri imageUri = Uri.parse(savedStories.getImageUris(0).get(0));
        ImageHandler.setImageToImageView(context, imageUri, savedStoryViewHolder.savedStoryImageView, ImageView.ScaleType.CENTER_CROP);

        // clicklisteners
        savedStoryViewHolder.savedStoryContainerConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, StoryEditorActivity.class);
                intent.putExtra(EXTRA_SAVED_STORIES, savedStories);
                intent.putExtra(EXTRA_IS_NEW_STORIES, false);
                context.startActivity(intent);
            }
        });
        savedStoryViewHolder.savedStoryEditNameImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemListener = (OnItemListener) context;
                onItemListener.getNewName(savedStoryViewHolder.getAdapterPosition());
            }
        });
        savedStoryViewHolder.savedStoryDeleteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteStories(savedStoryViewHolder.getAdapterPosition());
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

    public void changeName(int postion, String newName) {
        // change name of page in list
        Stories stories = savedStoriesList.get(postion);
        stories.setName(newName);
        savedStoriesList.set(postion, stories);

        // update shared pref
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getResources().getString(R.string.saved_stories), 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(stories.getSharedPrefKey() + "_name", newName);
        editor.commit();
        notifyItemChanged(postion);
    }

    public void deleteStories(int position) {
        // remove story from shared pref
        String sharedPrefKey = savedStoriesList.get(position).getSharedPrefKey();
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getResources().getString(R.string.saved_stories), 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int numPages = sharedPreferences.getInt(sharedPrefKey + "_num_pages", 1);
        editor.remove(sharedPrefKey + "_num_pages");
        editor.remove(sharedPrefKey + "_name");
        editor.remove(sharedPrefKey + "_date");
        for (int i = 0; i < numPages; i++) {
            String pageKey = sharedPrefKey + "_" + String.valueOf(i);
            for (int j = 0; j < 9; j++) {
                editor.remove(pageKey + "_image_uri_" + String.valueOf(j));
                editor.remove(pageKey + "_color_" + String.valueOf(j));
            }
            editor.remove(pageKey + "_template");
            editor.remove(pageKey + "_title");
            editor.remove(pageKey + "_text");
        }
        int numStories = sharedPreferences.getInt(context.getResources().getString(R.string.saved_num_stories_keys), 0);
        editor.putInt(context.getResources().getString(R.string.saved_num_stories_keys), --numStories);
        editor.commit();

        // remove page from saved stories list
        savedStoriesList.remove(position);
        notifyItemRemoved(position);
    }
}
