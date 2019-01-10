package com.example.justindang.storywell.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.justindang.storywell.R;
import com.example.justindang.storywell.view_model.StoriesViewModel;

import java.util.List;

public class TemplateGridRecyclerAdapter extends RecyclerView.Adapter<TemplateGridRecyclerAdapter.TemplateViewHolder> {
    // member data
    private Context context;
    private List<String> templateNames;

    // view model
    private StoriesViewModel storiesViewModel;

    // constructor
    public TemplateGridRecyclerAdapter(Context context, List<String> templateNames) {
        this.context = context;
        this.templateNames = templateNames;
    }

    // interface listener
    public interface OnTemplateListener {
        void sendTemplate(String template);
    }
    OnTemplateListener onTemplateListener;

    // view inflater
    @NonNull
    @Override
    public TemplateViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_view_item_template, viewGroup, false);
        return new TemplateViewHolder(view);
    }

    // data binder
    @Override
    public void onBindViewHolder(TemplateViewHolder viewHolder, int index) {
        // set image resource according to index
        int imageResource = 0;
        final String templateName = templateNames.get(index);

        switch (index) {
            case 0:
                templateName.concat("1");
                imageResource = R.drawable.template_free_1;
                break;
            case 1:
                templateName.concat("2");
                imageResource = R.drawable.template_free_2;
                break;
            case 2:
                templateName.concat("3");
                imageResource = R.drawable.template_free_3;
                break;
            case 3:
                templateName.concat("4");
                imageResource = R.drawable.template_free_4;
                break;
            case 4:
                templateName.concat("5");
                imageResource = R.drawable.template_free_5;
                break;
            case 5:
                templateName.concat("6");
                imageResource = R.drawable.template_free_6;
                break;
        }

        viewHolder.templatePreview.setImageResource(imageResource);

        // bind clicklistener
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = (Activity) context;
                onTemplateListener = (OnTemplateListener) activity;
                onTemplateListener.sendTemplate(templateName);
                Toast.makeText(context, templateName, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (templateNames == null) {
            return 0;
        }
        return templateNames.size();
    }

    // view holder
    public static class TemplateViewHolder extends RecyclerView.ViewHolder {
        public ImageView templatePreview;

        public TemplateViewHolder(@NonNull View itemView) {
            super(itemView);
            templatePreview = (ImageView) itemView.findViewById(R.id.image_view_template_preview);
        }

    }
}
