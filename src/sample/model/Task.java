package sample.model;

import java.sql.Timestamp;

public class Task extends User{

    private int userID;
    private int taskId;
    private Timestamp dateCreated;
    private String task;
    private String description;

    public Task() {
    }

    public Task(Timestamp dateCreated, String task, String description) {
        this.dateCreated = dateCreated;
        this.task = task;
        this.description = description;
    }

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }
}
