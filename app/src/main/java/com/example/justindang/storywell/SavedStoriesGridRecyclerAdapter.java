package com.example.justindang.storywell;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.justindang.storywell.model.Story;

import java.io.File;
import java.util.List;

public class SavedStoriesGridRecyclerAdapter extends RecyclerView.Adapter<SavedStoriesGridRecyclerAdapter.SavedStoryViewHolder> {

    // member data
    Context context;
    List<Story> savedStoriesList;

    // constructor
    public SavedStoriesGridRecyclerAdapter(Context context, List<Story> savedStoriesList) {
        this.context = context;
        this.savedStoriesList = savedStoriesList;
    }

    public interface OnStoryListener {
        public void sendStory();
    }
    OnStoryListener onStoryListener;

    // view holder
    public static class SavedStoryViewHolder extends RecyclerView.ViewHolder {
        public TextView savedStoryNameTextView;
        public TextView savedStoryDateTextView;
        public ImageView savedStoryImageView;
        public ImageView savedStoryEditNameImageView;

        public SavedStoryViewHolder(@NonNull View itemView) {
            super(itemView);
            savedStoryNameTextView = (TextView) itemView.findViewById(R.id.text_view_template_name);
            savedStoryDateTextView = (TextView) itemView.findViewById(R.id.text_view_saved_story_date);
            savedStoryImageView = (ImageView) itemView.findViewById(R.id.image_view_saved_story);
            savedStoryEditNameImageView = (ImageView) itemView.findViewById(R.id.image_view_edit_saved_story_name);
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
        final Story savedStory = savedStoriesList.get(i);
        savedStoryViewHolder.savedStoryNameTextView.setText(savedStory.getName());
        savedStoryViewHolder.savedStoryDateTextView.setText(savedStory.getDate().toString());

        // get directory for the user's public pictures directory
        File pictureDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "storywell");

        // get image file
        File imageFile = new File(pictureDir, savedStory.getPicturePaths().get(i));

        // create bitmap
        if(imageFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
            savedStoryViewHolder.savedStoryImageView.setImageBitmap(myBitmap);
        }
    }

    @Override
    public int getItemCount() {
        if (savedStoriesList == null) {
            return 0;
        }
        return savedStoriesList.size();
    }
}
