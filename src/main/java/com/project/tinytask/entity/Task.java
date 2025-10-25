package com.project.tinytask.entity;

public class Task {

    private int id;
    private boolean done;
    private String title;

    public Task(int id, String title) {
        this.title = title;
        this.id = id;
        setDone(false);
    }
    public Task() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

}
