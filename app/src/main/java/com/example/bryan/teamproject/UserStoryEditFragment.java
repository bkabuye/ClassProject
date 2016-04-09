package com.example.bryan.teamproject;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;


public class UserStoryEditFragment extends Fragment implements OnClickListener, OnSeekBarChangeListener{

    private Button backButton;
    private EditText titleEditText;
    private EditText descriptionEditText;
    private EditText reasonEditText;
    private EditText testEditText;
    private Spinner ownerSpinner;
    private EditText hourEditText;
    private EditText minuteEditText;
    private EditText secondEditText;
    private Spinner statusSpinner;
    private SeekBar scalePointSeekBar;
    private TextView scalePointIndexTxtView;
    private CheckBox pausedCheckBox;
    private Spinner reassignSpinner;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_story, container, false);

        backButton = (Button)view.findViewById(R.id.go_back_btn);
        titleEditText = (EditText)view.findViewById(R.id.user_story_title_editText);
        descriptionEditText = (EditText)view.findViewById(R.id.user_story_description_editText);
        reasonEditText = (EditText)view.findViewById(R.id.user_story_reason_editText);
        testEditText = (EditText)view.findViewById(R.id.user_story_test_editText);
        ownerSpinner = (Spinner)view.findViewById(R.id.user_story_owner_spinner);
        hourEditText = (EditText)view.findViewById(R.id.user_story_hour_editView);
        minuteEditText = (EditText)view.findViewById(R.id.user_story_minute_editView);
        secondEditText = (EditText)view.findViewById(R.id.user_story_second_editView);
        statusSpinner = (Spinner)view.findViewById(R.id.user_story_status_spinner);
        scalePointSeekBar = (SeekBar)view.findViewById(R.id.user_story_scale_point_seekbar);
        scalePointIndexTxtView = (TextView)view.findViewById(R.id.user_story_scale_point_value_txtView);
        pausedCheckBox = (CheckBox)view.findViewById(R.id.user_story_pause_checkbox);
        reassignSpinner = (Spinner)view.findViewById(R.id.user_story_reassign_spinner);

        backButton.setOnClickListener(this);
        scalePointSeekBar.setOnSeekBarChangeListener(this);

        /* set up the status spinner */
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(), R.array.user_story_status_string_array, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.dropdown_items);
        statusSpinner.setAdapter(adapter);
        statusSpinner.setSelection(0);

        return view;
    }


    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.go_back_btn:
                getActivity().getSupportFragmentManager().popBackStack();
                break;
        }
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
    {
        scalePointIndexTxtView.setText(Integer.toString(progress));
    }

    @Override
    public void onStartTrackingTouch (SeekBar seekBar)
    {

    }

    @Override
    public void onStopTrackingTouch (SeekBar seekBar)
    {

    }
}