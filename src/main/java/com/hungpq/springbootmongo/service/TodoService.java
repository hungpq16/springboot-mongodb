package com.hungpq.springbootmongo.service;

import com.hungpq.springbootmongo.exception.TodoCollectionException;
import com.hungpq.springbootmongo.model.TodoDTO;
import jakarta.validation.ConstraintViolationException;

import java.util.List;
import java.util.Optional;

public interface TodoService {

    public void createTodo(TodoDTO todo) throws ConstraintViolationException, TodoCollectionException;

    public List<TodoDTO> findAll();

    public TodoDTO getTodoById(String id) throws TodoCollectionException;

    public void updateTodo(String id, TodoDTO todo) throws TodoCollectionException;

    public void deleteTodo(String id) throws TodoCollectionException;
}
