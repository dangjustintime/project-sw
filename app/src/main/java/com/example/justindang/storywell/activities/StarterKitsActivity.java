package com.example.justindang.storywell.activities;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.justindang.storywell.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StarterKitsActivity extends AppCompatActivity {

    // views
    @BindView(R.id.image_view_starter_kits_back_button) ImageView backButtonImageView;
    @BindView(R.id.constraint_layout_c01) ConstraintLayout c01ConstraintLayout;
    @BindView(R.id.constraint_layout_c02) ConstraintLayout c02ConstraintLayout;
    @BindView(R.id.constraint_layout_c03) ConstraintLayout c03ConstraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starter_kits);
        ButterKnife.bind(this);

        // clicklisteners
        backButtonImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        c01ConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(StarterKitsActivity.this, "C01 Starter Kit", Toast.LENGTH_SHORT).show();
            }
        });
        c02ConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(StarterKitsActivity.this, "C02 Starter Kit", Toast.LENGTH_SHORT).show();
            }
        });
        c03ConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(StarterKitsActivity.this, "C03 Starter Kit", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
