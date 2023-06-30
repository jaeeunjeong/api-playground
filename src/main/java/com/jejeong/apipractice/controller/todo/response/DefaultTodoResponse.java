package com.jejeong.apipractice.controller.todo.response;

import com.jejeong.apipractice.dto.todo.TodoDto;
import java.sql.Timestamp;
import lombok.Getter;

@Getter
public class DefaultTodoResponse {

  private Long id;
  private String title;
  private String description;
  private Timestamp dueDate;
  private boolean isComplete;
  private boolean isDeleted;
  private Timestamp registeredAt;
  private Timestamp updatedAt;
  private Timestamp removeAt;

  public static DefaultTodoResponse of(TodoDto dto) {
    DefaultTodoResponse res = new DefaultTodoResponse();
    res.id = dto.getId();
    res.title = dto.getTitle();
    res.description = dto.getDescription();
    res.dueDate = dto.getDueDate();
    res.isComplete = dto.isComplete();
    res.isDeleted = dto.isDeleted();
    res.registeredAt = dto.getRegisteredAt();
    res.updatedAt = dto.getUpdatedAt();
    return res;
  }
}
