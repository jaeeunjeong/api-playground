package com.jejeong.apipractice.sevice.todo;

import com.jejeong.apipractice.controller.todo.request.DefaultTodoRequest;
import com.jejeong.apipractice.controller.todo.request.UpdateTodoRequest;
import com.jejeong.apipractice.controller.todo.response.DefaultTodoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TodoService {

  DefaultTodoResponse findTodo(Long todoId);

  Page<DefaultTodoResponse> findTodoList(String userEmail, Pageable pageable);

  void createTodo(DefaultTodoRequest req, String userEmail);

  void modifyTodo(UpdateTodoRequest req, String userEmail);

  void deleteTodo(Long todoId, String userEmail);
}
