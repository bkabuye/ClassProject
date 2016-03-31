package com.example.bryan.teamproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button signIn, signUp;
    String selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        signIn = (Button) findViewById(R.id.Login);
        signUp = (Button) findViewById(R.id.SignUp);
        View.OnClickListener handler = new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                switch(v.getId()){
                case R.id.Login:
                    startActivity(new Intent(MainActivity.this, signin.class));
                    Log.i("Content", "Signin home");
                break;
                    case R.id.SignUp:
                        startActivity(new Intent(MainActivity.this, Register.class));
                        Log.i("Content", "SignUp home");
                break;
                }
            }
        };
        signUp.setOnClickListener(handler);
        signIn.setOnClickListener(handler);
    }
}
