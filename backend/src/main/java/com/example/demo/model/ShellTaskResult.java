package com.example.demo.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "shell_tasks")
public class ShellTaskResult {

    @Id
    private String id;
    private String command;
    private String output;
    private String status;
    private LocalDateTime timestamp;

    public ShellTaskResult() {}

    public ShellTaskResult(String command, String output, String status, LocalDateTime timestamp) {
        this.command = command;
        this.output = output;
        this.status = status;
        this.timestamp = timestamp;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getCommand() { return command; }
    public void setCommand(String command) { this.command = command; }
    public String getOutput() { return output; }
    public void setOutput(String output) { this.output = output; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
