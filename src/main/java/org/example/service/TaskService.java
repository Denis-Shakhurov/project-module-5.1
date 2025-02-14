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

    public Task findById(Long id) {
        return taskDAO.findById(id.intValue())
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
    }

    public int getAllCount() {
        return taskDAO.getAllCount();
    }

    public Task create(Task task) {
        if (task != null) {
            taskDAO.updateOrSave(task);
        }
        return task;
    }

    @Transactional
    public Task update(Task task) {
        Task oldTask = taskDAO.findById(task.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
        if (oldTask != null) {
            oldTask.setDescription(task.getDescription());
            oldTask.setStatus(task.getStatus());
            taskDAO.updateOrSave(oldTask);
        }
        return oldTask;
    }

    @Transactional
    public void delete(Long id) {
        Task task = taskDAO.findById(id.intValue())
                        .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
        if (task != null) {
            taskDAO.deleteById(id.intValue());
        }
    }
}
