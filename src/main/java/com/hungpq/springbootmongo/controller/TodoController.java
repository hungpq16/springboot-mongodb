package com.hungpq.springbootmongo.controller;

import com.hungpq.springbootmongo.exception.TodoCollectionException;
import com.hungpq.springbootmongo.model.TodoDTO;
import com.hungpq.springbootmongo.service.TodoService;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @GetMapping("/todos")
    public ResponseEntity<?> getAllTodo() {
        List<TodoDTO> todos = todoService.findAll();
        return new ResponseEntity<List<TodoDTO>>(todos, todos.size() > 0 ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @PostMapping("/todos")
    public ResponseEntity<?> createTodo(@RequestBody TodoDTO todo) {
        try {
            todoService.createTodo(todo);
            return new ResponseEntity<TodoDTO>(todo, HttpStatus.OK);
        } catch (ConstraintViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (TodoCollectionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/todos/{id}")
    public ResponseEntity<?> getTodoById(@PathVariable("id") String id) {
        try {
            return new ResponseEntity<>(todoService.getTodoById(id), HttpStatus.OK);
        } catch (TodoCollectionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/todos/{id}")
    public ResponseEntity<?> updateTodoById(@PathVariable("id") String id, @RequestBody TodoDTO todo) {
        try {
            todoService.updateTodo(id, todo);
            return new ResponseEntity<>("Update Todo with id " + id + "successfully!", HttpStatus.OK);
        } catch (TodoCollectionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/todos/{id}")
    public ResponseEntity<?> deleteTodoById(@PathVariable("id") String id) {
        try {
            todoService.deleteTodo(id);
            return new ResponseEntity<>("Delete Todo with id " + id + "successfully!", HttpStatus.OK);
        } catch (TodoCollectionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
