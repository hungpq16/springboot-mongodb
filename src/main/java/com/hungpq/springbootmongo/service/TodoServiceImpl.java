package com.hungpq.springbootmongo.service;

import com.hungpq.springbootmongo.exception.TodoCollectionException;
import com.hungpq.springbootmongo.model.TodoDTO;
import com.hungpq.springbootmongo.repository.TodoRepository;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TodoServiceImpl implements TodoService {

    @Autowired
    private TodoRepository todoRepository;

    @Override
    public void createTodo(TodoDTO todo) throws ConstraintViolationException, TodoCollectionException {
        Optional<TodoDTO> todoOptional = todoRepository.findByTodo(todo.getTodo());
        if (todoOptional.isPresent()) {
            throw new TodoCollectionException(TodoCollectionException.todoAlreadyExist());
        } else {
            todo.setCreatedAt(new Date(System.currentTimeMillis()));
            todoRepository.save(todo);
        }
    }

    @Override
    public List<TodoDTO> findAll() {
        return todoRepository.findAll();
    }

    @Override
    public TodoDTO getTodoById(String id) throws TodoCollectionException {
        Optional<TodoDTO> todoOptional = todoRepository.findById(id);
        if (!todoOptional.isPresent()) {
            throw new TodoCollectionException(TodoCollectionException.NotFoundException(id));
        } else {
            return todoOptional.get();
        }
    }

    @Override
    public void updateTodo(String id, TodoDTO todo) throws TodoCollectionException {
        Optional<TodoDTO> todoOptional = todoRepository.findById(id);
        Optional<TodoDTO> todoOptionalWithSameName = todoRepository.findByTodo(todo.getTodo());
        if (todoOptional.isPresent()) {
            //
            if (todoOptionalWithSameName.isPresent() && !todoOptionalWithSameName.get().getId().equals(id)) {
                throw new TodoCollectionException(TodoCollectionException.todoAlreadyExist());
            }
            //
            TodoDTO todoSave = todoOptional.get();
            // if data not change, not need update
            todoSave.setTodo(todo.getTodo());
            todoSave.setDescription(todo.getDescription());
            todoSave.setIsCompleted(todo.getIsCompleted());
            todoSave.setUpdatedAt(new Date(System.currentTimeMillis()));
            todoRepository.save(todoSave);
        } else {
            throw new TodoCollectionException(TodoCollectionException.NotFoundException(id));
        }
    }

    @Override
    public void deleteTodo(String id) throws TodoCollectionException {
        Optional<TodoDTO> todoOptional = todoRepository.findById(id);
        if (!todoOptional.isPresent()) {
            todoRepository.deleteById(id);
            throw new TodoCollectionException((TodoCollectionException.NotFoundException(id)));
        } else {
            todoRepository.deleteById(id);
        }
    }
}
