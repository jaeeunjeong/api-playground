package com.jejeong.apipractice.dto.todo;

import com.jejeong.apipractice.entity.todo.Todo;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class TodoDto {

  private Long id;
  private String title;
  private String description;
  private Timestamp dueDate;
  private boolean isComplete;
  private boolean isDeleted;
  private Timestamp registeredAt;
  private Timestamp updatedAt;
  private Timestamp removeAt;

  protected TodoDto() {
  }

  public static TodoDto fromEntity(Todo entity) {

    TodoDto dto = new TodoDto();
    dto.id = entity.getId();
    dto.title = entity.getTitle();
    dto.description = entity.getDescription();
    dto.dueDate = entity.getDueDate();
    dto.isComplete = entity.isComplete();
    dto.isDeleted = entity.isDeleted();
    dto.registeredAt = entity.getRegisteredAt();
    dto.updatedAt = entity.getUpdatedAt();
    dto.removeAt = entity.getRemovedAt();

    return dto;
  }
}
