package com.example.justindang.storywell;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ChooseATemplateFragment extends Fragment {

    // views
    @BindView(R.id.text_view_choose_a_template) TextView chooseATemplateTextView;
    @BindView(R.id.image_view_x_icon) ImageView xIconImageView;
    @BindView(R.id.image_view_shopping_cart_icon) ImageView shoppingCartIconImageView;
    @BindView(R.id.button_template1) Button template1Button;
    @BindView(R.id.button_template2) Button template2Button;
    @BindView(R.id.button_template3) Button template3Button;
    @BindView(R.id.button_template4) Button template4Button;
    @BindView(R.id.button_template5) Button template5Button;
    @BindView(R.id.button_template6) Button template6Button;

    public interface OnTemplateListener {
        public void sendTemplate(String template);
    }
    OnTemplateListener onTemplateListener;

    public ChooseATemplateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_choose_atemplate, container, false);
        ButterKnife.bind(this, view);

        // click listeners
        template1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onTemplateListener = (OnTemplateListener) getActivity();
                onTemplateListener.sendTemplate("template 1");
            }
        });
        template2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "template2", Toast.LENGTH_SHORT).show();
            }
        });
        template3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "template3", Toast.LENGTH_SHORT).show();
            }
        });
        template4Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "template4", Toast.LENGTH_SHORT).show();
            }
        });
        template5Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "template5", Toast.LENGTH_SHORT).show();
            }
        });
        template6Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "template6", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

}
