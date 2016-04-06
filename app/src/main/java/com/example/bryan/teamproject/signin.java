package com.example.bryan.teamproject;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class signin extends Activity {
    EditText userName, passWord;
    Button signIn;
    ImageButton backbutton;
    TextView forGot;
    userLocalStore localStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        userName = (EditText) findViewById(R.id.username_input);
        passWord = (EditText) findViewById(R.id.password_input);
        signIn = (Button) findViewById(R.id.submit);
        forGot = (TextView) findViewById(R.id.forgotPassword);
        backbutton = (ImageButton) findViewById(R.id.back_SignIn);
        localStore = new userLocalStore(getApplicationContext());

        View.OnClickListener handler = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.submit:
                        //v.setEnabled(false);
                       // userName.setEnabled(false);
                       // passWord.setEnabled(false);
                        String username = userName.getText().toString();
                        String password = passWord.getText().toString();
                        if (username.length() == 0) {
                            userName.requestFocus();
                            userName.setError("FIELD CANNOT BE EMPTY, PLEASE ENTER USERNAME");
                        } else if (!username.matches("[a-zA-Z]+")) {
                            userName.requestFocus();
                            userName.setError("ENTER ONLY ALPHABETICAL CHARACTER");
                        } else if (password.length() == 0) {
                            passWord.requestFocus();
                            passWord.setError("FIELD CANNOT BE EMPTY, PLEASE ENTER CORRECT PASSWORD");
                        } else {
                             User user = new User(username, password);
                             authenticate(user);
                        }
                        break;
                    case R.id.forgotPassword:
                        v.setEnabled(false);
                        // this to be filled in later
                        forgot();
                    case R.id.back_SignIn:
                        v.setEnabled(false);
                        //this to be filled in later
                        goback();
                }
            }
        };
        signIn.setOnClickListener(handler);
        forGot.setOnClickListener(handler);
        backbutton.setOnClickListener(handler);
    }

    private void authenticate(User user) {
        ServerRequests serverRequests = new ServerRequests(getApplicationContext());
        serverRequests.fetchUserDataInBackground(user, new GetUserCallback() {
            @Override
            public void done(User returnedUser) {

                if (returnedUser == null) {
                    showErrorMessage();
                    userLocalStore.setUserLoggedIn(false);
                    //startActivity(new Intent(getApplicationContext(), MainActivity.class));
                } else {
                    logUserIn(returnedUser);
                }
            }
        });
    }

    private void showErrorMessage() {
        AlertDialog.Builder dialogbuileder = new AlertDialog.Builder(this);
        dialogbuileder.setMessage("User could not be validated for a token");
        dialogbuileder.setPositiveButton("Ok", restart());
        dialogbuileder.show();

    }

    private DialogInterface.OnClickListener restart() {
        //startActivity(new Intent(getApplicationContext(), MainActivity.class));
        return null;
    }

    private void logUserIn(User returnedUser){
        userLocalStore.storeUserData(returnedUser);
        userLocalStore.setUserLoggedIn(true);
        startActivity(new Intent(this, ProfileActivity.class));
    }

    private void goback() {
        startActivity(new Intent(this, MainActivity.class));
    }

    private void forgot() {
        Log.i("Verify ", " Authetication");
    }
}
