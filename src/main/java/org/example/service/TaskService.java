package org.example.service;

import org.example.dao.TaskDAO;
import org.example.exception.ResourceNotFoundException;
import org.example.model.Task;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TaskService {
    private final TaskDAO taskDAO;

    public TaskService(TaskDAO taskDAO) {
        this.taskDAO = taskDAO;
    }

    public List<Task> findAll(int page, int size) {
        return taskDAO.findAll(page, size);
    }

    public Task findById(int id) {
        return taskDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
    }

    public int getAllCount() {
        return taskDAO.getAllCount();
    }

    public void create(Task task) {
        if (task != null) {
            taskDAO.save(task);
        }
    }

    @Transactional
    public void update(Task task) {
        taskDAO.update(task);
    }

    @Transactional
    public void delete(int id) {
        taskDAO.findById(id).ifPresent(task -> taskDAO.deleteById(id));
    }
}
