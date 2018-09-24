package com.example.justindang.storywell;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateNewStoryDialogFragment extends DialogFragment {
    // fragment management
    FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;

    // views
    @BindView(R.id.edit_text_enter_a_name) EditText editTextEnterAName;
    @BindView(R.id.button_add_story) Button buttonAddStory;
    @BindView(R.id.button_cancel_story) Button buttonCancelStory;

    // variables
    private String storyName;

    // input listener
    public interface OnInputListener {
        void sendInput(String input);
    }
    // instantiate OnInputListener
    OnInputListener onInputListener;

    public CreateNewStoryDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_new_story, container, false);
        ButterKnife.bind(this, view);

        // clickListeners
        buttonAddStory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                storyName = editTextEnterAName.getText().toString();
                onInputListener = (OnInputListener) getActivity();
                if (storyName.equals("")) {
                    Toast.makeText(getContext(), "Enter Name", Toast.LENGTH_SHORT).show();
                } else {
                    onInputListener.sendInput(storyName);
                    dismiss();
                }
            }
        });
        buttonCancelStory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                storyName = "";
                editTextEnterAName.setText("", TextView.BufferType.EDITABLE);
                dismiss();
            }
        });
        return view;
    }
}
