package com.example.bryan.teamproject;

import android.content.Context;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import android.view.View;
import android.view.ViewGroup;
import android.util.Log;

/**
 * Created by ChihWu on 3/22/16.
 */
public class ProjectListAdapter extends BaseAdapter{

    private Context context;
    private ArrayList<Project> projects;

    public ProjectListAdapter(Context context, ArrayList<Project> projects)
    {
        this.context = context;
        this.projects = projects;
    }

    @Override
    public int getCount()
    {
        Log.i("ATTENTION : ",Integer.toString(5));
        return projects.size();
    }

    @Override
    public Object getItem(int position)
    {
        return projects.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ProjectLayout projectLayout = null;
        Project project = projects.get(position);

        if(convertView == null)
        {
            projectLayout = new ProjectLayout(context, project);
        }
        else
        {
            projectLayout = (ProjectLayout)convertView;
            projectLayout.setProject(project);
        }

        return projectLayout;
    }


}
