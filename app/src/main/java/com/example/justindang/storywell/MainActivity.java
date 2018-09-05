package com.example.justindang.storywell;

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

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements CreateNewStoryDialogFragment.OnInputListener, ChooseATemplateFragment.OnTemplateListener {
    // Fragment Transaction
    FragmentTransaction fragmentTransaction;

    // views
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.constraint_layout_anywhere) ConstraintLayout constraintLayoutAnywhere;
    @BindView(R.id.frame_layout_fragment_placeholder) FrameLayout frameLayoutFragmentPlaceholder;

    // fragments
    CreateNewStoryDialogFragment createNewStoryDialogFragment = new CreateNewStoryDialogFragment();
    ChooseATemplateFragment chooseATemplateFragment = new ChooseATemplateFragment();
    Template1Fragment template1Fragment = new Template1Fragment();

    // variables
    Story newStory = new Story("","");

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
        newStory.setName(input);
        getSupportFragmentManager().beginTransaction().add(R.id.frame_layout_fragment_placeholder, chooseATemplateFragment).commit();
    }

    @Override
    public void sendTemplate(String template) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_fragment_placeholder, template1Fragment).commit();
    }

}
