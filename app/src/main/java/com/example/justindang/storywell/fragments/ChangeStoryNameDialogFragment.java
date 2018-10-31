package com.example.justindang.storywell.fragments;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.justindang.storywell.R;
import com.example.justindang.storywell.adapters.SavedStoriesGridRecyclerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChangeStoryNameDialogFragment extends DialogFragment {

    // views
    @BindView(R.id.button_cancel_story_change_name) Button cancelButton;
    @BindView(R.id.button_change_story) Button changeButton;
    @BindView(R.id.edit_text_enter_a_new_name) EditText enterNewNameEditText;

    public interface OnChangeNameListener {
        void sendNewName(String newName);
    }

    public ChangeStoryNameDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_change_story_name_dialog, container, false);
        ButterKnife.bind(this, view);

        // clickListeners
        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // instantiate OnChangeNameListener
                OnChangeNameListener onChangeNameListener = (OnChangeNameListener) getActivity();

                // make toast if no input
                String storyName = enterNewNameEditText.getText().toString();
                if (storyName.equals("")) {
                    Toast.makeText(getContext(), "Enter Name", Toast.LENGTH_SHORT).show();
                } else {
                    onChangeNameListener.sendNewName(storyName);
                    dismiss();
                }
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enterNewNameEditText.setText("", TextView.BufferType.EDITABLE);
                dismiss();
            }
        });

        return view;
    }
}
