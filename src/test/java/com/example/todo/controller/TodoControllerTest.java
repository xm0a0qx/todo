package com.example.todo.controller;

import com.example.todo.model.Todo;
import com.example.todo.service.TodoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class TodoControllerTest {

    @Mock
    private TodoService service;
    @Autowired
    private MockMvc mockMvc;
    @InjectMocks
    private TodoController todoController;

    private Todo todo1;
    private Todo todo2;
    private Model model;

    @BeforeEach
    void setUp() {
        todo1 = new Todo();
        todo1.setId(1L);
        todo1.setTask("Sample like this and other way around");
        todo1.setDone(false);
        mockMvc = MockMvcBuilders.standaloneSetup(todoController).build();
        todo2 = mock(Todo.class);
        model = mock(Model.class);
    }

    @Test
    void showCreateForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(todo1)))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void insertTodo() throws Exception {
        when(service.findById(todo1.getId())).thenReturn(Optional.ofNullable(todo1));
        mockMvc.perform(post("/save", todo1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(todo1)))
                .andExpect(status().is3xxRedirection());
        Assertions.assertEquals(todo1, service.findById(todo1.getId()).orElseThrow());
    }

    @Test
    void showEditForm() throws Exception {
        when(service.findById(todo1.getId())).thenReturn(Optional.ofNullable(todo1));
        mockMvc.perform(MockMvcRequestBuilders.get("/edit/{id}", todo1.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(todo1)))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void updateTodo() throws Exception {
        mockMvc.perform(post("/update/{id}", todo1.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(todo1)))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void showLastCreated() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/created", model, todo1.getId())
                        .param("id", todo1.getId().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(todo1)))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    void deleteTodo() throws Exception {
        when(service.findById(todo1.getId())).thenReturn(Optional.ofNullable(todo1));
        mockMvc.perform(MockMvcRequestBuilders.get("/delete/{id}", todo1.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(todo1)))
                .andExpect(status().is3xxRedirection());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}