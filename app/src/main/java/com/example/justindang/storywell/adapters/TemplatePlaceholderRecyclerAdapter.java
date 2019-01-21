package com.example.justindang.storywell.adapters;

import android.app.Activity;
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
import com.example.justindang.storywell.model.Page;
import com.example.justindang.storywell.model.Stories;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TemplatePlaceholderRecyclerAdapter extends RecyclerView.Adapter<TemplatePlaceholderRecyclerAdapter.TemplateViewHolder> {

    Context context;
    Stories stories;

    // constructor
    public TemplatePlaceholderRecyclerAdapter(Context context, Stories stories) {
        this.context = context;
        this.stories = stories;
    }

    // view holder
    public static class TemplateViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.recycler_view_item_frame_layout_template_placeholder) FrameLayout templatePlaceholderFrameLayout;
        Page page;

        public TemplateViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    // inflater
    @NonNull
    @Override
    public TemplateViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_view_item_template_placeholder, viewGroup, false);
        return new TemplateViewHolder(view);
    }

    // binder
    @Override
    public void onBindViewHolder(@NonNull TemplateViewHolder templateViewHolder, int i) {
        templateViewHolder.page = stories.getPage();
    }

    @Override
    public int getItemCount() {
        if (stories == null) {
            return 0;
        }
        return stories.getNumPages();
    }
}
