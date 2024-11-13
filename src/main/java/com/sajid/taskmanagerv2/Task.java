package com.sajid.taskmanagerv2;

import java.time.LocalDate;

public class Task {
    String assignedTo;
    String assignedBy;
    String title;
    String description;
    LocalDate due;
    String status;

    public Task(String assignedTo, String assignedBy, String title, String description, LocalDate due, String status) {
        this.assignedTo = assignedTo;
        this.assignedBy = assignedBy;
        this.title = title;
        this.description = description;
        this.due = due;
        this.status = status;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getAssignedBy() {
        return assignedBy;
    }

    public void setAssignedBy(String assignedBy) {
        this.assignedBy = assignedBy;
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

    public LocalDate getDue() {
        return due;
    }

    public void setDue(LocalDate due) {
        this.due = due;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
