package com.example.justindang.storywell.utilities;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.example.justindang.storywell.free_templates.Template1Fragment;
import com.example.justindang.storywell.free_templates.Template2Fragment;
import com.example.justindang.storywell.free_templates.Template3Fragment;
import com.example.justindang.storywell.free_templates.Template4Fragment;
import com.example.justindang.storywell.free_templates.Template5Fragment;
import com.example.justindang.storywell.free_templates.Template6Fragment;
import com.example.justindang.storywell.views.FreeTemplate1View;
import com.example.justindang.storywell.views.FreeTemplate2View;
import com.example.justindang.storywell.views.TemplateView;

import java.util.HashMap;

public class TemplateManager {
    private HashMap<String, TemplateView> hashMap;
    private Context context;

    // constructor
    public TemplateManager(Context context) {
        this.context = context;
        hashMap = new HashMap<String, TemplateView>();

        // free templates
        hashMap.put("free template 1", new FreeTemplate1View(context));
        hashMap.put("free template 2", new FreeTemplate2View(context));
        hashMap.put("free template 3", null);
        hashMap.put("free template 4", null);
        hashMap.put("free template 5", null);
        hashMap.put("free template 6", null);
    }

    // gets key from intent and returns fragment
    public TemplateView getTemplate(String key) {
    return hashMap.get(key);
  }
}
