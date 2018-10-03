package com.example.justindang.storywell.utilities;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {
    private SharedPreferences sharedPreferences;
    public SharedPrefManager(Context context) {
        context.getSharedPreferences("dfsd", 0);
    }
}
