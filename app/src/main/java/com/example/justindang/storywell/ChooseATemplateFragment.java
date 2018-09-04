package com.example.justindang.storywell;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChooseATemplateFragment extends Fragment {

    // views
    @BindView(R.id.text_view_choose_a_template) TextView chooseATemplateTextView;
    @BindView(R.id.image_view_x_icon) ImageView xIconImageView;
    @BindView(R.id.image_view_shopping_cart_icon) ImageView shoppingCartIconImageView;


    public ChooseATemplateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_choose_atemplate, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

}
