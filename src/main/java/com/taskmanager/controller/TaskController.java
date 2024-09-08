package com.taskmanager.controller;

import com.taskmanager.model.Task;
import com.taskmanager.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    // Home page displaying all tasks
    @GetMapping("/")
    public String viewHomePage(Model model) {
        List<Task> tasks = taskRepository.findAll();
        model.addAttribute("tasks", tasks);
        return "index"; // This will render index.html using Thymeleaf
    }

    // Form to create a new task
    @GetMapping("/new-task")
    public String showNewTaskForm(Model model) {
        Task task = new Task();
        model.addAttribute("task", task);
        return "new-task";
    }

    // Save the new task
    @PostMapping("/save-task")
    public String saveTask(@ModelAttribute("task") Task task) {
        taskRepository.save(task);
        return "redirect:/";
    }

    // Form to update an existing task
    @GetMapping("/edit-task/{id}")
    public String showEditTaskForm(@PathVariable("id") Long id, Model model) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid task ID:" + id));
        model.addAttribute("task", task);
        return "edit-task";
    }

    // Delete a task
    @GetMapping("/delete-task/{id}")
    public String deleteTask(@PathVariable("id") Long id) {
        taskRepository.deleteById(id);
        return "redirect:/";
    }
}
