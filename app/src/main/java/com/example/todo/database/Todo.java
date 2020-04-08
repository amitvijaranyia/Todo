package com.example.todo.database;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "todo")
public class Todo {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String heading;
    private String description;
    private String priority;
    private long updatedAt;

    public Todo(int id, String heading, String description, String priority, long updatedAt) {
        this.id = id;
        this.heading = heading;
        this.description = description;
        this.priority = priority;
        this.updatedAt = updatedAt;
    }

    @Ignore
    public Todo(String heading, String description, String priority, long updatedAt) {
        this.heading = heading;
        this.description = description;
        this.priority = priority;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }
}
