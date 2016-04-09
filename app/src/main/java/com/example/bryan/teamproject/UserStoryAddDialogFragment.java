package com.example.bryan.teamproject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.app.DialogFragment;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.view.View;
import android.content.DialogInterface.OnClickListener;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.util.Log;


public class UserStoryAddDialogFragment extends DialogFragment implements OnClickListener, OnSeekBarChangeListener{

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
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.fragment_user_story_add_dialog, null);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view)
                // Add action buttons
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
//                        ProjectAddDialogFragment.this.getDialog().cancel();
                    }
                })
                .setPositiveButton(R.string.create_btn, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // logics for creating a new porject
                    }
                })
                ;


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


        scalePointSeekBar.setOnSeekBarChangeListener(this);

        /* set up the status spinner */
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(), R.array.user_story_status_string_array, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.dropdown_items);
        statusSpinner.setAdapter(adapter);
        statusSpinner.setSelection(0);


        return builder.create();
    }


    @Override
    public void onClick(DialogInterface dialogInterface, int id) {

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
    {
        scalePointIndexTxtView.setText(Integer.toString(progress));
        //statusSpinner.requestFocus();
        /*
         *   here we set the hourEditText to be in focus because there is a little problem with the scalePointIndexTxtView when the progress
         *   is changed. The focus will alwasy move back to the User Story Title textfield. In order to solve the problem. The requestFocus()
         *   can not be applied to scalePointIndexTxtView and statusSpinner. Thus, the focus is set to the nearest focusable widget. In this
         *   case, it is hourEditText.
         */
        hourEditText.requestFocus();

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
