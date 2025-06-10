package com.example.demo.model;

import java.util.Date;

public class TaskExecution {

    private Date executedAt;
    private String output;
    private boolean success;

    public TaskExecution() {
        this.executedAt = new Date();
    }

    public TaskExecution(String output, boolean success) {
        this.executedAt = new Date();
        this.output = output;
        this.success = success;
    }

    // Getters and setters

    public Date getExecutedAt() { return executedAt; }
    public void setExecutedAt(Date executedAt) { this.executedAt = executedAt; }

    public String getOutput() { return output; }
    public void setOutput(String output) { this.output = output; }

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }
}
