package com.todo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface TodoRepo extends JpaRepository<Todo, Integer>{

}