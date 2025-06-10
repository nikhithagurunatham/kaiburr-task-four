package com.example.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.ShellTaskResult;

@Repository
public interface ShellTaskRepository extends MongoRepository<ShellTaskResult, String> {
}
