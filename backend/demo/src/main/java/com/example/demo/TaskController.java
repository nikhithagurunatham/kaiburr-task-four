package com.example.demo.controller;

import com.example.demo.model.Task;
import com.example.demo.model.TaskExecution;
import com.example.demo.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
@Tag(name = "Task Management API", description = "Manage tasks and execute shell commands")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Operation(summary = "Get all tasks")
    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @Operation(summary = "Get task by ID")
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable String id) {
        Optional<Task> task = taskService.getTaskById(id);
        if (task.isPresent()) {
            return ResponseEntity.ok(task.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Operation(summary = "Create or update a task")
    @PutMapping
    public ResponseEntity<?> createOrUpdateTask(@RequestBody Task task) {
        if (isUnsafeCommand(task.getCommand())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Unsafe command detected.");
        }
        Task saved = taskService.saveTask(task);
        return ResponseEntity.ok(saved);
    }

    @Operation(summary = "Delete a task by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable String id) {
        boolean deleted = taskService.deleteTask(id);
        if (deleted) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Operation(summary = "Search tasks by name substring")
    @GetMapping("/search")
    public ResponseEntity<List<Task>> searchTasksByName(@RequestParam String name) {
        List<Task> tasks = taskService.searchTasksByName(name);
        if (tasks.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(tasks);
    }

    @Operation(summary = "Execute a task by ID")
    @PutMapping("/{id}/execute")
    public ResponseEntity<TaskExecution> executeTask(@PathVariable String id) {
        Optional<TaskExecution> execResult = taskService.executeTaskById(id);
        if (execResult.isPresent()) {
            return ResponseEntity.ok(execResult.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    private boolean isUnsafeCommand(String command) {
        // Basic example of command validation: block dangerous commands
        if (command == null) return true;
        String lower = command.toLowerCase();
        return lower.contains("rm") || lower.contains("del") || lower.contains("shutdown") || lower.contains("reboot");
    }
}
