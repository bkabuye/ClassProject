package com.example.bryan.teamproject;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.TabHost;

import com.google.tabmanager.TabManager;

public class ProjectEditFragment extends Fragment implements OnClickListener {

    private Button back_btn;
    private TabHost tabHost;
    private TabManager tabManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project_edit, container, false);


        back_btn = (Button) view.findViewById(R.id.go_back_btn);
        back_btn.setOnClickListener(this);
        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.go_back_btn:
                Log.i("INFO", "BACK button clicked.");
                getActivity().getSupportFragmentManager().popBackStack();
                break;
        }
    }
}