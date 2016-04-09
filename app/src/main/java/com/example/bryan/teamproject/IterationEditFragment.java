package com.example.bryan.teamproject;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View.OnClickListener;
import android.widget.Button;

public class IterationEditFragment extends Fragment implements OnClickListener{

    Button back_btn;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_iteration_edit, container, false);

        back_btn = (Button)view.findViewById(R.id.go_back_btn);
        back_btn.setOnClickListener(this);

        return view;

    }


    @Override
    public void onClick(View v) {

        switch(v.getId())
        {
            case R.id.go_back_btn:
                getActivity().getSupportFragmentManager().popBackStack();
                break;
        }

    }


    /* The method below has moved to the ProjectProfileActivity where it belongs */

//    public void showDatePickerDialog(View v) {
//        DialogFragment newFragment = new DatePickerFragment();
//
//        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
//    }

}
