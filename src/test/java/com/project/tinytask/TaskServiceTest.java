package com.project.tinytask;
import com.project.tinytask.entity.Task;
import com.project.tinytask.repository.TaskRepository;
import com.project.tinytask.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TaskServiceTest {

    private TaskRepository repository;
    private TaskService service;

    @BeforeEach
    void setUp() {
        repository = new TaskRepository();
        service = new TaskService(repository);
    }

    // CREATE - positivo
    @Test
    void saveTask_validTitle_createsTask() {
        Task input = new Task();
        input.setTitle("Learn Spring Boot");

        Task saved = service.saveTask(input);

        assertNotNull(saved);
        assertEquals("Learn Spring Boot", saved.getTitle());
        assertFalse(saved.isDone());
        assertEquals(1, repository.findAll().size());
    }

    // CREATE - negativo (título demasiado corto)
    @Test
    void saveTask_shortTitle_throwsIllegalArgument() {
        Task input = new Task();
        input.setTitle("ab");

        assertThrows(IllegalArgumentException.class, () -> service.saveTask(input));
    }

    // TOGGLE - positivo
    @Test
    void toggleTask_existing_changesDone() {
        Task created = repository.save("Task 1");
        assertFalse(created.isDone());

        service.toggleTask(created.getId()); // ahora no devuelve Task

        Optional<Task> after = repository.findById(created.getId());
        assertTrue(after.isPresent());
        assertTrue(after.get().isDone()); // ahora debería ser true
    }



    // TOGGLE - negativo (id inexistente)
    @Test
    void toggleTask_nonExisting_throwsNoSuchElement() {
        assertThrows(NoSuchElementException.class, () -> service.toggleTask(999));
    }

    // DELETE - positivo
    @Test
    void deleteTask_existing_removes() {
        Task created = repository.save("Task to delete");
        assertEquals(1, repository.findAll().size());

        service.deleteTask(created.getId());
        assertEquals(0, repository.findAll().size());
    }

    // DELETE - negativo (id inexistente)
    @Test
    void deleteTask_nonExisting_throwsNoSuchElement() {
        assertThrows(NoSuchElementException.class, () -> service.deleteTask(12345));
    }
}

