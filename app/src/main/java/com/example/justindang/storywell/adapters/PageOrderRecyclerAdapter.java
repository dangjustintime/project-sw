package com.example.justindang.storywell.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.justindang.storywell.R;
import com.example.justindang.storywell.activities.StoryEditorActivity;
import com.example.justindang.storywell.model.Page;
import com.example.justindang.storywell.model.Stories;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PageOrderRecyclerAdapter extends RecyclerView.Adapter<PageOrderRecyclerAdapter.PageViewHolder> {

    // member data
    private Context context;
    private Stories stories;
    private ArrayList<Page> newOrderPageList;
    private int currentIndex;

    // constructor
    public PageOrderRecyclerAdapter(Context context, Stories stories) {
        this.context = context;
        this.stories = stories;
        this.currentIndex = 0;
        this.newOrderPageList = new ArrayList<>();
    }

    // view holder
    public static class PageViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.constraint_layout_recycler_view_item_page) ConstraintLayout pageItemConstraintLayout;
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
        return new PageOrderRecyclerAdapter.PageViewHolder(view);
    }

    // binder
    @Override
    public void onBindViewHolder(@NonNull final PageViewHolder pageViewHolder, final int i) {
        Uri imageUri = Uri.parse(stories.getImageUris(i).get(0));
        InputStream inputStream;
        try {
            inputStream = context.getContentResolver().openInputStream(imageUri);
            Bitmap imageBitmap = BitmapFactory.decodeStream(inputStream);
            Matrix matrix = new Matrix();
            matrix.postRotate(90);
            Bitmap rotatedBitmap = Bitmap.createBitmap(imageBitmap, 0, 0,
                    imageBitmap.getWidth(), imageBitmap.getHeight(), matrix, true);
            BitmapDrawable drawable = new BitmapDrawable(rotatedBitmap);
            pageViewHolder.pageItemConstraintLayout.setBackground(drawable);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // clicklistener
        pageViewHolder.pageItemConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pageViewHolder.pageNumberTextView.getVisibility() == View.INVISIBLE) {
                    pageViewHolder.pageNumberTextView.setText(String.valueOf(currentIndex + 1));
                    newOrderPageList.add(stories.getPage(i));
                    currentIndex++;
                    pageViewHolder.pageNumberTextView.setVisibility(View.VISIBLE);
                }
            }
        });
        pageViewHolder.trashCanImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stories.removePageByIndex(i);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (stories == null) {
            return 0;
        }
        return stories.getNumPages();
    }

    // getters
    public Stories getStories() {
        stories.setPagesList(newOrderPageList);
        return this.stories;
    }

    public boolean allPagesSelected() {
        if (newOrderPageList.size() == stories.getNumPages()) {
            return true;
        }
        return false;
    }
}
