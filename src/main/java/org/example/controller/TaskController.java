package org.example.controller;

import org.example.dto.TaskInfo;
import org.example.model.Task;
import org.example.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/")
public class TaskController {
    private final TaskService taskService;

    private final Logger logger = LoggerFactory.getLogger(TaskController.class);

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public String tasks(Model model,
                            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                            @RequestParam(value = "limit", required = false, defaultValue = "10") int limit) {
        List<Task> tasks = taskService.findAll((page - 1) * limit, limit);
        logger.info("tasks count: {}", tasks.size());
        model.addAttribute("tasks", tasks);
        model.addAttribute("currentPage", page);
        int totalPages = (int) Math.ceil((1.0 * taskService.getAllCount()) / limit);
        if (totalPages > 1) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .toList();
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "tasks";
    }

    @GetMapping("/count")
    public String count(Model model) {
        int count = taskService.getAllCount();
        model.addAttribute("count", count);
        return tasks(model, 1, 10);
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public String create(@RequestBody TaskInfo task, Model model) {
        Task newTask = new Task();
        newTask.setDescription(task.getDescription());
        newTask.setStatus(task.getStatus());

        taskService.create(newTask);

        model.addAttribute("task", newTask);
        return tasks(model, 1, 10);
    }

    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String update(@RequestBody TaskInfo task,
                       @PathVariable("id") Integer id,
                       Model model) {
        Task newTask = new Task();
        newTask.setDescription(task.getDescription());
        newTask.setStatus(task.getStatus());
        newTask.setId(id);

        taskService.update(newTask);

        model.addAttribute("task", newTask);
        return tasks(model, 1, 10);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String delete(Model model, @PathVariable("id") Integer id) {
        if (id != null) {
            taskService.delete(id);
        }
        return tasks(model, 1, 10);
    }
}
