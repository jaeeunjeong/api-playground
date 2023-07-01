package com.jejeong.apipractice.repository.todo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.jejeong.apipractice.entity.todo.Todo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class TodoRepositoryTest {

  @PersistenceContext
  EntityManager em;

  @Autowired
  TodoRepository todoRepository;

  @DisplayName("Todo 생성")
  @Test
  void test1() {
    // given
    Todo todo = createTodo();

    // when
    todoRepository.save(todo);

    // then
    assertNotNull(todo.getId());
  }


  @DisplayName("Todo 조회")
  @Test
  void test2() {
    // given
    Todo todo = createTodo();
    todoRepository.save(todo);

    // when
    Todo foundTodo = todoRepository.findById(todo.getId()).orElseThrow();

    // then
    assertThat(todo.getId()).isEqualTo(foundTodo.getId());
  }

  @DisplayName("Todo 삭제")
  @Test
  void test3() {
    // given
    Todo todo = createTodo();
    todoRepository.save(todo);

    // when
    todoRepository.delete(todo);
    Optional<Todo> deletedUser = todoRepository.findById(todo.getId());

    // then
    assertFalse(deletedUser.isPresent());
  }


  public Todo createTodo() {
    return Todo.of("title", "description", Timestamp.valueOf(LocalDateTime.now()));
  }
}