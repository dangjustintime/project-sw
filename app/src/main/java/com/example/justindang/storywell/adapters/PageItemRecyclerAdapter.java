package com.example.justindang.storywell.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.justindang.storywell.R;
import com.example.justindang.storywell.model.Stories;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PageItemRecyclerAdapter extends RecyclerView.Adapter<PageItemRecyclerAdapter.PageItemViewHolder> {
    private Stories stories;
    private Context context;

    // constructor
    public PageItemRecyclerAdapter(Context context, Stories stories) {
        this.context = context;
        this.stories = new Stories(stories);
    }

    // view holder
    public static class PageItemViewHolder extends RecyclerView.ViewHolder {
        FrameLayout fragmentPlaceholderPageItemFrameLayout;
        public PageItemViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    // inflater
    @Override
    public PageItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.page_view_item, viewGroup, false);
        return new PageItemRecyclerAdapter.PageItemViewHolder(view);
    }

    // binder
    @Override
    public void onBindViewHolder(@NonNull PageItemViewHolder pageItemViewHolder, int i) {
        final int itemId = stories.getPage().hashCode();
        pageItemViewHolder.fragmentPlaceholderPageItemFrameLayout.setId(itemId);
    }

    @Override
    public int getItemCount() {
        if (this.stories == null) {
            return 0;
        }
        return this.stories.getNumPages();
    }

    public Stories getStories() {
        return this.stories;
    }
}