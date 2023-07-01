package com.jejeong.apipractice.controller.todo;

import com.jejeong.apipractice.controller.todo.request.DefaultTodoRequest;
import com.jejeong.apipractice.controller.todo.request.UpdateTodoRequest;
import com.jejeong.apipractice.controller.todo.response.DefaultTodoResponse;
import com.jejeong.apipractice.sevice.todo.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/todos")
public class TodoController {

  private final TodoService todoService;

  @PostMapping
  public ResponseEntity<Void> createTodo(@RequestBody DefaultTodoRequest req,
      Authentication authentication) {
    todoService.createTodo(req, authentication.getName());
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @GetMapping("/{todoId}")
  public ResponseEntity<DefaultTodoResponse> getTodo(@PathVariable Long todoId) {
    return ResponseEntity.ok(todoService.findTodo(todoId));
  }

  @GetMapping
  public ResponseEntity<Page<DefaultTodoResponse>> getTodoList(Authentication authentication,
      Pageable pageable) {
    return ResponseEntity.ok(todoService.findTodoList(authentication.getName(), pageable));
  }

  @PutMapping
  public ResponseEntity<Void> modifyTodo(@RequestBody UpdateTodoRequest req,
      Authentication authentication) {
    todoService.modifyTodo(req, authentication.getName());
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{todoId}")
  public ResponseEntity<Void> removeTodo(@PathVariable Long todoId, Authentication authentication) {
    todoService.deleteTodo(todoId, authentication.getName());
    return ResponseEntity.ok().build();
  }
}
