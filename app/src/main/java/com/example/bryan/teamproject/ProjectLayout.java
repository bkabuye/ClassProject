package com.example.bryan.teamproject;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.view.View.OnClickListener;
import android.content.Context;
import android.widget.CheckBox;
import android.widget.TextView;
import android.content.Intent;

/**
 * Created by ChihWu on 3/22/16.
 */
public class ProjectLayout extends RelativeLayout implements OnClickListener {

    private Context context;
    private CheckBox completed_checkBox;
    private TextView project_name_txtView;
    private TextView project_description_txtView;


    private Project project;

    public ProjectLayout(Context context)
    {
        super(context);
      // this.context = context;
    }

    public ProjectLayout(Context context, Project project)
    {
        super(context);

        this.context = context;

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.project, this, true);

        completed_checkBox = (CheckBox)findViewById(R.id.completed_checkBox);
        project_name_txtView = (TextView)findViewById(R.id.project_name_txtView);
        project_description_txtView = (TextView)findViewById(R.id.project_description_txtView);
        completed_checkBox.setOnClickListener(this);
        this.setOnClickListener(this);

        setProject(project);
    }

    // this method is used to check make sure the completed project are checked whenever the page loads.
    public void setProject(Project p)
    {
        project = p;

        /*
           More logics will be implemented here
         */
        project_name_txtView.setText(p.title);

        if(project.description.equalsIgnoreCase(""))
        {
            project_description_txtView.setVisibility(GONE);
        }
        else
        {
            project_description_txtView.setText(project.description);
        }

        if(project.getCompleted()=="1")
        {
            completed_checkBox.setChecked(true);
        }
        else
        {
            completed_checkBox.setChecked(false);
        }
    }

    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.completed_checkBox:
                break;
            default: //when user clicks anywhere except the checkbox, then we go the ProjectInfoActivity.java
                context.startActivity(new Intent(this.context, ProjectProfileActivity.class));
        }
    }
    /*
    @Override
    public void onResume()
    {
        super.onResume();
        refreshProjectList();
    }
    */

}
