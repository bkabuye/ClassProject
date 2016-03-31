package com.example.bryan.teamproject;

/**
 * Created by ChihWu on 3/22/16.
 */
public class Project {

    public long projectId;
    public long listId;
    public String title;
    public String description;
    public String completed;
    public String hidden;
    public String owner;

    public static final String TRUE = "1";
    public static final String FALSE = "0";

    public Project() {
        title = "";
        description = "";
        owner = "";
        completed = FALSE;
        hidden = FALSE;
    }

    public Project(int listId, String name, String description, String owner
                ,String completed, String hidden) {
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

    public Project(int listId, String title, String description, String owner){
        this.title = title;
        this.description = description;
        this.owner = owner;
        this.projectId = -1;
        this.listId = listId;
        this.completed = "";
        this.hidden = "";

    }

    public long getId() {
        return projectId;
    }

    public void setId(long id) {
        this.projectId = id;
    }

    public long getListId() {
        return listId;
    }

    public void setListId(long listId) {
        this.listId = listId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    public String getCompleted() {
        return completed;
    }


    public String getHidden(){
        return hidden;
    }

    public void setHidden(String hidden) {
        this.hidden = hidden;
    }
}
