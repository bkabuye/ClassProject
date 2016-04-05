package com.example.bryan.teamproject;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ChihWu on 3/22/16.
 */
public class Project {

    public int projectId;
    public int listId;
    public static ArrayList<String> Title = new ArrayList<String>();
    public static ArrayList<String> Description = new ArrayList<String>();
    public static ArrayList<String> Owner = new ArrayList<String>();
    public static ArrayList<Integer> ProjectId = new ArrayList<Integer>();
    public String title;
    public String description;
    public String completed;
    public String hidden;
    public String owner;

    public static final String TRUE = "1";
    public static final String FALSE = "0";

    static SharedPreferences projectLocalDatabase;
    public static final String SP_NAME = "ProjectDetails";

    public Project(Context context) {
        projectLocalDatabase = context.getSharedPreferences(SP_NAME, 0);

    }

    public Project() {
        title = "";
        description = "";
        owner = "";
        completed = FALSE;
        hidden = FALSE;
    }

    public Project(int listId, String name, String description, String owner
            , String completed, String hidden) {
        this.listId = listId;
        this.title = name;
        this.description = description;
        this.owner = owner;
        this.completed = completed;
        this.hidden = hidden;

    }

    public Project(int taskId, String name, String description, String owner,
                   String completed) {
        this.projectId = taskId;
        this.listId = listId;
        this.title = name;
        this.description = description;
        this.owner = owner;
        this.completed = completed;
        this.hidden = hidden;
    }

    public Project(int listId, String title, String description) {
        this.title = title;
        this.description = description;
        this.owner = "";
        this.projectId = -1;
        this.listId = listId;
        this.completed = completed;
        this.hidden = hidden;
    }

    public long getId() {
        return projectId;
    }

    public ArrayList<Integer> getProjectId(){
        return ProjectId;
    }

    public void setId(int id) {
        this.projectId = id;
        ProjectId.add(projectId);
    }

    public long getListId() {
        return listId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }

    public List<String> getTitle() {
        return Title;
    }

    public List<String> getDescription() {

        return Description;
    }

    public void setTitle(String values) {
        title = values;
        Title.add(title);
    }

    public void setDescription(String descriptions) {
        description = descriptions;
        Description.add(description);
    }

    public void setOwner(String owners) {
        owner = owners;
        Owner.add(owner);
    }

    public List<String> getOwner() {

        return Owner;
    }

    public String getCompleted() {
        return completed;
    }


    public String getHidden() {
        return hidden;
    }

    public void setHidden(String hidden) {
        this.hidden = hidden;
    }

    public static boolean getLoaded() {
        if (projectLocalDatabase.getBoolean("loaded", false) == true) {
            return true;
        } else {
            return false;
        }
    }

    public void setLoadedInfo(boolean loaded) {
        SharedPreferences.Editor spEditor = projectLocalDatabase.edit();
        spEditor.putBoolean("loaded", loaded);
        spEditor.commit();
    }
}
