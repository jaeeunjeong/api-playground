package com.jejeong.apipractice.entity.todo;

import com.jejeong.apipractice.entity.common.EntityDate;
import com.jejeong.apipractice.entity.member.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "todo")
@Getter
@Where(clause = "deleted = false")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Todo extends EntityDate {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "title", nullable = false)
  private String title;

  @Column(name = "description")
  private String description;

  private Timestamp dueDate;

  private boolean isComplete;

  private boolean isDeleted;

  @ManyToOne
  @JoinColumn(name = "member_id")
  private Member member;

  public static Todo of(String title, String description, LocalDateTime dueDate) {

    Todo entity = new Todo();
    entity.title = title;
    entity.description = description;
    entity.dueDate = Timestamp.valueOf(dueDate);

    return entity;
  }

  public void updateTodo(String title, String description, LocalDateTime dueDate) {
    this.title = title;
    this.description = description;
    this.dueDate = Timestamp.valueOf(dueDate);
  }
}
