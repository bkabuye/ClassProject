package com.example.bryan.teamproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;
import android.view.View.OnClickListener;
import android.view.Menu;

public class ProfileActivity extends AppCompatActivity {

    private TextView greeting_msg_txtView;
    private Button requirement_tracker_btn;
    private Button comm_tool;
    private Button issue_tracker;
    userLocalStore localStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        greeting_msg_txtView = (TextView) findViewById(R.id.profile_greeting_msg_txtView);
        requirement_tracker_btn = (Button) findViewById(R.id.requirement_tracker_btn);
        comm_tool = (Button) findViewById(R.id.com_tool_btn);
        issue_tracker = (Button) findViewById(R.id.issue_tracker_btn);
        localStore = new userLocalStore(getApplicationContext());
        View.OnClickListener handler = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

           switch (v.getId()){
               case R.id.requirement_tracker_btn:
                   startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
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