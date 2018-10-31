package com.example.justindang.storywell.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.justindang.storywell.R;
import com.example.justindang.storywell.activities.StoryEditorActivity;
import com.example.justindang.storywell.model.Stories;

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
        @BindView(R.id.frame_layout_recycler_view_page_item_container) FrameLayout pageItemFrameLayout;

        public PageViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
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
    }

    @Override
    public int getItemCount() {
        if (stories == null) {
            return 0;
        }
        return  stories.getNumPages();
    }
}
