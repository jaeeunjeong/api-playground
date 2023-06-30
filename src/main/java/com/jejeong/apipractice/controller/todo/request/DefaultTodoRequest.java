package com.jejeong.apipractice.controller.todo.request;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class DefaultTodoRequest {

  private String title;
  private String description;
  private LocalDateTime dueDate;

}
