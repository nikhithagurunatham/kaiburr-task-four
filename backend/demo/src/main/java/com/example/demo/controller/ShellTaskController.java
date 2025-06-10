package com.example.demo.controller;

import com.example.demo.model.ShellTaskRequest;
import com.example.demo.model.ShellTaskResult;
import com.example.demo.service.ShellTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ShellTaskController {

    @Autowired
    private ShellTaskService shellTaskService;

    @PostMapping("/shell/execute")
    public ShellTaskResult executeShellCommand(@RequestBody ShellTaskRequest request) {
        return shellTaskService.executeCommand(request.getCommand());
    }
}
