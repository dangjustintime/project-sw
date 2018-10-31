package com.example.justindang.storywell.utilities;

import android.support.v4.app.Fragment;

import com.example.justindang.storywell.free_templates.Template1Fragment;
import com.example.justindang.storywell.free_templates.Template2Fragment;
import com.example.justindang.storywell.free_templates.Template3Fragment;
import com.example.justindang.storywell.free_templates.Template4Fragment;
import com.example.justindang.storywell.free_templates.Template5Fragment;
import com.example.justindang.storywell.free_templates.Template6Fragment;

import java.util.HashMap;

public class TemplateManager {
  private HashMap<String, Fragment> hashMap;

  // constructor
  public TemplateManager() {
    hashMap = new HashMap<String, Fragment>();
    // free templates
    hashMap.put("free template 1", new Template1Fragment());
    hashMap.put("free template 2", new Template2Fragment());
    hashMap.put("free template 3", new Template3Fragment());
    hashMap.put("free template 4", new Template4Fragment());
    hashMap.put("free template 5", new Template5Fragment());
    hashMap.put("free template 6", new Template6Fragment());
  }

  // gets key from intent and returns fragment
  public Fragment getTemplate(String key) {
    return hashMap.get(key);
  }
}
