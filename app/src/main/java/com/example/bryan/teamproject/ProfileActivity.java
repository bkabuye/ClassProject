package com.example.bryan.teamproject;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;

public class ProfileActivity extends AppCompatActivity {

    private TextView greeting_msg_txtView;
    private Button requirement_tracker_btn;
    private Button comm_tool;
    private Button issue_tracker;
    private Project project;
    private userLocalStore localStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        greeting_msg_txtView = (TextView) findViewById(R.id.profile_greeting_msg_txtView);
        requirement_tracker_btn = (Button) findViewById(R.id.requirement_tracker_btn);
        comm_tool = (Button) findViewById(R.id.com_tool_btn);
        issue_tracker = (Button) findViewById(R.id.issue_tracker_btn);
        localStore = new userLocalStore(getApplicationContext());
        project = new Project(getApplicationContext());
        View.OnClickListener handler = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

           switch (v.getId()){
               case R.id.requirement_tracker_btn:
                   ServerRequests requestData = new ServerRequests(getApplicationContext());
                   requestData.fetchProjectDataInBackground(localStore.getLoggedInUser(),new GetProjectCallback() {
                       @Override
                       public boolean done(Boolean returnedProjects) {
                           if (returnedProjects == false) {
                               showErrorMessage();
                               //return false;
                           } else {
                               startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                              // return true;
                           }
                    return false;
                       }
                   });
                    v.setEnabled(false);
                   break;
               case R.id.com_tool_btn:
                   v.setEnabled(false);
                   break;
               case R.id.issue_tracker_btn:
                   v.setEnabled(false);
                   break;
               }
            }
        };
        requirement_tracker_btn.setOnClickListener(handler);
        comm_tool.setOnClickListener(handler);
        issue_tracker.setOnClickListener(handler);
    }

    private void showErrorMessage() {
        AlertDialog.Builder dialogbuileder = new AlertDialog.Builder(this);
        dialogbuileder.setMessage("Missing projects");
        dialogbuileder.setPositiveButton("Ok", null);
        dialogbuileder.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(authenticate() == true){
            displayUserDetails();
        } else{
            startActivity(new Intent(getApplicationContext(), signin.class));
        }
    }

    private boolean authenticate(){
        return localStore.getUserLoggedIn();
    }

    private void displayUserDetails() {
        User user = localStore.getLoggedInUser();
        greeting_msg_txtView.setText("Welcome back,"+"\t" + user.username +"\t");
    }
}