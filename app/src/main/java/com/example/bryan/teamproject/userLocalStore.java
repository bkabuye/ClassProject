package com.example.bryan.teamproject;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Bryan on 3/21/16.
 */
public class userLocalStore {
    public static final String SP_NAME = "userDetails";
    static SharedPreferences userLocalDatabase;

    public userLocalStore(Context context) {
        userLocalDatabase = context.getSharedPreferences(SP_NAME, 0);
    }

    public static void storeUserData(User user) {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putString("username", user.username);
        spEditor.putString("password", user.passWord);
        spEditor.putString("token", user.token);
        spEditor.commit();
    }


    public User getLoggedInUser() {
        String username = userLocalDatabase.getString("username", "");
        String password = userLocalDatabase.getString("password","");
        String token = userLocalDatabase.getString("token", "");
        User storedUser = new User(username, password, token);
        return storedUser;
    }

    public User getNewUser(){
        String firstName = userLocalDatabase.getString("firstname", "");
        String lastName = userLocalDatabase.getString("lastname", "");
        String email = userLocalDatabase.getString("email", "");
        String username = userLocalDatabase.getString("username", "");
        String password = userLocalDatabase.getString("password","");
        User newUser = new User(firstName, lastName, email, username, password);
        return newUser;
    }

    public boolean getUserLoggedIn() {
        if (userLocalDatabase.getBoolean("loggedIn",false)==true) {
            return true;
        } else {
            return false;
        }
    }

    public static void setUserLoggedIn(boolean loggedIn) {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putBoolean("loggedIn", loggedIn);
        spEditor.commit();
    }

    public void clearUserData() {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.clear();
        spEditor.commit();
    }
}
