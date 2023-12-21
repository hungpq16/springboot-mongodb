package com.hungpq.springbootmongo.repository;

import com.hungpq.springbootmongo.model.TodoDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TodoRepository extends MongoRepository<TodoDTO, String> {

    @Query("{'todo': ?0}")
    Optional<TodoDTO> findByTodo(String todo);

}
