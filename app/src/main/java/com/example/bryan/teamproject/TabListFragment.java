package com.example.bryan.teamproject;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TextView;


public class TabListFragment extends Fragment {

    private TextView tab_page_txtView;
    private String currentTabTag;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        tab_page_txtView = (TextView)view.findViewById(R.id.tab_page_msg);

        TabHost tabHost = (TabHost)container.getParent().getParent();
        currentTabTag = tabHost.getCurrentTabTag();

        refreshPage();

        return view;
    }


    public void refreshPage()
    {
        String text = "This is "+currentTabTag+" tab's content.";
        tab_page_txtView.setText(text);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        refreshPage();
    }

}
