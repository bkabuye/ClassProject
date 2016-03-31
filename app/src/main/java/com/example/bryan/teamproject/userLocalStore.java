package com.example.bryan.teamproject;

import android.content.Context;
import android.content.SharedPreferences;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
        spEditor.putString("firstname", user.Firstname);
        spEditor.putString("lastname", user.Lastname);
        spEditor.putString("username", user.username);
        spEditor.putString("password", user.passWord);
        spEditor.putString("email", user.email);
        spEditor.putString("token", user.token);
        spEditor.commit();
    }

    public User getLoggedInUser() {
        String firstName = userLocalDatabase.getString("firstname", "");
        String lastName = userLocalDatabase.getString("lastname", "");
        String email = userLocalDatabase.getString("email", "");
        String username = userLocalDatabase.getString("username", "");
        String password = userLocalDatabase.getString("password","");
        String token = userLocalDatabase.getString("token", "");
        User storedUser = new User(username, password, token);
        return storedUser;
    }

    public Project getProjectData(){
        String title = userLocalDatabase.getString("title", "");
        String description = userLocalDatabase.getString("description", "");
        String owner = userLocalDatabase.getString("owner", "");
        long listId = userLocalDatabase.getLong("listid", -1);
        long projectId = userLocalDatabase.getLong("projectId", -1);
        String completed = userLocalDatabase.getString("completed", "1");
        String hidden = userLocalDatabase.getString("hidden", "1");
        Project project = new Project((int)listId, title, description, owner, completed, hidden);
        return project;
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

    public static void setUserLoggedIn(boolean temp) {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putBoolean("loggedIn", temp);
        spEditor.commit();
    }


    public void clearUserData() {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.clear();
        spEditor.commit();
    }

    public boolean authenticate(String username, String password){
        TokenTest test = new TokenTest(username, password);
        try {
            test.getToken();
            if(test.getTokenValue() != null){
           // System.out.println("passed: " + test.getTokenValue());
            User user = new User(username, password, test.getTokenValue());
            storeUserData(user);
            setUserLoggedIn(true);
                test.sendGet();
            return true;
            } else{
                System.out.println("failed get a token, user not yet registered : " + test.getTokenValue());
                System.exit(0);
            }

        }

        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }
    public static void setProjects(JSONArray array){
        JSONArray arrays = array;
        try {
            for (int i = 0; i < arrays.length(); i++) {
                JSONObject c = array.getJSONObject(i);
                String title = c.getString("title");
                String description = c.getString("description");
                String owner = c.getString("owner");
                System.out.println(" "+title+" \n " + description+" \n \t"+owner+"\n");
                Project _project = new Project(i, title, description, owner);
            }
        }
        catch(Exception e){

            System.out.println(e.fillInStackTrace());
        }
    }
}
