package com.example.justindang.storywell;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements CreateNewStoryDialogFragment.OnInputListener,
        TemplateGridRecyclerAdapter.OnTemplateListener {
    // variables
    private String newStoryName;

    // ViewModel
    private static final String SID_KEY = "sid";

    // tags
    private static final String EXTRA_NAME = "name";
    private static final String EXTRA_TEMPLATE = "template";
    private static final String DIALOG_NEW_STORY = "create a new story";

    // Fragment Transaction
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    // views
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.constraint_layout_anywhere) ConstraintLayout constraintLayoutAnywhere;
    @BindView(R.id.frame_layout_fragment_placeholder) FrameLayout frameLayoutFragmentPlaceholder;
    @BindView(R.id.text_view_shared_preferences) TextView sharedPreferencesTextView;

    // fragments
    CreateNewStoryDialogFragment createNewStoryDialogFragment = new CreateNewStoryDialogFragment();
    ChooseATemplateFragment chooseATemplateFragment = new ChooseATemplateFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // get values from SharedPreferences
        SharedPreferences sharedPreferences = this.getSharedPreferences(getResources().getString(R.string.saved_stories), 0);
        Map<String, ?> map = sharedPreferences.getAll();
        sharedPreferencesTextView.setText(map.toString());

        // clickListeners
        constraintLayoutAnywhere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager = getSupportFragmentManager();
                createNewStoryDialogFragment.show(fragmentManager, DIALOG_NEW_STORY);
            }
        });
        setSupportActionBar(toolbar);
    }

    @Override
    public void sendInput(String input) {
        newStoryName = input;
        getSupportFragmentManager().beginTransaction().add(R.id.frame_layout_fragment_placeholder, chooseATemplateFragment).commit();
    }

    @Override
    public void sendTemplate(String template) {
        Intent intent = new Intent(MainActivity.this, StoryEditorActivity.class);
        intent.putExtra(EXTRA_NAME, newStoryName);
        intent.putExtra(EXTRA_TEMPLATE, template);
        startActivity(intent);
    }
}
