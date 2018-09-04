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

public class MainActivity extends AppCompatActivity implements CreateNewStoryDialogFragment.OnInputListener {
    // Fragment Transaction
    FragmentTransaction fragmentTransaction;

    // views
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.constraint_layout_anywhere) ConstraintLayout constraintLayoutAnywhere;
    @BindView(R.id.frame_layout_fragment_placeholder) FrameLayout frameLayoutFragmentPlaceholder;

    // fragments
    CreateNewStoryDialogFragment createNewStoryDialogFragment = new CreateNewStoryDialogFragment();

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
                /*
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.frame_layout_fragment_placeholder, new CreateNewStoryDialogFragment());
                fragmentTransaction.addToBackStack("create new story");
                fragmentTransaction.commit();
                */
            }
        });
        setSupportActionBar(toolbar);
    }

    @Override
    public void sendInput(String input) {
        newStory.setName(input);
        Toast.makeText(this, newStory.getName(), Toast.LENGTH_SHORT).show();
    }
}
