package com.platform.core;

/**
 * Domain model and individual pointer Node representing a corporate task.
 */
public class Task {
    private final String taskId;
    private final String taskName;
    private final String status;
    protected Task next; // Reference link pointer to the next node in the system memory

    public Task(String taskId, String taskName, String status) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.status = status;
        this.next = null;
    }

    public String getTaskId() { return taskId; }
    public String getTaskName() { return taskName; }
    public String getStatus() { return status; }

    @Override
    public String toString() {
        return String.format("[TaskID: %s | Name: %s | Status: %s]", taskId, taskName, status);
    }
}