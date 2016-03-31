package com.example.bryan.teamproject;

/**
 * Created by Bryan on 3/20/16.
 */

import android.content.Context;
import android.util.Log;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.io.Console;

public class User {
    String Firstname, Lastname, username, passWord, email, token;
    Context context;
    TokenTest test;
    userLocalStore localstore;
    /**
     * method to store data
     *
     * @param FirstName
     * @param LastName
     * @param Username
     * @param password
     */
    public User(String FirstName, String LastName, String Username, String password, String Email) {
        this.username = Username;
        this.Firstname = FirstName;
        this.Lastname = LastName;
        this.passWord = password;
        this.email = Email;
    }

    /**
     * method to cater to already existing user
     *
     * @param Username
     * @param Password
     */
    public User(String Username, String Password, String token) {
        this.username = Username;
        this.passWord = Password;
        this.token = token;
    }


}
