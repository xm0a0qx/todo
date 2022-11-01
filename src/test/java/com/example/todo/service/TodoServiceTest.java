package com.example.todo.service;

import com.example.todo.model.Todo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TodoServiceTest {

    @Mock
    private TodoService service;

    private Todo todo1;
    private Todo todo2;
    private List<Todo> todos;

    @BeforeEach
    void setUp() {
        todo1 = mock(Todo.class);
        todo2 = mock(Todo.class);
        todos = List.of(todo1, todo2);
    }

    @Test
    void shouldSaveTodo() {
        service.insertTodo(todo1);
        verify(service, times(1)).insertTodo(todo1);
    }

    @Test
    void shouldUpdateTodo() {
        service.updateTodo(todo1);
        verify(service, times(1)).updateTodo(todo1);
    }

    @Test
    void shouldShowAllTodos() {
        when(service.showAllTodos()).thenReturn(todos);
        var todoList = service.showAllTodos();
        verify(service, times(1)).showAllTodos();
        Assertions.assertEquals(todos, todoList);
    }

    @Test
    void shouldShowLastCreatedTodo() {
        when(service.showLastCreatedTodo(todo2.getId())).thenReturn(todo2);
        var latestCreatedTodo = service.showLastCreatedTodo(todo2.getId());
        Assertions.assertEquals(todo2, latestCreatedTodo);
        verify(service, times(1)).showLastCreatedTodo(todo2.getId());
    }

    @Test
    void shouldFindById() {
        when(service.findById(todo2.getId())).thenReturn(Optional.ofNullable(todo2));
        var id2 = service.findById(todo2.getId());
        Assertions.assertEquals(todo2, id2.orElseThrow());
    }

    @Test
    void shouldDelete() {
        service.delete(todo1);
        var size = Stream.of(service.showAllTodos()).count();
        Assertions.assertEquals(1L, size);
    }
}