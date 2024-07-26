package com.jejeong.apipractice.entity.common;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.sql.Timestamp;
import java.time.Instant;
import lombok.Getter;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
public class EntityDate {

  @Column(name = "created_at")
  private Timestamp createdAt;

  @LastModifiedDate
  @Column(name = "updated_at")
  private Timestamp updatedAt;

  @Column(name = "removed_at")
  private Timestamp removedAt;

  @PrePersist
  void createAt() {
    this.createdAt = Timestamp.from(Instant.now());
  }

  @PreUpdate
  void updatedAt() {
    this.updatedAt = Timestamp.from(Instant.now());
  }
}
