package com.example.justindang.storywell.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.justindang.storywell.R;

import java.util.List;

public class TemplateGridRecyclerAdapter extends RecyclerView.Adapter<TemplateGridRecyclerAdapter.TemplateViewHolder> {
    // member data
    private Context context;
    private List<String> templateNames;

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
        final String templateName = templateNames.get(index);
        viewHolder.templateName.setText(templateName);

        // bind clicklistener
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = (Activity) context;
                onTemplateListener = (OnTemplateListener) activity;
                onTemplateListener.sendTemplate(templateName);
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
        public TextView templateName;

        public TemplateViewHolder(@NonNull View itemView) {
            super(itemView);
            templateName = (TextView) itemView.findViewById(R.id.text_view_template_name);
        }

    }
}
