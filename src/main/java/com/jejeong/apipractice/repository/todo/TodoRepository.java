package com.jejeong.apipractice.repository.todo;

import com.jejeong.apipractice.entity.todo.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {

}
