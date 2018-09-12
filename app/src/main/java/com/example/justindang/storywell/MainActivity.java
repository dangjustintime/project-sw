package com.example.justindang.storywell;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.justindang.storywell.model.Story;

import java.io.FileNotFoundException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements CreateNewStoryDialogFragment.OnInputListener, ChooseATemplateFragment.OnTemplateListener {
    // variables
    private String newStoryName;

    // tags
    private static final String EXTRA_NAME = "name";

    // Fragment Transaction
    FragmentTransaction fragmentTransaction;

    // views
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.constraint_layout_anywhere) ConstraintLayout constraintLayoutAnywhere;
    @BindView(R.id.frame_layout_fragment_placeholder) FrameLayout frameLayoutFragmentPlaceholder;

    // fragments
    CreateNewStoryDialogFragment createNewStoryDialogFragment = new CreateNewStoryDialogFragment();
    ChooseATemplateFragment chooseATemplateFragment = new ChooseATemplateFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        // set onClickListener
        constraintLayoutAnywhere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewStoryDialogFragment.show(getSupportFragmentManager(), "create a new story");
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
        startActivity(intent);
    }
}
