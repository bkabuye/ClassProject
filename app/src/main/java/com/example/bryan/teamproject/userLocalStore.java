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


    public static void storeUserData(ProjectUser projectUser) {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putString("username", projectUser.username);
        spEditor.putString("password", projectUser.passWord);
        spEditor.putString("token", projectUser.token);
        spEditor.commit();
    }


    public ProjectUser getLoggedInUser() {
        String username = userLocalDatabase.getString("username", "");
        String password = userLocalDatabase.getString("password","");
        String token = userLocalDatabase.getString("token", "");
        ProjectUser storedProjectUser = new ProjectUser(username, password, token);
        return storedProjectUser;
    }

    public ProjectUser getNewUser(){
        String firstName = userLocalDatabase.getString("firstname", "");
        String lastName = userLocalDatabase.getString("lastname", "");
        String email = userLocalDatabase.getString("email", "");
        String username = userLocalDatabase.getString("username", "");
        String password = userLocalDatabase.getString("password","");
        ProjectUser newProjectUser = new ProjectUser(firstName, lastName, email, username, password);
        return newProjectUser;
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

   /* public boolean authenticate(String username, String password){
        TokenTest test = new TokenTest(username, password);
        try {
            test.getToken();
            if(test.getTokenValue() != null){
           // System.out.println("passed: " + test.getTokenValue());
            ProjectUser projectUser = new ProjectUser(username, password, test.getTokenValue());
            storeUserData(projectUser);
            setUserLoggedIn(true);
                test.sendGet();
            return true;
            } else{
                System.out.println("failed get a token, projectUser not yet registered : " + test.getTokenValue());
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
    }*/
}
