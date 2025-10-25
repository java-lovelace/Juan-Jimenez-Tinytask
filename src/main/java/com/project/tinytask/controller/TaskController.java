package com.project.tinytask.controller;

import com.project.tinytask.entity.Task;
import com.project.tinytask.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService){
        this.taskService = taskService;
    }

    @GetMapping
    ResponseEntity<List<Task>> getAllTasks(){
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @PostMapping
    ResponseEntity<Task> createTask(@RequestBody Task task){
        Task savedTask = taskService.saveTask(task);
        return ResponseEntity.ok(savedTask);
    }

    @PutMapping("/{id}/toggle")
    ResponseEntity<?> toggleTask(@PathVariable int id){
        taskService.toggleTask(id);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteTask(@PathVariable int id){
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build(); // 204
    }
}
