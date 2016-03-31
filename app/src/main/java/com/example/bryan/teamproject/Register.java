package com.example.bryan.teamproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    Button signUp;
    ImageButton back;
    EditText userName, passWord, confirmPassWord, firstname, lastname, email;
    private Pattern pattern;
    private Matcher matcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        userName = (EditText) findViewById(R.id.username_input);
        passWord = (EditText) findViewById(R.id.password_input);
        confirmPassWord = (EditText) findViewById(R.id.confirmPassword_input);
        firstname = (EditText) findViewById(R.id.firstname_Input);
        lastname = (EditText) findViewById(R.id.lastname_input);
        email = (EditText) findViewById(R.id.Email_input);
        signUp = (Button) findViewById(R.id.SignUpbtn);
        back = (ImageButton) findViewById(R.id.back);

        View.OnClickListener handler = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //v.setEnabled(false);
                pattern = Pattern.compile(EMAIL_PATTERN);

                switch (v.getId()) {
                    case R.id.SignUpbtn:
                        String FirstName = userName.getText().toString();
                        String LastName = lastname.getText().toString();
                        String Username = userName.getText().toString();
                        String Email = email.getText().toString();
                        String Pass = passWord.getText().toString();
                        String confPass = confirmPassWord.getText().toString();
                        if (FirstName.length() == 0 || LastName.length() == 0 || Username.length() == 0 || Email.length() == 0 || Pass.length() == 0 || confPass.length() == 0) {
                            userName.requestFocus();
                            passWord.requestFocus();
                            confirmPassWord.requestFocus();
                            firstname.requestFocus();
                            lastname.requestFocus();
                            email.requestFocus();
                            userName.setError("FIELD CANNOT BE EMPTY, PLEASE ENTER Username");
                            passWord.setError("FIELD CANNOT BE EMPTY, PLEASE ENTER Password");
                            confirmPassWord.setError("FIELD CANNOT BE EMPTY, PLEASE ENTER Password");
                            firstname.setError("FIELD CANNOT BE EMPTY, PLEASE ENTER FirstName");
                            lastname.setError("FIELD CANNOT BE EMPTY, PLEASE ENTER LastName");
                            email.setError("FIELD CANNOT BE EMPTY, PLEASE ENTER YOUR Email");
                        } else if (FirstName.matches("[A-Z][a-zA-Z]*") || LastName.matches("[A-Z][a-zA-Z]*") || Username.matches("[A-Z][a-zA-Z]*")) {
                            firstname.requestFocus();
                            lastname.requestFocus();
                            userName.requestFocus();
                            firstname.setError("ENTER ONLY ALPHABETICAL CHARACTER");
                            lastname.setError("ENTER ONLY ALPHABETICAL  CHARACTER");
                            userName.setError("ENTER ONLY ALPHABETICAL  CHARACTER");
                        } else if (Pass.length() < 8 || confPass.length() < 8) {
                            passWord.requestFocus();
                            confirmPassWord.requestFocus();
                            passWord.setError("ENTER A PASSWORD OF AT LEAST 8 CHARACTERS");
                        } else if (!confPass.equals(Pass)) {
                            confirmPassWord.requestFocus();
                            confirmPassWord.setError("PASSWORDS ARE NOT MATCHING");
                        } else {
                            if (validateEmail(Email) == true) {

                                User registeredData = new User(FirstName, LastName, Username, Pass, Email);
                                registerNewUser(registeredData);
                                Toast.makeText(Register.this, "User has been Successful Added", Toast.LENGTH_LONG).show();
                            }
                        }
                        break;
                    case R.id.back:
                        goback();
                        break;
                }
            }
        };
        signUp.setOnClickListener(handler);
        back.setOnClickListener(handler);
    }

    private void goback() {

        startActivity(new Intent(this, MainActivity.class));
    }

    private boolean validateEmail(final String hex) {

        matcher = pattern.matcher(hex);
        return matcher.matches();

    }

    private void registerNewUser(User newData) {
        //User newMember = new User(newData);
        //newMember.addUser(newData);
        startActivity(new Intent(getApplicationContext(), signin.class));

    }

}