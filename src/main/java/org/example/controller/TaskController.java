package org.example.controller;

import org.example.dto.TaskInfo;
import org.example.model.Task;
import org.example.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/")
public class TaskController {
    private final TaskService taskService;

    private final Logger logger = LoggerFactory.getLogger(TaskController.class);

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/")
    public String tasks(Model model,
                            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                            @RequestParam(value = "limit", required = false, defaultValue = "1") int limit) {
        List<Task> tasks = taskService.findAll((page - 1) * limit, limit);
        logger.info("tasks count: {}", tasks.size());
        model.addAttribute("tasks", tasks);
        String hello = "Hello World";
        model.addAttribute("hello", hello);
        return "tasks";
    }

//    @GetMapping("{id}")
//    public Task getTask(@PathVariable Long id, Model model) {
//        Task task = taskService.findById(id);
//        model.addAttribute("task", task);
//        return task;
//    }

    @PostMapping("/")
    public String create(@RequestBody TaskInfo task, Model model) {
        Task newTask = new Task();
        newTask.setDescription(task.getDescription());
        newTask.setStatus(task.getStatus());

        taskService.create(newTask);

        model.addAttribute("task", newTask);
        return "redirect:/tasks/";
    }

    @PostMapping("/{id}")
    public String update(@RequestBody TaskInfo task,
                       @PathVariable Long id,
                       Model model) {
        Task newTask = new Task();
        newTask.setDescription(task.getDescription());
        newTask.setStatus(task.getStatus());
        newTask.setId(id.intValue());

        taskService.update(newTask);

        model.addAttribute("task", newTask);
        return "redirect:/tasks/";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        taskService.delete(id);
        return "redirect:/tasks/";
    }
}
