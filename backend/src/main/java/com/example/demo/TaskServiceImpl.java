package com.example.demo.service;

import com.example.demo.model.Task;
import com.example.demo.model.TaskExecution;
import com.example.demo.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public Optional<Task> getTaskById(String id) {
        return taskRepository.findById(id);
    }

    @Override
    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public boolean deleteTask(String id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Task> searchTasksByName(String name) {
        return taskRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    public Optional<TaskExecution> executeTaskById(String id) {
        Optional<Task> optTask = taskRepository.findById(id);
        if (optTask.isEmpty()) {
            return Optional.empty();
        }
        Task task = optTask.get();

        TaskExecution executionResult;
        try {
            String output = runCommand(task.getCommand());
            executionResult = new TaskExecution(output, true);
        } catch (Exception e) {
            executionResult = new TaskExecution("Error: " + e.getMessage(), false);
        }

        // Save execution result in the task's execution list
        task.getTaskExecutions().add(executionResult);
        taskRepository.save(task);

        return Optional.of(executionResult);
    }

    private String runCommand(String command) throws Exception {
        // Simple validation example: reject commands with 'rm' or 'del' for safety
        if (command.contains("rm") || command.contains("del")) {
            throw new Exception("Unsafe command detected.");
        }

        ProcessBuilder builder = new ProcessBuilder();
        // Use OS shell to run command:
        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            builder.command("cmd.exe", "/c", command);
        } else {
            builder.command("sh", "-c", command);
        }
        Process process = builder.start();

        StringBuilder output = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
        }

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new Exception("Command exited with code " + exitCode);
        }

        return output.toString().trim();
    }
}
