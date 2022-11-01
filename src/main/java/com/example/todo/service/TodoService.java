package com.example.todo.service;

import com.example.todo.model.Todo;
import com.example.todo.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TodoService {

    @Autowired
    private TodoRepository repository;

    public void insertTodo(Todo todo) {
        repository.save(todo);
    }

    public void updateTodo(Todo todo) {
        repository.save(todo);
    }

    public Iterable<Todo> showAllTodos() {
        return repository.findAll();
    }

    public Todo showLastCreatedTodo(Long id) {
        return repository.findById(id).orElseThrow();
    }

    public Optional<Todo> findById(Long id) {
        return repository.findById(id);
    }

    public void delete(Todo todo) {
        repository.delete(todo);
    }
}
