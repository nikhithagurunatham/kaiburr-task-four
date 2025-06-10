package com.example.demo.service;

import com.example.demo.model.Task;
import com.example.demo.model.TaskExecution;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    List<Task> getAllTasks();
    Optional<Task> getTaskById(String id);
    Task saveTask(Task task);
    boolean deleteTask(String id);
    List<Task> searchTasksByName(String name);
    Optional<TaskExecution> executeTaskById(String id);
}
