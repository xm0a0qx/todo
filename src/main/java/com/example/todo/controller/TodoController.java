package com.example.todo.controller;

import com.example.todo.model.Todo;
import com.example.todo.service.TodoService;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

import static org.apache.logging.log4j.LogManager.getLogger;

@Controller
public class TodoController {

    private static Logger logger = getLogger(TodoController.class);

    @Autowired
    private TodoService service;

    @GetMapping("/index")
    public String showMainView(Model model) {
        model.addAttribute("todos", service.showAllTodos());
        return "index";
    }

    @GetMapping("/create")
    public String showCreateForm(Todo todo) {
        return "add-todo";
    }

    @PostMapping("/save")
    public String insertTodo(@Valid Todo todo, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            logger.error("Validation error occurred while inserting");
            return "add-todo";
        }
        service.insertTodo(todo);
        redirectAttributes.addAttribute("id", todo.getId());
        return "redirect:/created";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") long id, Model model) {
        Todo todo = service.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid todo Id:" + id));
        model.addAttribute("todo", todo);

        return "edit-todo";
    }

    @PostMapping("/update/{id}")
    public String updateTodo(@PathVariable("id") long id, @Valid Todo todo, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            logger.error("Validation errors occurred while update");
            return "edit-todo";
        }
        service.updateTodo(todo);
        redirectAttributes.addAttribute("id", id);

        return "redirect:/created";
    }

    @GetMapping("/created")
    public String showLastCreated(Model model, @RequestParam("id") Long id) {
        model.addAttribute("todo", service.showLastCreatedTodo(id));
        return "show-created";
    }

    @GetMapping("/delete/{id}")
    public String deleteTodo(@PathVariable("id") long id) {
        var todo = service.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid todo Id:" + id));
        service.delete(todo);

        return "redirect:/index";
    }
}
