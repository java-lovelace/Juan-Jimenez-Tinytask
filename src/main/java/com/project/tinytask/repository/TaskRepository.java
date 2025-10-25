package com.project.tinytask.repository;

import com.project.tinytask.entity.Task;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class TaskRepository {
    private final List<Task> todos = new ArrayList<>();
    private int idCounter = 1;

    public List<Task> findAll() {
        return todos;
    }

    public Optional<Task> findById(int id){
        return todos.stream().filter(t -> t.getId() == id).findFirst();
    }

    public Task save (String title){
        Task task = new Task(idCounter++,title);
        todos.add(task);
        return task;
    }

    public void toggleTask(int id){
        Optional<Task> optional = findById(id);

        if(optional.isEmpty()){
            System.out.println("task not found");
            return;
        }
        Task taskFound = optional.get();
        taskFound.setDone(!taskFound.isDone());
        todos.set(todos.indexOf(taskFound), taskFound);
    }
    public boolean delete(int id) {
        return todos.removeIf(t -> t.getId() == id);
    }
}
