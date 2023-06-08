package com.jejeong.apipractice.entity.common;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;
import java.time.Instant;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
public abstract class EntityDate {

    @Column(name = "registered_at")
    @CreatedDate
    private Timestamp registeredAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private Timestamp updatedAt;

    @Column(name = "removed_at")
    @LastModifiedDate
    private Timestamp removedAt;

    @PrePersist
    void registeredAt() {
        this.registeredAt = Timestamp.from(Instant.now());
    }

    @PreUpdate
    void updatedAt() {
        this.updatedAt = Timestamp.from(Instant.now());
    }

}
