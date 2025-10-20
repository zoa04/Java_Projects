package com.zoa.todo;
import java.time.LocalDateTime;
public class Task {
    private int id;
    private String title;
    private String description;
    private boolean done;
    private String createdAt;
    public Task() {}
    public Task(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description == null ? "" : description;
        this.done = false;
        this.createdAt = LocalDateTime.now().toString();
    }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public boolean isDone() { return done; }
    public void setDone(boolean done) { this.done = done; }
    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
    @Override
    public String toString() {
        return String.format("[%d] %s %s\n    %s", id, (done ? "✔" : " "), title, description);
    }
}