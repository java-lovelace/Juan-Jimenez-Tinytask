package com.project.tinytask.service;

import com.project.tinytask.entity.Task;
import com.project.tinytask.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTasks(){
        return taskRepository.findAll();
    }

    public void toggleTask(int id){
        Optional<Task> optional = taskRepository.findById(id);
        if (optional.isEmpty()) {
            throw new NoSuchElementException("Not found");
        }
        // delegamos el toggle al repository (que no devuelve nada)
        taskRepository.toggleTask(id);
    }

    public Task saveTask(Task task){
        if(task.getTitle() == null || task.getTitle().trim().length() < 3){
            throw new IllegalArgumentException("Title is required");
        }
        return taskRepository.save(task.getTitle());
    }
    public void deleteTask(int id){
        boolean removed = taskRepository.delete(id);
        if (!removed) {
            throw new NoSuchElementException("Not found");
        }
    }
}



