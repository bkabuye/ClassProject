package com.example.bryan.teamproject;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ListView;
import java.util.ArrayList;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.widget.Toast;

import java.lang.reflect.Array;

public class DashboardActivity extends Activity implements OnClickListener, OnTouchListener{

    private ImageButton project_add_btn;
    private ListView project_listView;
    private userLocalStore localStore;
    private  Project project;
    private ArrayList<Project> projects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        project_add_btn = (ImageButton)findViewById(R.id.project_add_button);
        project_add_btn.setOnClickListener(this);
        project_listView = (ListView)findViewById(R.id.project_listView);
        localStore = new userLocalStore(getApplicationContext());
        projects =  new ArrayList<Project>();
         project =  new Project();
         refreshProjectList();
    }

    public void refreshProjectList()
    {
        Context context = getApplicationContext();
        projects = new ArrayList<Project>();
        for(int i=0; i < project.getOwner().size(); i++){
            Project p1 = new Project(project.getProjectId().get(i), project.getTitle().get(i), project.getDescription().get(i), "1", "0");
            projects.add(p1);
        }

        ProjectListAdapter adapter = new ProjectListAdapter(context, projects);
        project_listView.setAdapter(adapter);

        Toast.makeText(this,Integer.toString(projects.size()), Toast.LENGTH_SHORT ).show();
    }

    @Override
    public void onPause()
    {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.profile_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.profile_info:
                return true;
            case R.id.change_password:
                return true;
            case R.id.log_out:
                localStore.clearUserData();
                project.getDescription().clear();
                project.getTitle().clear();
                project.getOwner().clear();
                project.getProjectId().clear();
                startActivity(new Intent(this, MainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @TargetApi(11)
    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.project_add_button:
                this.project_add_btn.getBackground().setColorFilter(0x000, PorterDuff.Mode.SRC_ATOP);
                this.project_add_btn.invalidate();
                Log.i("ATTENTION", "We are here!!!!");

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                DialogFragment newFragment = new ProjectAddFragment();

                newFragment.show(ft, "add new project");
                break;
            //case android.R.id.home :

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        projects.clear();
        project.getDescription().clear();
        project.getTitle().clear();
        project.getOwner().clear();
        project.getProjectId().clear();
        startActivity(new Intent(this, ProfileActivity.class));
    }


    @Override
    public boolean onTouch(View v, MotionEvent event)
    {

        switch(v.getId())
        {
            case R.id.project_add_button:

                return true;

            default:
                return true;
        }
    }


}
