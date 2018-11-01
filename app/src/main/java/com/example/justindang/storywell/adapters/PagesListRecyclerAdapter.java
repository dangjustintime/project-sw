package com.example.justindang.storywell.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.justindang.storywell.R;
import com.example.justindang.storywell.activities.StoryEditorActivity;
import com.example.justindang.storywell.model.Stories;

import java.io.FileNotFoundException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PagesListRecyclerAdapter extends RecyclerView.Adapter<PagesListRecyclerAdapter.PageViewHolder> {

    // member data
    Context context;
    Stories stories;

    // constructor
    public PagesListRecyclerAdapter(Context context, Stories stories) {
        this.context = context;
        this.stories = stories;
    }

    // view holder
    public static class PageViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.constraint_layout_recycler_view_item_page) ConstraintLayout pageItemConstaintLayout;
        @BindView(R.id.text_view_recycler_view_page_number) TextView pageNumberTextView;
        @BindView(R.id.image_view_recycler_view_page_trash_can) ImageView trashCanImageView;

        public PageViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            pageNumberTextView.setVisibility(View.INVISIBLE);
        }
    }

    // inflater
    @NonNull
    @Override
    public PageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_view_item_page, viewGroup, false);
        return new PagesListRecyclerAdapter.PageViewHolder(view);
    }

    // binder
    @Override
    public void onBindViewHolder(@NonNull PageViewHolder pageViewHolder, int i) {
        Uri imageUri = Uri.parse(stories.getImageUris(i).get(0));
        InputStream inputStream;
        try {
            inputStream = context.getContentResolver().openInputStream(imageUri);
            Bitmap imageBitmap = BitmapFactory.decodeStream(inputStream);
            BitmapDrawable drawable = new BitmapDrawable(imageBitmap);
            pageViewHolder.pageItemConstaintLayout.setBackground(drawable);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        if (stories == null) {
            return 0;
        }
        return  stories.getNumPages();
    }


}
