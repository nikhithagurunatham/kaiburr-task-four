package com.example.demo.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.ShellTaskResult;
import com.example.demo.repository.ShellTaskRepository;

@Service
public class ShellTaskService {

    @Autowired
    private ShellTaskRepository repository;

    public ShellTaskResult executeCommand(String command) {
        StringBuilder output = new StringBuilder();
        String status = "SUCCESS";

        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command("cmd.exe", "/c", command);  // Windows

            Process process = builder.start();

            BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream())
            );

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                status = "FAILURE";
            }
        } catch (Exception e) {
            output.append("Error: ").append(e.getMessage());
            status = "ERROR";
        }

        ShellTaskResult result = new ShellTaskResult(
            command,
            output.toString(),
            status,
            LocalDateTime.now()  // timestamp
        );

        return repository.save(result);
    }
}
